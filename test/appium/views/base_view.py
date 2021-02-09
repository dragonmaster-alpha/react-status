import time

import base64
import random
import re
import string
import zbarlight
from PIL import Image
from appium.webdriver.common.touch_action import TouchAction
from datetime import datetime
from io import BytesIO
from selenium.common.exceptions import NoSuchElementException, TimeoutException, StaleElementReferenceException

from support.device_apps import start_web_browser
from tests import common_password, pytest_config_global, geth_log_emulator_path, transl
from views.base_element import Button, BaseElement, EditBox, Text


class BackButton(Button):
    def __init__(self, driver):
        super().__init__(driver, accessibility_id="back-button")

    def click(self, times_to_click: int = 1):
        for _ in range(times_to_click):
            self.find_element().click()
        return self.navigate()


class AllowButton(Button):
    def __init__(self, driver):
        super().__init__(driver, translation_id="allow", uppercase=True)

    def click(self, times_to_click=3):
        try:
            for _ in range(times_to_click):
                self.find_element().click()
        except NoSuchElementException:
            pass


class TabButton(Button):

    @property
    def counter(self):
        class Counter(Text):
            def __init__(self, driver, parent_locator):
                super().__init__(driver,
                                 xpath="//*[@content-desc='%s']//android.view.ViewGroup[2]/android.widget.TextView" % parent_locator)
        return Counter(self.driver, self.locator)

    @property
    def public_unread_messages(self):
        class PublicChatUnreadMessages(BaseElement):
            def __init__(self, driver):
                super().__init__(driver, accessibility_id="public-unread-badge")
        return PublicChatUnreadMessages(self.driver)


class HomeButton(TabButton):
    def __init__(self, driver):
        super().__init__(driver, accessibility_id="home-tab-button")

    def navigate(self):
        from views.home_view import HomeView
        return HomeView(self.driver)

    def click(self, desired_view='home'):
        element = None
        from views.home_view import HomeView
        from views.chat_view import ChatView
        if desired_view == 'home':
            element = HomeView(self.driver).plus_button
        elif desired_view == 'chat':
            element = ChatView(self.driver).chat_message_input
        elif desired_view == 'other_user_profile':
            element = ChatView(self.driver).profile_nickname
        self.click_until_presence_of_element(element)
        return self.navigate()


class DappTabButton(TabButton):
    def __init__(self, driver):
        super().__init__(driver, accessibility_id="dapp-tab-button")

    def navigate(self):
        from views.dapps_view import DappsView
        return DappsView(self.driver)

    def click(self, desired_element_text=None):

        from views.dapps_view import DappsView
        if desired_element_text is None:
            self.click_until_presence_of_element(DappsView(self.driver).enter_url_editbox)
        elif desired_element_text == 'webview':
            self.find_element().click()
        else:
            base_view = BaseView(self.driver)
            self.click_until_presence_of_element(base_view.element_by_text_part(desired_element_text))
        return self.navigate()


class WalletButton(TabButton):
    def __init__(self, driver):
        super().__init__(driver, accessibility_id="wallet-tab-button")

    def navigate(self):
        from views.wallet_view import WalletView
        return WalletView(self.driver)

    def click(self):
        from views.wallet_view import WalletView
        self.click_until_presence_of_element(WalletView(self.driver).multiaccount_more_options)
        return self.navigate()


class ProfileButton(TabButton):
    def __init__(self, driver):
        super().__init__(driver, accessibility_id="profile-tab-button")

    def navigate(self):
        from views.profile_view import ProfileView
        return ProfileView(self.driver)

    def click(self, desired_element_text = 'privacy'):
        from views.profile_view import ProfileView
        if desired_element_text == 'privacy':
            self.click_until_presence_of_element(ProfileView(self.driver).privacy_and_security_button)
        else:
            base_view = BaseView(self.driver)
            self.click_until_presence_of_element(base_view.element_by_text_part(desired_element_text))
        return self.navigate()


class StatusButton(TabButton):
    def __init__(self, driver):
        super().__init__(driver, accessibility_id="status-tab-button")

    def navigate(self):
        from views.chat_view import ChatView
        return ChatView(self.driver)


class SendMessageButton(Button):
    def __init__(self, driver):
        super().__init__(driver, accessibility_id="send-message-button")


class ProgressBar(BaseElement):
    def __init__(self, driver, parent_locator: str = ''):
        super().__init__(driver, xpath="%s//android.widget.ProgressBar" % parent_locator)


class AssetButton(Button):
    def __init__(self, driver, asset_name):
        super().__init__(driver, xpath="(//*[@content-desc=':%s-asset-value'])[1]" % asset_name)
        self.asset_name = asset_name

    @property
    def name(self):
        return self.asset_name + self.__class__.__name__

    def click(self):
        self.wait_for_element().click()

class OpenInStatusButton(Button):
    def __init__(self, driver):
        super().__init__(driver, translation_id="browsing-open-in-status")

    def click(self):
        self.wait_for_visibility_of_element()
        # using sleep is wrong, but implicit wait for element can't help in particular case
        time.sleep(3)
        self.swipe_to_web_element()
        self.wait_for_element().click()


class EnterQRcodeEditBox(EditBox):
    def __init__(self, driver):
        super().__init__(driver, translation_id="type-a-message")

    def scan_qr(self, value):
        self.set_value(value)
        base_view = BaseView(self.driver)
        base_view.ok_button.click()

    def click(self):
        self.wait_for_element().click()
        self.wait_for_invisibility_of_element()


class AirplaneModeButton(Button):
    def __init__(self, driver):
        super().__init__(driver, accessibility_id="Airplane mode")

    def click(self):
        action = TouchAction(self.driver)
        action.press(None, 50, 0).move_to(None, 50, 300).perform()
        super(AirplaneModeButton, self).click()
        self.driver.press_keycode(4)


class BaseView(object):
    def __init__(self, driver):
        self.driver = driver
        self.send_message_button = SendMessageButton(self.driver)

        # Tabs
        self.home_button = HomeButton(self.driver)
        self.wallet_button = WalletButton(self.driver)
        self.profile_button = ProfileButton(self.driver)
        self.dapp_tab_button = DappTabButton(self.driver)
        self.status_button = StatusButton(self.driver)

        self.yes_button = Button(self.driver, xpath="//*[@text='YES' or @text='GOT IT']")
        self.no_button = Button(self.driver, translation_id="no")
        self.back_button = BackButton(self.driver)
        self.allow_button = AllowButton(self.driver)
        self.deny_button = Button(self.driver, translation_id="deny", uppercase=True)
        self.continue_button = Button(self.driver, translation_id="continue", uppercase=True)
        self.ok_button = Button(self.driver, xpath="//*[@text='OK' or @text='Ok']")
        self.next_button = Button(self.driver, translation_id="next")
        self.add_button = Button(self.driver, translation_id="add")
        self.save_button = Button(self.driver, translation_id="save")
        self.done_button = Button(self.driver, translation_id="done") #self.locator = self.Locator.xpath_selector("//*[@content-desc='done-button' or contains(@text, 'Done')]")
        self.delete_button = Button(self.driver, translation_id="delete", uppercase=True)
        self.ok_continue_button = Button(self.driver, xpath="//*[@text='OK, CONTINUE' or @text='Okay, continue']")
        self.discard_button = Button(self.driver, xpath="//*[@text='DISCARD']")
        self.confirm_button = Button(self.driver, translation_id='confirm', uppercase=True)
        self.connection_status = Text(self.driver,
                                      xpath="//*[@content-desc='connection-status-text']/android.widget.TextView")
        self.cross_icon = Button(self.driver, xpath="(//android.view.ViewGroup[@content-desc='icon'])[1]")
        self.native_close_button = Button(self.driver, id="android:id/aerr_close")
        self.show_roots_button = Button(self.driver, accessibility_id="Show roots")
        self.get_started_button = Button(self.driver, translation_id="get-started")
        self.ok_got_it_button = Button(self.driver, translation_id="ok-got-it")
        self.progress_bar = ProgressBar(self.driver)
        self.cross_icon_inside_welcome_screen_button = Button(self.driver, accessibility_id='hide-home-button')
        self.status_in_background_button = Button(self.driver, xpath="//*[contains(@content-desc,'Status')]")
        self.cancel_button = Button(self.driver, translation_id="cancel", uppercase=True)
        self.search_input = EditBox(self.driver, accessibility_id="search-input")
        self.share_button = Button(self.driver, accessibility_id="share-my-contact-code-button")

        # external browser
        self.open_in_status_button = OpenInStatusButton(self.driver)

        self.apps_button = Button(self.driver, accessibility_id="Apps")
        self.status_app_icon = Button(self.driver, translation_id="status")
        self.airplane_mode_button = AirplaneModeButton(self.driver)
        self.enter_qr_edit_box = EnterQRcodeEditBox(self.driver)

        self.element_types = {
            'base': BaseElement,
            'button': Button,
            'edit_box': EditBox,
            'text': Text
        }

    @property
    def status_account_name(self):
        return self.get_translation_by_key('ethereum-account')

    def accept_agreements(self):
        iterations = int()
        self.close_native_device_dialog("Messages has stopped")
        self.close_native_device_dialog("YouTube")
        while iterations <= 1 and (self.ok_button.is_element_displayed(2) or
                                   self.continue_button.is_element_displayed(2)):
            for button in self.ok_button, self.continue_button:
                try:
                    button.wait_for_element(3)
                    button.click()
                except (NoSuchElementException, TimeoutException):
                    pass
            iterations += 1

    @staticmethod
    def get_translation_by_key(id):
        return transl[id]

    def rooted_device_continue(self):
        try:
            self.continue_button.wait_for_element(3)
            self.continue_button.click()
        except (NoSuchElementException, TimeoutException):
            pass

    def close_native_device_dialog(self, alert_text_part):
        element = self.element_by_text_part(alert_text_part)
        if element.is_element_present(1):
            self.driver.info("**Closing '%s' alert..." % alert_text_part)
            self.native_close_button.click()

    @property
    def logcat(self):
        logcat = self.driver.get_log("logcat")
        if len(logcat) > 1000:
            return str([i for i in logcat if not ('appium' in str(i).lower() or ':1.000000.' in str(i).lower())])
        raise TimeoutError('Logcat is empty')

    def confirm(self):
        self.driver.info("*Tap 'Confirm' on native keyboard*")
        self.driver.press_keycode(66)

    def confirm_until_presence_of_element(self, desired_element, attempts=3):
        counter = 0
        while not desired_element.is_element_present(1) and counter <= attempts:
            try:
                self.confirm()
                self.driver.info('Wait for %s' % desired_element.name)
                desired_element.wait_for_element(5)
                return
            except TimeoutException:
                counter += 1

    def just_fyi(self, string):
        self.driver.info('=========================================================================')
        self.driver.info(string)
        self.driver.info('=========================================================================')

    def click_system_back_button(self, times=1):
        self.driver.info('*Click system back button*')
        for _ in range(times):
            self.driver.press_keycode(4)

    def put_app_to_background_and_back(self, time_in_background=1):
        self.driver.info('*Put app to background and back*')
        self.driver.press_keycode(187)
        time.sleep(time_in_background)
        self.status_in_background_button.click()

    def click_system_home_button(self):
        self.driver.info('*Press system Home button*')
        self.driver.press_keycode(3)

    def put_app_to_background(self):
        self.driver.info('*App to background*')
        self.driver.press_keycode(187)

    def cut_text(self):
        self.driver.info('*Cut text*')
        self.driver.press_keycode(277)

    def copy_text(self):
        self.driver.info('*Copy text*')
        self.driver.press_keycode(278)

    def paste_text(self):
        self.driver.info('*Paste text*')
        self.driver.press_keycode(279)

    def send_as_keyevent(self, string):
        self.driver.info("*Sending as keyevent* `%s`" % string)
        keys = {'0': 7, '1': 8, '2': 9, '3': 10, '4': 11, '5': 12, '6': 13, '7': 14, '8': 15, '9': 16,

                ',': 55, '-': 69, '+': 81, '.': 56, '/': 76, '\\': 73, ';': 74, ' ': 62,
                '[': 71, ']': 72, '=': 70, '\n': 66, '_': [69, 5], ':': [74, 5],

                'a': 29, 'b': 30, 'c': 31, 'd': 32, 'e': 33, 'f': 34, 'g': 35, 'h': 36, 'i': 37, 'j': 38,
                'k': 39, 'l': 40, 'm': 41, 'n': 42, 'o': 43, 'p': 44, 'q': 45, 'r': 46, 's': 47, 't': 48,
                'u': 49, 'v': 50, 'w': 51, 'x': 52, 'y': 53, 'z': 54}
        time.sleep(3)
        for i in string:
            if i.isalpha() and i.isupper():
                keycode, metastate = keys[i.lower()], 64  # META_SHIFT_LEFT_ON Constant Value: 64. Example: i='n' -> 'N'
            elif type(keys[i]) is list:
                keycode, metastate = keys[i][0], keys[i][1]
            else:
                keycode, metastate = keys[i], None
            self.driver.press_keycode(keycode=keycode, metastate=metastate)

    def element_by_text(self, text, element_type='button'):
        element = self.element_types[element_type](self.driver)
        element.locator = '//*[@text="%s"]' % text
        return element

    def element_by_text_part(self, text, element_type='button'):
        element = self.element_types[element_type](self.driver)
        element.locator = '//*[contains(@text, "' + text + '")]'
        return element

    def element_starts_with_text(self, text, element_type='button'):
        element = self.element_types[element_type](self.driver, xpath="//*[starts-with(@text,'%s')]" % text)
        return element

    def element_by_translation_id(self, id, element_type='button', uppercase=False):
        element = self.element_types[element_type](self.driver, translation_id=id, uppercase=uppercase)
        return element

    def wait_for_element_starts_with_text(self, text, wait_time=60):
        element = Button(self.driver, xpath="//*[starts-with(@text,'%s')]" % text)
        return element.wait_for_element(wait_time)

    def swipe_up(self):
        self.driver.info("*Swiping up*")
        size = self.driver.get_window_size()
        self.driver.swipe(size["width"]*0.5, size["height"]*0.8, size["width"]*0.5, size["height"]*0.2)

    def swipe_down(self):
        self.driver.info("*Swiping down*")
        size = self.driver.get_window_size()
        self.driver.swipe(size["width"]*0.5, size["height"]*0.2, size["width"]*0.5, size["height"]*0.8)

    def swipe_left(self):
        self.driver.info("*Swiping left*")
        size = self.driver.get_window_size()
        self.driver.swipe(size["width"]*0.8, size["height"]*0.8, size["width"]*0.2, size["height"]*0.8)

    def swipe_right(self):
        self.driver.info("*Swiping right*")
        size = self.driver.get_window_size()
        self.driver.swipe(size["width"]*0.2, size["height"]*0.8, size["width"]*0.8, size["height"]*0.8)

    def get_status_test_dapp_view(self):
        from views.web_views.status_test_dapp import StatusTestDAppView
        return StatusTestDAppView(self.driver)

    def get_home_view(self):
        from views.home_view import HomeView
        return HomeView(self.driver)

    def get_chat_view(self):
        from views.chat_view import ChatView
        return ChatView(self.driver)

    def get_sign_in_view(self):
        from views.sign_in_view import SignInView
        return SignInView(self.driver)

    def get_send_transaction_view(self):
        from views.send_transaction_view import SendTransactionView
        return SendTransactionView(self.driver)

    def get_base_web_view(self):
        from views.web_views.base_web_view import BaseWebView
        return BaseWebView(self.driver)

    def get_profile_view(self):
        from views.profile_view import ProfileView
        return ProfileView(self.driver)

    def get_wallet_view(self):
        from views.wallet_view import WalletView
        return WalletView(self.driver)

    @staticmethod
    def get_unique_amount():
        return '0.00%s' % datetime.now().strftime('%-d%-H%-M%-S').strip('0')

    @staticmethod
    def get_random_chat_name():
        return ''.join(random.choice(string.ascii_lowercase) for _ in range(7))

    def get_text_from_qr(self):
        image = Image.open(BytesIO(base64.b64decode(self.driver.get_screenshot_as_base64())))
        image.load()
        try:
            return str(zbarlight.scan_codes('qrcode', image)[0])[2:][:132]
        except IndexError:
            raise BaseException('No data in QR code')

    def get_back_to_home_view(self, times_to_click_on_back_btn=3):
        counter = 0
        while BackButton(self.driver).is_element_displayed(2):
            try:
                if counter >= times_to_click_on_back_btn:
                    break
                self.back_button.click()
                counter += 1
            except (NoSuchElementException, TimeoutException):
                continue
        return self.home_button.click()

    def relogin(self, password=common_password):
        try:
            profile_view = self.profile_button.click()
        except (NoSuchElementException, TimeoutException):
            self.get_back_to_home_view()
            profile_view = self.profile_button.click()
        profile_view.logout()
        sign_in_view = self.get_sign_in_view()
        sign_in_view.sign_in(password)

    def close_share_popup(self):
        self.driver.info("*Closing share popup*")
        TouchAction(self.driver).tap(None, 255, 104, 1).perform()
        time.sleep(3)


    def get_public_key_and_username(self, return_username=False):
        self.driver.info("**Get public key and username**")
        profile_view = self.profile_button.click()
        default_username = profile_view.default_username_text.text
        profile_view.share_my_profile_button.click()
        profile_view.public_key_text.wait_for_visibility_of_element()
        public_key = profile_view.public_key_text.text
        self.click_system_back_button()
        user_data = (public_key, default_username) if return_username else public_key
        return user_data

    def share_via_messenger(self):
        self.driver.info("**Sharing via messenger**")
        self.element_by_text_part("Direct share").wait_for_element()
        self.element_by_text('Messages').click()
        self.element_by_text('New message').click()
        self.send_as_keyevent('+0100100101')
        self.confirm()

    def click_upon_push_notification_by_text(self, text):
        self.driver.info("**Click on PN with text:** `%s`" % text)
        self.element_by_text_part(text).click()
        return self.get_chat_view()

    def reconnect(self):
        self.driver.info("**Reconnecting**")
        connect_status = self.connection_status
        for i in range(3):
            if connect_status.is_element_displayed(5, ignored_exceptions=StaleElementReferenceException):
                if 'Tap to reconnect' in connect_status.text:
                    try:
                        connect_status.click()
                    except AttributeError:
                        pass
                    try:
                        connect_status.wait_for_invisibility_of_element()
                    except TimeoutException as e:
                        if i == 2:
                            e.msg = "Device %s: Can't reconnect to mail server after 3 attempts" % self.driver.number
                            raise e

    def find_values_in_logcat(self, **kwargs):
        logcat = self.logcat
        items_in_logcat = list()
        for key, value in kwargs.items():
            self.driver.info("**Checking in logcat for:** `%s`" % value)
            escaped_value = re.escape(value)
            if re.findall(r'\W%s$|\W%s\W' % (escaped_value, escaped_value), logcat):
                items_in_logcat.append('%s in logcat!!!' % key.capitalize())
        return items_in_logcat

    def find_values_in_geth(self, *args):
        b64_log = self.driver.pull_file(geth_log_emulator_path)
        file = base64.b64decode(b64_log)
        result = False
        for value in args:
            self.driver.info('**Checking in geth for:** `%s`' % value)
            if re.findall('%s*' % value, file.decode("utf-8")):
                self.driver.info('%s was found in geth.log' % value)
                result = True
        return result


    def asset_by_name(self, asset_name):
        return AssetButton(self.driver, asset_name)

    def open_notification_bar(self):
        self.driver.open_notifications()

    def toggle_airplane_mode(self):
        self.driver.info("**Toggling airplane mode**")
        self.airplane_mode_button.click()
        self.close_native_device_dialog("MmsService")

    def toggle_mobile_data(self):
        self.driver.info("**Toggling mobile data**")
        self.driver.start_activity(app_package='com.android.settings', app_activity='.Settings')
        network_and_internet = self.element_by_text('Network & internet')
        network_and_internet.wait_for_visibility_of_element()
        network_and_internet.click()
        toggle=Button(self.driver, accessibility_id='Wi‑Fi')
        toggle.wait_for_visibility_of_element()
        toggle.click()
        self.driver.back()
        self.driver.back()

    def open_universal_web_link(self, deep_link):
        start_web_browser(self.driver)
        self.driver.info('**Open web link via web browser:** `%s`' % deep_link)
        self.driver.get(deep_link)

    def upgrade_app(self):
        self.driver.info("**Upgrading apk to apk_upgrade**")
        self.driver.install_app(pytest_config_global['apk_upgrade'], replace=True)

    def search_by_keyword(self, keyword):
        self.driver.info('**Search for** `%s`' % keyword)
        self.search_input.click()
        self.search_input.send_keys(keyword)

    # Method-helper
    def write_page_source_to_file(self, full_path_to_file):
        string_source = self.driver.page_source
        source = open(full_path_to_file, "a+")
        source.write(string_source)

