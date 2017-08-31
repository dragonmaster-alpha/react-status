from views.base_view import BaseViewObject
from views.base_element import *


class ProfileButton(BaseButton):

    def __init__(self, driver):
        super(ProfileButton, self).__init__(driver)
        self.locator = self.Locator.xpath_selector(
            "//android.support.v4.view.ViewPager//android.view.ViewGroup[2]/android.view.ViewGroup[1]/android.view.View"
        )


class ProfileIcon(BaseButton):

    def __init__(self, driver):
        super(ProfileIcon, self).__init__(driver)
        self.locator = self.Locator.xpath_selector(
            "//android.widget.EditText/../android.view.ViewGroup")

    def navigate(self):
        from views.profile import ProfileViewObject
        return ProfileViewObject(self.driver)


class PlusButton(BaseButton):

    def __init__(self, driver):
        super(PlusButton, self).__init__(driver)
        self.locator = self.Locator.xpath_selector(
            "//android.widget.TextView[@text='+']")


class AddNewContactButton(BaseButton):

    def __init__(self, driver):
        super(AddNewContactButton, self).__init__(driver)
        self.locator = self.Locator.xpath_selector(
            "//android.widget.TextView[@text='Add new contact']")


class NewGroupChatButton(BaseButton):

    def __init__(self, driver):
        super(NewGroupChatButton, self).__init__(driver)
        self.locator = self.Locator.xpath_selector(
            "//android.widget.TextView[@text='New group chat']")

    class AugurContact(BaseButton):
        def __init__(self, driver):
            super(NewGroupChatButton.AugurContact, self).__init__(driver)
            self.locator = self.Locator.xpath_selector(
                "//android.widget.TextView[@text='Augur']")

    class JarradContact(BaseButton):
        def __init__(self, driver):
            super(NewGroupChatButton.JarradContact, self).__init__(driver)
            self.locator = self.Locator.xpath_selector(
                "//android.widget.TextView[@text='Jarrad']")

    class NextButton(BaseButton):
        def __init__(self, driver):
            super(NewGroupChatButton.NextButton, self).__init__(driver)
            self.locator = self.Locator.xpath_selector(
                "//android.widget.TextView[@text='NEXT']")

    class NameEditBox(BaseEditBox):
        def __init__(self, driver):
            super(NewGroupChatButton.NameEditBox, self).__init__(driver)
            self.locator = \
                self.Locator.xpath_selector("//android.widget.EditText[@NAF='true']")

    class SaveButton(BaseButton):
        def __init__(self, driver):
            super(NewGroupChatButton.SaveButton, self).__init__(driver)
            self.locator = self.Locator.xpath_selector(
                "//android.widget.TextView[@text='SAVE']")


class PublicKeyEditBox(BaseEditBox):

    def __init__(self, driver):
        super(PublicKeyEditBox, self).__init__(driver)
        self.locator = \
            self.Locator.xpath_selector("//android.widget.EditText[@NAF='true']")


class ConfirmPublicKeyButton(BaseButton):

    def __init__(self, driver):
        super(ConfirmPublicKeyButton, self).__init__(driver)
        self.locator = \
            self.Locator.xpath_selector("//android.widget.TextView[@text='Add new contact']"
                                        "/following-sibling::android.view.ViewGroup/"
                                        "android.widget.ImageView")


class ChatMessageInput(BaseEditBox):

    def __init__(self, driver):
        super(ChatMessageInput, self).__init__(driver)
        self.locator = self.Locator.xpath_selector("//*[@content-desc='chat-message-input']")


class SendMessageButton(BaseButton):

    def __init__(self, driver):
        super(SendMessageButton, self).__init__(driver)
        self.locator = \
            self.Locator.xpath_selector("//android.widget.FrameLayout//"
                                        "android.view.ViewGroup[3]//"
                                        "android.view.ViewGroup[2]//android.widget.ImageView")


class UserNameText(BaseText):

    def __init__(self, driver):
        super(UserNameText, self).__init__(driver)
        self.locator = \
            self.Locator.xpath_selector("//android.widget.ScrollView//android.widget.TextView")

    class UserContactByName(BaseButton):

        def __init__(self, driver, user_name):
            super(UserNameText.UserContactByName, self).__init__(driver)
            self.locator = self.Locator.xpath_selector('//*[@text="' + user_name + '"]')


class ChatsViewObject(BaseViewObject):

    def __init__(self, driver):
        super(ChatsViewObject, self).__init__(driver)
        self.driver = driver

        self.profile_button = ProfileButton(self.driver)
        self.profile_icon = ProfileIcon(self.driver)
        self.plus_button = PlusButton(self.driver)
        self.add_new_contact = AddNewContactButton(self.driver)

        self.public_key_edit_box = PublicKeyEditBox(self.driver)
        self.confirm_public_key_button = ConfirmPublicKeyButton(self.driver)

        self.chat_message_input = ChatMessageInput(self.driver)
        self.send_message_button = SendMessageButton(self.driver)
        self.user_name_text = UserNameText(self.driver)

        self.new_group_chat_button = NewGroupChatButton(self.driver)
        self.augur_contact = NewGroupChatButton.AugurContact(self.driver)
        self.jarrad_contact = NewGroupChatButton.JarradContact(self.driver)
        self.next_button = NewGroupChatButton.NextButton(self.driver)
        self.name_edit_box = NewGroupChatButton.NameEditBox(self.driver)
        self.save_button = NewGroupChatButton.SaveButton(self.driver)
