from selenium.common.exceptions import NoSuchElementException

from tests import get_current_time, common_password
from views.base_element import BaseButton, BaseEditBox
from views.base_view import BaseView


class AccountButton(BaseButton):

    def __init__(self, driver):
        super(AccountButton, self).__init__(driver)
        self.locator = self.Locator.xpath_selector("//*[contains(@text,'0x')]")


class PasswordInput(BaseEditBox):

    def __init__(self, driver):
        super(PasswordInput, self).__init__(driver)
        self.locator = self.Locator.xpath_selector("//android.widget.TextView[@text='Password']"
                                                   "/following-sibling::android.view.ViewGroup/android.widget.EditText")


class SignInButton(BaseButton):

    def __init__(self, driver):
        super(SignInButton, self).__init__(driver)
        self.locator = self.Locator.xpath_selector("//android.widget.TextView[@text='Sign in' or @text='SIGN IN']")

    def navigate(self):
        from views.home_view import HomeView
        return HomeView(self.driver)


class RecoverAccessButton(BaseButton):

    def __init__(self, driver):
        super(RecoverAccessButton, self).__init__(driver)
        self.locator = self.Locator.xpath_selector("//android.widget.TextView[@text='Recover access']")

    def navigate(self):
        from views.recover_access_view import RecoverAccessView
        return RecoverAccessView(self.driver)


class CreateAccountButton(BaseButton):
    def __init__(self, driver):
        super(CreateAccountButton, self).__init__(driver)
        self.locator = self.Locator.xpath_selector("//*[@text='Create account' or @text='Create new account']")


class IHaveAccountButton(RecoverAccessButton):
    def __init__(self, driver):
        super(IHaveAccountButton, self).__init__(driver)
        self.locator = self.Locator.xpath_selector("//*[@text='I already have an account']")


class AddExistingAccountButton(RecoverAccessButton):
    def __init__(self, driver):
        super(AddExistingAccountButton, self).__init__(driver)
        self.locator = self.Locator.xpath_selector("//*[@text='Add existing account']")


class ConfirmPasswordInput(BaseEditBox):
    def __init__(self, driver):
        super(ConfirmPasswordInput, self).__init__(driver)
        self.locator = self.Locator.xpath_selector("//android.widget.TextView[@text='Confirm']"
                                                   "/following-sibling::android.view.ViewGroup/android.widget.EditText")


class NameInput(BaseEditBox):
    def __init__(self, driver):
        super(NameInput, self).__init__(driver)
        self.locator = self.Locator.xpath_selector("//android.widget.EditText")


class OtherAccountsButton(BaseButton):

    def __init__(self, driver):
        super(OtherAccountsButton, self).__init__(driver)
        self.locator = self.Locator.text_selector('Other accounts')


class PrivacyPolicyLink(BaseButton):
    def __init__(self, driver):
        super(PrivacyPolicyLink, self).__init__(driver)
        self.locator = self.Locator.text_part_selector('Privacy Policy')

    def navigate(self):
        from views.web_views.base_web_view import BaseWebView
        return BaseWebView(self.driver)


class SignInView(BaseView):

    def __init__(self, driver, skip_popups=True):
        super(SignInView, self).__init__(driver)
        self.driver = driver
        if skip_popups:
            self.accept_agreements()

        self.account_button = AccountButton(self.driver)
        self.password_input = PasswordInput(self.driver)
        self.sign_in_button = SignInButton(self.driver)
        self.recover_access_button = RecoverAccessButton(self.driver)

        # new design
        self.create_account_button = CreateAccountButton(self.driver)
        self.i_have_account_button = IHaveAccountButton(self.driver)
        self.add_existing_account_button = AddExistingAccountButton(self.driver)
        self.confirm_password_input = ConfirmPasswordInput(self.driver)
        self.name_input = NameInput(self.driver)
        self.other_accounts_button = OtherAccountsButton(self.driver)
        self.privacy_policy_link = PrivacyPolicyLink(self.driver)

    def create_user(self, username: str = '', password=common_password):
        self.create_account_button.click()
        self.password_input.set_value(password)
        self.next_button.click()
        self.confirm_password_input.set_value(password)
        self.next_button.click()

        self.element_by_text_part('Display name').wait_for_element(60)
        username = username if username else 'user_%s' % get_current_time()
        self.name_input.set_value(username)

        self.next_button.click()
        self.get_started_button.click()
        return self.get_home_view()

    def recover_access(self, passphrase: str, password: str = common_password):
        if self.other_accounts_button.is_element_displayed():
            self.other_accounts_button.click()
            recover_access_view = self.add_existing_account_button.click()
        else:
            recover_access_view = self.i_have_account_button.click()
        recover_access_view.passphrase_input.click()
        recover_access_view.passphrase_input.set_value(passphrase)
        recover_access_view.password_input.click()
        recover_access_view.password_input.set_value(password)
        recover_access_view.sign_in_button.click_until_presence_of_element(recover_access_view.home_button)
        return self.get_home_view()

    def sign_in(self, password=common_password):
        if self.ok_button.is_element_displayed():
            self.ok_button.click()
        self.password_input.set_value(password)
        return self.sign_in_button.click()

    def click_account_by_position(self, position: int):
        if self.ok_button.is_element_displayed():
            self.ok_button.click()
        try:
            self.account_button.find_elements()[position].click()
        except IndexError:
            raise NoSuchElementException(
                'Device %s: Unable to find account by position %s' % (self.driver.number, position)) from None

    def open_weblink_and_login(self, url_weblink):
        self.open_universal_web_link(url_weblink)
        self.sign_in()
