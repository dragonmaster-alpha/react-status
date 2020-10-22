import time

from tests import common_password
from views.base_element import BaseButton, BaseText, BaseEditBox
from views.base_view import BaseView


class SendRequestButton(BaseButton):

    def __init__(self, driver):
        super(SendRequestButton, self).__init__(driver)
        self.locator = self.Locator.accessibility_id('sent-request-button')


class ChooseRecipientButton(BaseButton):
    def __init__(self, driver):
        super(ChooseRecipientButton, self).__init__(driver)
        self.locator = self.Locator.accessibility_id('choose-recipient-button')


class TransactionHistoryButton(BaseButton):
    def __init__(self, driver):
        super(TransactionHistoryButton, self).__init__(driver)
        self.locator = self.Locator.text_selector("History")

    def navigate(self):
        from views.transactions_view import TransactionsView
        return TransactionsView(self.driver)


class ChooseFromContactsButton(BaseButton):
    def __init__(self, driver):
        super(ChooseFromContactsButton, self).__init__(driver)
        self.locator = self.Locator.text_selector("Choose From Contacts")


class ScanQRButton(BaseButton):
    def __init__(self, driver):
        super(ScanQRButton, self).__init__(driver)
        self.locator = self.Locator.accessibility_id("accounts-qr-code")


class AssetText(BaseText):
    def __init__(self, driver, asset):
        super(AssetText, self).__init__(driver)
        self.locator = self.Locator.xpath_selector("//android.view.ViewGroup[@content-desc=':%s-asset-value']"
                                                   "//android.widget.TextView[1]" % asset)


class AssetFullNameInAssets(BaseText):
    def __init__(self, driver):
        super(AssetFullNameInAssets, self).__init__(driver)
        self.locator = self.Locator.xpath_selector('//*[@content-desc="checkbox"]/../../android.widget.TextView[1]')


class AssetSymbolInAssets(BaseText):
    def __init__(self, driver):
        super(AssetSymbolInAssets, self).__init__(driver)
        self.locator = self.Locator.xpath_selector('//*[@content-desc="checkbox"]/../../android.widget.TextView[2]')

class CurrencyItemText(BaseText):
    def __init__(self, driver):
        super(CurrencyItemText, self).__init__(driver)
        self.locator = self.Locator.xpath_selector('//*[@content-desc="currency-item"]//android.widget.TextView')


class UsdTotalValueText(BaseText):
    def __init__(self, driver):
        super(UsdTotalValueText, self).__init__(driver)
        self.locator = self.Locator.accessibility_id('total-amount-value-text')


class SendTransactionRequestButton(BaseButton):
    def __init__(self, driver):
        super(SendTransactionRequestButton, self).__init__(driver)
        self.locator = self.Locator.accessibility_id('sent-transaction-request-button')

    def navigate(self):
        from views.send_transaction_view import SendTransactionView
        return SendTransactionView(self.driver)


class OptionsButton(BaseButton):
    def __init__(self, driver):
        super(OptionsButton, self).__init__(driver)
        self.locator = self.Locator.accessibility_id('options-menu-button')

class AccountOptionsButton(BaseButton):
    def __init__(self, driver, account_name):
        super(AccountOptionsButton, self).__init__(driver)
        self.locator = self.Locator.xpath_selector('(//*[@text="%s"]/../..//*[@content-desc="icon"])[2]' % account_name)


class ManageAssetsButton(BaseButton):
    def __init__(self, driver):
        super(ManageAssetsButton, self).__init__(driver)
        self.locator = self.Locator.accessibility_id('wallet-manage-assets')


class ScanTokensButton(BaseButton):
    def __init__(self, driver):
        super(ScanTokensButton, self).__init__(driver)
        self.locator = self.Locator.accessibility_id('wallet-scan-token')


class STTCheckBox(BaseButton):
    def __init__(self, driver):
        super(STTCheckBox, self).__init__(driver)
        self.locator = self.Locator.xpath_selector("//*[@text='STT']"
                                                   "/../android.view.ViewGroup[@content-desc='checkbox']")


class QRCodeImage(BaseButton):
    def __init__(self, driver):
        super(QRCodeImage, self).__init__(driver)
        self.locator = self.Locator.accessibility_id('qr-code-image')


class AddressText(BaseButton):
    def __init__(self, driver):
        super(AddressText, self).__init__(driver)
        self.locator = self.Locator.accessibility_id('address-text')


class SetUpButton(BaseButton):
    def __init__(self, driver):
        super(SetUpButton, self).__init__(driver)
        self.locator = self.Locator.text_selector("Let’s get set up")


class SetCurrencyButton(BaseButton):
    def __init__(self, driver):
        super(SetCurrencyButton, self).__init__(driver)
        self.locator = self.Locator.text_selector("Set currency")


class SignInPhraseText(BaseText):
    def __init__(self, driver):
        super(SignInPhraseText, self).__init__(driver)
        self.locator = self.Locator.xpath_selector('//*[@text="This is your signing phrase"]'
                                                   '//following-sibling::*[2]/android.widget.TextView')

    @property
    def string(self):
        return self.text

    @property
    def list(self):
        return self.string.split()


class RemindMeLaterButton(BaseButton):
    def __init__(self, driver):
        super(RemindMeLaterButton, self).__init__(driver)
        self.locator = self.Locator.xpath_selector("//*[@text='Show me this again']")


class AssetTextElement(BaseText):
    def __init__(self, driver, asset_name):
        super(AssetTextElement, self).__init__(driver)
        self.locator = self.Locator.text_part_selector(asset_name)


class CollectibleTextElement(BaseText):
    def __init__(self, driver, collectible_name):
        super().__init__(driver)
        self.locator = self.Locator.accessibility_id('%s-collectible-value-text' % collectible_name.lower())


class AssetCheckBox(BaseButton):
    def __init__(self, driver, asset_name):
        super(AssetCheckBox, self).__init__(driver)
        self.asset_name = asset_name
        self.locator = self.Locator.xpath_selector("//*[@text='%s']" % self.asset_name)

    def click(self):
        self.scroll_to_element(12).click()
        self.driver.info('Click %s asset checkbox' % self.asset_name)


class TotalAmountText(BaseText):

    def __init__(self, driver):
        super(TotalAmountText, self).__init__(driver)
        self.locator = self.Locator.accessibility_id('total-amount-value-text')


class CurrencyText(BaseText):

    def __init__(self, driver):
        super(CurrencyText, self).__init__(driver)
        self.locator = self.Locator.accessibility_id('total-amount-currency-text')


class CollectiblesButton(BaseButton):
    def __init__(self, driver):
        super(CollectiblesButton, self).__init__(driver)
        self.locator = self.Locator.text_selector('Collectibles')


class CryptoKittiesInCollectiblesButton(BaseButton):
    def __init__(self, driver):
        super(CryptoKittiesInCollectiblesButton, self).__init__(driver)
        self.locator = self.Locator.text_selector('CryptoKitties')


class ViewInCryptoKittiesButton(BaseButton):
    def __init__(self, driver):
        super(ViewInCryptoKittiesButton, self).__init__(driver)
        self.locator = self.Locator.accessibility_id('open-collectible-button')

    def navigate(self):
        from views.web_views.base_web_view import BaseWebView
        return BaseWebView(self.driver)

    def click(self):
        self.wait_for_element(60).click()
        self.driver.info('Tap on View in CryptoKitties')
        return self.navigate()


class BackupRecoveryPhrase(BaseButton):
    def __init__(self, driver):
        super(BackupRecoveryPhrase, self).__init__(driver)
        self.locator = self.Locator.text_selector('Back up your seed phrase')

    def navigate(self):
        from views.profile_view import ProfileView
        return ProfileView(self.driver)


class BackupRecoveryPhraseWarningText(BaseButton):
    def __init__(self, driver):
        super(BackupRecoveryPhraseWarningText, self).__init__(driver)
        self.locator = self.Locator.accessibility_id('back-up-your-seed-phrase-warning')


class MultiaccountMoreOptions(BaseButton):
    def __init__(self, driver):
        super(MultiaccountMoreOptions, self).__init__(driver)
        self.locator = self.Locator.accessibility_id('accounts-more-options')


class AccountElementButton(BaseButton):
    def __init__(self, driver, account_name):
        super(AccountElementButton, self).__init__(driver)
        self.locator = self.Locator.xpath_selector(
            "//*[@content-desc='accountcard%s']" % account_name)

    def color_matches(self, expected_color_image_name: str):
        amount_text = BaseText(self.driver)
        amount_text.locator = amount_text.Locator.xpath_selector(self.locator.value + "//*[@text=' USD']")
        return amount_text.is_element_image_equals_template(expected_color_image_name)

class StatusAccountTotalValueText(BaseText):
    def __init__(self, driver):
        super(StatusAccountTotalValueText, self).__init__(driver)
        self.locator = self.Locator.accessibility_id('account-total-value')



class SendTransactionButton(BaseButton):
    def __init__(self, driver):
        super(SendTransactionButton, self).__init__(driver)
        self.locator = self.Locator.xpath_selector("//*[@text='Send']")

    def navigate(self):
        from views.send_transaction_view import SendTransactionView
        return SendTransactionView(self.driver)

    def click(self):
        self.find_element().click()
        self.driver.info('Tap on %s' % self.name)
        return self.navigate()


class ReceiveTransactionButton(BaseButton):
    def __init__(self, driver):
        super(ReceiveTransactionButton, self).__init__(driver)
        self.locator = self.Locator.xpath_selector("//*[@text='Receive']")

    def navigate(self):
        from views.send_transaction_view import SendTransactionView
        return SendTransactionView(self.driver)


class AddCustomTokenButton(BaseButton):
    def __init__(self, driver):
        super(AddCustomTokenButton, self).__init__(driver)
        self.locator = self.Locator.xpath_selector("//*[@text='Add custom token']")

    def navigate(self):
        from views.add_custom_token_view import AddCustomTokenView
        return AddCustomTokenView(self.driver)


class AddAccountButton(BaseButton):
    def __init__(self, driver):
        super(AddAccountButton, self).__init__(driver)
        self.locator = self.Locator.accessibility_id('add-new-account')


class GenerateAnAccountButton(BaseButton):
    def __init__(self, driver):
        super(GenerateAnAccountButton, self).__init__(driver)
        self.locator = self.Locator.accessibility_id('add-account-sheet-generate')

class AddAWatchOnlyAddressButton(BaseButton):
    def __init__(self, driver):
        super(AddAWatchOnlyAddressButton, self).__init__(driver)
        self.locator = self.Locator.accessibility_id('add-account-sheet-watch')

class EnterASeedPhraseButton(BaseButton):
    def __init__(self, driver):
        super(EnterASeedPhraseButton, self).__init__(driver)
        self.locator = self.Locator.accessibility_id('add-account-sheet-seed')

class EnterAPrivateKeyButton(BaseButton):
    def __init__(self, driver):
        super(EnterAPrivateKeyButton, self).__init__(driver)
        self.locator = self.Locator.accessibility_id('add-account-sheet-private-key')

class EnterAddressInput(BaseEditBox):
    def __init__(self, driver):
        super(EnterAddressInput, self).__init__(driver)
        self.locator = self.Locator.accessibility_id('add-account-enter-watch-address')

class EnterSeedPhraseInput(BaseEditBox):
    def __init__(self, driver):
        super(EnterSeedPhraseInput, self).__init__(driver)
        self.locator = self.Locator.accessibility_id('add-account-enter-seed')

class EnterPrivateKeyInput(BaseEditBox):
    def __init__(self, driver):
        super(EnterPrivateKeyInput, self).__init__(driver)
        self.locator = self.Locator.accessibility_id('add-account-enter-private-key')

class DeleteAccountButton(BaseButton):
    def __init__(self, driver):
        super(DeleteAccountButton, self).__init__(driver)
        self.locator = self.Locator.text_selector('Delete account')



class EnterYourPasswordInput(BaseEditBox):
    def __init__(self, driver):
        super(EnterYourPasswordInput, self).__init__(driver)
        self.locator = self.Locator.accessibility_id('add-account-enter-password')



class AccountNameInput(BaseEditBox):
    def __init__(self, driver):
        super(AccountNameInput, self).__init__(driver)
        self.locator = self.Locator.accessibility_id('enter-account-name')


class AccountColorButton(BaseButton):
    def __init__(self, driver):
        super(AccountColorButton, self).__init__(driver)
        self.locator = self.Locator.xpath_selector("//android.widget.TextView[@text='Account color']"
                                                   "/following-sibling::android.view.ViewGroup[1]")

    def select_color_by_position(self, position: int):
        self.click()
        self.driver.find_element_by_xpath(
            "((//android.widget.ScrollView)[last()]/*/*)[%s]" % str(position+1)).click()

# Add account on Generate An Account screen
class AddAccountGenerateAnAccountButton(BaseButton):
    def __init__(self, driver):
        super(AddAccountGenerateAnAccountButton, self).__init__(driver)
        self.locator = self.Locator.accessibility_id('add-account-add-account-button')

class AccountSettingsButton(BaseButton):
    def __init__(self, driver):
        super(AccountSettingsButton, self).__init__(driver)
        self.locator = self.Locator.text_selector('Account settings')

class ApplySettingsButton(BaseButton):
    def __init__(self, driver):
        super(ApplySettingsButton, self).__init__(driver)
        self.locator = self.Locator.text_selector('Apply')

class WalletView(BaseView):
    def __init__(self, driver):
        super(WalletView, self).__init__(driver)
        self.driver = driver

        self.send_transaction_button = SendTransactionButton(self.driver)
        self.transaction_history_button = TransactionHistoryButton(self.driver)
        self.usd_total_value = UsdTotalValueText(self.driver)

        self.send_transaction_request = SendTransactionRequestButton(self.driver)
        self.receive_transaction_button = ReceiveTransactionButton(self.driver)

        self.send_request_button = SendRequestButton(self.driver)
        self.options_button = OptionsButton(self.driver)
        self.manage_assets_button = ManageAssetsButton(self.driver)
        self.scan_tokens_button = ScanTokensButton(self.driver)
        self.stt_check_box = STTCheckBox(self.driver)
        self.all_assets_full_names = AssetFullNameInAssets(self.driver)
        self.all_assets_symbols = AssetSymbolInAssets(self.driver)
        self.currency_item_text = CurrencyItemText(self.driver)


        self.qr_code_image = QRCodeImage(self.driver)
        self.address_text = AddressText(self.driver)

        self.set_up_button = SetUpButton(self.driver)
        self.sign_in_phrase = SignInPhraseText(self.driver)
        self.remind_me_later_button = RemindMeLaterButton(self.driver)

        self.total_amount_text = TotalAmountText(self.driver)
        self.currency_text = CurrencyText(self.driver)
        self.backup_recovery_phrase = BackupRecoveryPhrase(self.driver)
        self.backup_recovery_phrase_warning_text = BackupRecoveryPhraseWarningText(self.driver)

        self.add_custom_token_button = AddCustomTokenButton(self.driver)

        # elements for multiaccount
        self.multiaccount_more_options = MultiaccountMoreOptions(self.driver)
        self.accounts_status_account = AccountElementButton(self.driver, account_name="Status account")
        self.collectibles_button = CollectiblesButton(self.driver)
        self.cryptokitties_in_collectibles_button = CryptoKittiesInCollectiblesButton(self.driver)
        self.view_in_cryptokitties_button = ViewInCryptoKittiesButton(self.driver)
        self.set_currency_button = SetCurrencyButton(self.driver)
        self.add_account_button = AddAccountButton(self.driver)
        self.generate_an_account_button = GenerateAnAccountButton(self.driver)
        self.add_watch_only_address_button = AddAWatchOnlyAddressButton(self.driver)
        self.enter_a_seed_phrase_button = EnterASeedPhraseButton(self.driver)
        self.enter_a_private_key_button = EnterAPrivateKeyButton(self.driver)
        self.enter_address_input = EnterAddressInput(self.driver)
        self.enter_seed_phrase_input = EnterSeedPhraseInput(self.driver)
        self.enter_a_private_key_input = EnterPrivateKeyInput(self.driver)
        self.delete_account_button = DeleteAccountButton(self.driver)
        self.enter_your_password_input = EnterYourPasswordInput(self.driver)
        self.account_name_input = AccountNameInput(self.driver)
        self.account_color_button = AccountColorButton(self.driver)
        self.add_account_generate_account_button = AddAccountGenerateAnAccountButton(self.driver)
        self.status_account_total_usd_value = StatusAccountTotalValueText(self.driver)
        self.scan_qr_button = ScanQRButton(self.driver)

        # individual account settings
        self.account_settings_button = AccountSettingsButton(self.driver)
        self.apply_settings_button = ApplySettingsButton(self.driver)

    def get_usd_total_value(self):
        import re
        return float(re.sub('[~,]', '', self.usd_total_value.text))

    def get_account_options_by_name(self, account_name='Status account'):
        return AccountOptionsButton(self.driver, account_name)

    def get_asset_amount_by_name(self, asset: str):
        asset_value = AssetText(self.driver, asset)
        asset_value.scroll_to_element()
        try:
            return float(asset_value.text.split()[0])
        except ValueError:
            return 0.0

    def verify_currency_balance(self, expected_rate: int, errors: list):
        usd = self.get_usd_total_value()
        eth = self.get_asset_amount_by_name('ETH')
        expected_usd = round(eth * expected_rate, 2)
        percentage_diff = abs((usd - expected_usd) / ((usd + expected_usd) / 2)) * 100
        if percentage_diff > 2:
            errors.append('Difference between current (%s) and expected (%s) USD balance > 2%%!!' % (usd, expected_usd))
        else:
            self.driver.info('Current USD balance %s is ok' % usd)

    def wait_balance_is_equal_expected_amount(self, asset ='ETH', expected_balance=0.1, wait_time=300):
        counter = 0
        while True:
            if counter >= wait_time:
                self.driver.fail('Balance is not changed during %s seconds!' % wait_time)
            elif self.get_asset_amount_by_name(asset) != expected_balance:
                counter += 10
                time.sleep(10)
                self.swipe_down()
                self.driver.info('Waiting %s seconds for %s balance update to be equal to %s' % (counter,asset, expected_balance))
            else:
                self.driver.info('Balance for %s is equal to %s' % (asset, expected_balance))
                return

    def wait_balance_is_changed(self, asset ='ETH', initial_balance=0, wait_time=300, scan_tokens=False):
        counter = 0
        while True:
            if counter >= wait_time:
                self.driver.fail('Balance is not changed during %s seconds!' % wait_time)
            elif self.asset_by_name(asset).is_element_present() and self.get_asset_amount_by_name(asset) == initial_balance:
                counter += 10
                time.sleep(10)
                self.swipe_down()
                self.driver.info('Waiting %s seconds for %s to update' % (counter,asset))
            elif not self.asset_by_name(asset).is_element_present(10):
                counter += 10
                time.sleep(10)
                if scan_tokens:
                    self.scan_tokens()
                self.swipe_down()
                self.driver.info('Waiting %s seconds for %s to display asset' % (counter, asset))
            else:
                self.driver.info('Balance is updated!')
                return self

    def get_sign_in_phrase(self):
        return ' '.join([element.text for element in self.sign_in_phrase.find_elements()])

    def set_up_wallet(self):
        phrase = self.sign_in_phrase.string
        self.ok_got_it_button.click()
        return phrase

    def get_wallet_address(self, account_name="Status account"):
        self.wallet_account_by_name(account_name).click()
        self.receive_transaction_button.click_until_presence_of_element(self.qr_code_image)
        address = self.address_text.text
        self.close_share_popup()
        return address

    def wallet_account_by_name(self, account_name):
        return AccountElementButton(self.driver, account_name)

    def asset_by_name(self, asset_name):
        return AssetTextElement(self.driver, asset_name)

    def asset_checkbox_by_name(self, asset_name):
        return AssetCheckBox(self.driver, asset_name)

    def account_options_by_name(self, account_name):
        return AccountOptionsButton(self.driver, account_name)

    def select_asset(self, *args):
        self.multiaccount_more_options.click()
        self.manage_assets_button.click()
        for asset in args:
            self.asset_checkbox_by_name(asset).click()
        self.cross_icon.click()

    def scan_tokens(self, *args):
        self.multiaccount_more_options.click()
        self.scan_tokens_button.click()
        counter = 0
        if args:
            for asset in args:
                while True:
                    if counter >= 20:
                        self.driver.fail('Balance of %s is not changed during 20 seconds!' % asset)
                    elif self.get_asset_amount_by_name(asset) == 0.0:
                        self.multiaccount_more_options.click()
                        self.scan_tokens_button.click()
                        self.driver.info('Trying to scan for tokens one more time and waiting %s seconds for %s '
                                         'to update' % (counter, asset))
                        time.sleep(5)
                        counter += 5
                    else:
                        self.driver.info('Balance of %s is updated!' % asset)
                        return self

    def send_transaction(self, **kwargs):
        send_transaction_view = self.send_transaction_button.click()
        send_transaction_view.select_asset_button.click()
        asset_name = kwargs.get('asset_name', 'ETH').upper()
        asset_button = send_transaction_view.asset_by_name(asset_name)
        send_transaction_view.select_asset_button.click_until_presence_of_element(send_transaction_view.eth_asset_in_select_asset_bottom_sheet_button)
        asset_button.click()
        send_transaction_view.amount_edit_box.click()

        transaction_amount = str(kwargs.get('amount', send_transaction_view.get_unique_amount()))

        send_transaction_view.amount_edit_box.set_value(transaction_amount)
        send_transaction_view.set_recipient_address(kwargs.get('recipient'))
        if kwargs.get('sign_transaction', True):
            send_transaction_view.sign_transaction_button.click()
            send_transaction_view.sign_transaction(keycard=kwargs.get('keycard', False),
                                                   default_gas_price=kwargs.get('default_gas_price', False),
                                                   sender_password=kwargs.get('sender_password', common_password))


    def receive_transaction(self, **kwargs):
        self.receive_transaction_button.click()
        send_transaction_view = self.send_transaction_request.click()
        send_transaction_view.select_asset_button.click()
        asset_name = kwargs.get('asset_name', 'ETH').upper()
        asset_button = send_transaction_view.asset_by_name(asset_name)
        send_transaction_view.select_asset_button.click_until_presence_of_element(asset_button)
        asset_button.click()
        send_transaction_view.amount_edit_box.click()

        transaction_amount = str(kwargs.get('amount')) if kwargs.get('amount') else \
            send_transaction_view.get_unique_amount()

        send_transaction_view.amount_edit_box.set_value(transaction_amount)
        send_transaction_view.confirm()
        send_transaction_view.chose_recipient_button.click()

        recipient = kwargs.get('recipient')
        send_transaction_view.recent_recipients_button.click()
        recent_recipient = send_transaction_view.element_by_text(recipient)
        send_transaction_view.recent_recipients_button.click_until_presence_of_element(recent_recipient)
        recent_recipient.click()
        self.send_request_button.click()

    def collectible_amount_by_name(self, name):
        elm = CollectibleTextElement(self.driver, name)
        elm.scroll_to_element()
        return elm.text

    def set_currency(self, desired_currency='EUR'):
        """
        :param desired_currency: defines a currency designator which is expressed by ISO 4217 code
        """
        self.multiaccount_more_options.click_until_presence_of_element(self.set_currency_button)
        self.set_currency_button.click()
        desired_currency = self.element_by_text_part(desired_currency)
        desired_currency.scroll_to_element()
        desired_currency.click()

    def get_account_by_name(self, account_name: str):
        return AccountElementButton(self.driver, account_name)

    def add_account(self, account_name: str, password: str = common_password, keycard=False):
        self.add_account_button.click()
        self.generate_an_account_button.click()
        self.account_name_input.send_keys(account_name)
        if keycard:
            from views.keycard_view import KeycardView
            keycard_view = KeycardView(self.driver)
            self.add_account_generate_account_button.click()
            keycard_view.enter_default_pin()
        else:
            self.enter_your_password_input.send_keys(password)
            self.add_account_generate_account_button.click()
