from _pytest.outcomes import Failed
from decimal import Decimal as d
from selenium.common.exceptions import TimeoutException

from tests import marks, transaction_users, common_password, group_chat_users
from tests.base_test_case import MultipleDeviceTestCase
from views.sign_in_view import SignInView


@marks.chat
@marks.transaction
class TestCommands(MultipleDeviceTestCase):

    @marks.testrail_case_id(3742)
    @marks.testrail_id(3697)
    def test_network_mismatch_for_send_request_commands(self):
        sender = self.senders['d_user'] = transaction_users['D_USER']
        self.create_drivers(2)
        device_1_sign_in, device_2_sign_in = SignInView(self.drivers[0]), SignInView(self.drivers[1])
        device_1_sign_in.recover_access(passphrase=sender['passphrase'], password=sender['password'])
        device_2_sign_in.create_user()
        device_1_home, device_2_home = device_1_sign_in.get_home_view(), device_2_sign_in.get_home_view()

        public_key = device_2_home.get_public_key()
        device_2_profile = device_2_home.get_profile_view()
        device_2_profile.switch_network('Mainnet with upstream RPC')
        device_2_sign_in.click_account_by_position(0)
        device_2_sign_in.sign_in()

        device_1_chat = device_1_home.add_contact(public_key)
        amount_1 = device_1_chat.get_unique_amount()
        device_1_chat.send_transaction_in_1_1_chat(amount_1, common_password, wallet_set_up=True)
        status_text_1 = device_1_chat.chat_element_by_text(amount_1).status.text
        if status_text_1 != 'Sent':
            self.errors.append("Message about sent funds has status '%s' instead of 'Sent'" % status_text_1)

        device_2_chat = device_2_home.get_chat_with_user(sender['username']).click()
        chat_element_1 = device_2_chat.chat_element_by_text(amount_1)
        try:
            chat_element_1.wait_for_visibility_of_element(120)
            if chat_element_1.status.text != 'Network mismatch':
                self.errors.append("'Network mismatch' warning is not shown for send transaction message")
            if not chat_element_1.contains_text('testnet'):
                self.errors.append("Sent transaction message doesn't contain text 'testnet'")
        except TimeoutException:
            self.errors.append('Sent transaction message was not received')

        amount_2 = device_1_chat.get_unique_amount()
        device_1_chat.request_transaction_in_1_1_chat(amount_2)
        status_text_2 = device_1_chat.chat_element_by_text(amount_2).status.text
        if status_text_2 != 'Sent':
            self.errors.append("Request funds message has status '%s' instead of 'Sent'" % status_text_2)

        chat_element_2 = device_2_chat.chat_element_by_text(amount_2)
        try:
            chat_element_2.wait_for_visibility_of_element(120)
            if chat_element_2.status.text != 'Network mismatch':
                self.errors.append("'Network mismatch' warning is not shown for request funds message")
            if not chat_element_2.contains_text('On testnet'):
                self.errors.append("Request funds message doesn't contain text 'testnet'")
            if not chat_element_2.contains_text('Transaction Request'):
                self.errors.append("Request funds message doesn't contain text 'Transaction Request'")
        except TimeoutException:
            self.errors.append('Request funds message was not received')
        self.verify_no_errors()

    @marks.testrail_id(765)
    def test_send_eth_in_1_1_chat(self):
        recipient = transaction_users['D_USER']
        sender = self.senders['c_user'] = transaction_users['C_USER']
        self.create_drivers(2)
        device_1, device_2 = SignInView(self.drivers[0]), SignInView(self.drivers[1])
        home_1 = device_1.recover_access(passphrase=sender['passphrase'], password=sender['password'])
        home_2 = device_2.recover_access(passphrase=recipient['passphrase'], password=recipient['password'])
        wallet_1, wallet_2 = home_1.wallet_button.click(), home_2.wallet_button.click()
        wallet_1.set_up_wallet()
        wallet_1.home_button.click()
        wallet_2.set_up_wallet()
        init_balance = wallet_2.get_eth_value()
        wallet_2.home_button.click()

        chat_1 = home_1.add_contact(recipient['public_key'])
        amount = chat_1.get_unique_amount()
        chat_1.commands_button.click()
        chat_1.send_command.click()
        chat_1.eth_asset.click()
        chat_1.send_as_keyevent(amount)
        send_transaction_view = chat_1.get_send_transaction_view()
        chat_1.send_message_button.click_until_presence_of_element(send_transaction_view.sign_transaction_button)

        send_transaction_view.chose_recipient_button.find_element().click()
        if send_transaction_view.recent_recipients_button.is_element_displayed():
            self.errors.append('Recipient field is editable')
            send_transaction_view.click_system_back_button()

        send_transaction_view.select_asset_button.click()
        if not send_transaction_view.chose_recipient_button.is_element_displayed():
            self.errors.append('Asset field is editable')
            send_transaction_view.back_button.click()

        if send_transaction_view.amount_edit_box.is_element_displayed():
            self.errors.append('Amount field is editable')

        send_transaction_view.advanced_button.click()
        send_transaction_view.transaction_fee_button.click()
        gas_limit = '25000'
        send_transaction_view.gas_limit_input.clear()
        send_transaction_view.gas_limit_input.set_value(gas_limit)
        gas_price = '1'
        send_transaction_view.gas_price_input.clear()
        send_transaction_view.gas_price_input.set_value(gas_price)
        send_transaction_view.total_fee_input.click()
        if send_transaction_view.total_fee_input.text != '%s ETH' % (d(gas_limit) * d(gas_price) / d(1000000000)):
            self.errors.append('Gas limit and/or gas price fields were not edited')
        send_transaction_view.done_button.click()
        send_transaction_view.sign_transaction(sender['password'])

        if not chat_1.chat_element_by_text(amount).is_element_displayed():
            self.errors.append('Message with the sent amount is not shown for the sender')
        chat_2 = home_2.get_chat_with_user(sender['username']).click()
        if not chat_2.chat_element_by_text(amount).is_element_displayed():
            self.errors.append('Message with the sent amount is not shown for the recipient')

        chat_2.get_back_to_home_view()
        home_2.wallet_button.click()
        try:
            wallet_2.wait_balance_changed_on_wallet_screen(expected_balance=init_balance + float(amount))
            self.network_api.find_transaction_by_unique_amount(recipient['address'], amount)
        except Failed as e:
            self.errors.append(e.msg)
        self.verify_no_errors()

    @marks.testrail_id(1391)
    def test_request_and_receive_eth_in_1_1_chat(self):
        recipient = transaction_users['C_USER']
        sender = self.senders['d_user'] = transaction_users['D_USER']
        self.create_drivers(2)
        device_1, device_2 = SignInView(self.drivers[0]), SignInView(self.drivers[1])
        home_1 = device_1.recover_access(passphrase=sender['passphrase'], password=sender['password'])
        home_2 = device_2.recover_access(passphrase=recipient['passphrase'], password=recipient['password'])
        wallet_1, wallet_2 = home_1.wallet_button.click(), home_2.wallet_button.click()
        wallet_1.set_up_wallet()
        wallet_1.home_button.click()
        wallet_2.set_up_wallet()
        init_balance = wallet_2.get_eth_value()
        wallet_2.home_button.click()

        chat_2 = home_2.add_contact(sender['public_key'])
        amount = chat_2.get_unique_amount()
        chat_2.request_transaction_in_1_1_chat(amount)

        chat_1 = home_1.get_chat_with_user(recipient['username']).click()
        chat_1.send_eth_to_request(amount=amount, sender_password=sender['password'])

        if not chat_1.chat_element_by_text(amount).is_element_displayed():
            self.errors.append('Message with the sent amount is not shown for the sender')
        if not chat_2.chat_element_by_text(amount).is_element_displayed():
            self.errors.append('Message with the sent amount is not shown for the recipient')
        if chat_2.chat_element_by_text(amount).send_request_button.is_element_displayed():
            self.errors.append("'Send' in a transaction request button is not disabled after receiving transaction")

        chat_2.get_back_to_home_view()
        home_2.wallet_button.click()
        try:
            wallet_2.wait_balance_changed_on_wallet_screen(expected_balance=init_balance + float(amount))
            self.network_api.find_transaction_by_unique_amount(recipient['address'], amount)
        except Failed as e:
            self.errors.append(e.msg)
        self.verify_no_errors()

    @marks.testrail_id(1429)
    def test_request_eth_in_wallet(self):
        self.create_drivers(2)
        device_1, device_2 = SignInView(self.drivers[0]), SignInView(self.drivers[1])
        username_1 = 'user_1'
        recipient = group_chat_users['C_USER']

        home_1 = device_1.create_user(username=username_1)
        home_2 = device_2.recover_access(passphrase=recipient['passphrase'], password=recipient['password'])

        home_1.add_contact(recipient['public_key'])
        home_1.get_back_to_home_view()
        wallet_1 = home_1.wallet_button.click()
        wallet_1.set_up_wallet()

        send_transaction_device_1 = wallet_1.receive_transaction_button.click_until_presence_of_element(
            wallet_1.send_transaction_request)
        wallet_1.send_transaction_request.click()
        send_transaction_device_1.amount_edit_box.scroll_to_element()
        amount = home_1.get_unique_amount()
        send_transaction_device_1.amount_edit_box.set_value(amount)
        send_transaction_device_1.confirm()
        send_transaction_device_1.chose_recipient_button.click()
        sender_button = send_transaction_device_1.element_by_text(recipient['username'])
        send_transaction_device_1.recent_recipients_button.click_until_presence_of_element(sender_button)
        sender_button.click()
        wallet_1.send_request_button.click()

        chat_2 = home_2.get_chat_with_user(username_1).click()
        chat_element = chat_2.chat_element_by_text(amount)
        try:
            chat_element.wait_for_visibility_of_element(120)
            if not chat_element.contains_text('Transaction Request'):
                self.errors.append("Request funds message doesn't contain text 'Transaction Request'")
            if not chat_element.send_request_button.is_element_displayed():
                self.errors.append("Request funds message doesn't contain 'Send' button")
        except TimeoutException:
            self.errors.append('Request funds message was not received')
        self.verify_no_errors()

    @marks.testrail_id(1417)
    def test_contact_profile_send_transaction(self):
        self.create_drivers(1)
        recipient = transaction_users['B_USER']
        sign_in_view = SignInView(self.drivers[0])
        sign_in_view.create_user()
        home_view = sign_in_view.get_home_view()
        sender_public_key = home_view.get_public_key()
        sender_address = home_view.public_key_to_address(sender_public_key)
        home_view.home_button.click()
        self.network_api.get_donate(sender_address)
        wallet_view = home_view.wallet_button.click()
        wallet_view.set_up_wallet()
        home_view.home_button.click()
        home_view.add_contact(recipient['public_key'])
        chat_view = home_view.get_chat_view()
        chat_view.chat_options.click_until_presence_of_element(chat_view.view_profile_button)
        chat_view.view_profile_button.click()
        chat_view.profile_send_transaction.click()
        chat_view.chat_message_input.click()
        chat_view.eth_asset.click()
        amount = chat_view.get_unique_amount()
        chat_view.send_as_keyevent(amount)
        chat_view.send_message_button.click()
        send_transaction_view = chat_view.get_send_transaction_view()
        send_transaction_view.sign_transaction(common_password)
        self.network_api.find_transaction_by_unique_amount(recipient['address'], amount)
