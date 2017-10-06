(ns status-im.translations.en)

(def translations
  {
   ;;common
   :members-title                         "Members"
   :not-implemented                       "!not implemented"
   :chat-name                             "Chat name"
   :notifications-title                   "Notifications and sounds"
   :offline                               "Offline"
   :search-for                            "Search for..."
   :cancel                                "Cancel"
   :next                                  "Next"
   :type-a-message                        "Type a message..."
   :type-a-command                        "Start typing a command..."
   :error                                 "Error"
   :unknown-status-go-error               "Unknown status-go error"
   :node-unavailable                      "No ethereum node running"
   :yes                                   "Yes"
   :no                                    "No"

   :camera-access-error                   "To grant the required camera permission, please, go to your system settings and make sure that Status > Camera is selected."
   :photos-access-error                   "To grant the required photos permission, please, go to your system settings and make sure that Status > Photos is selected."

   ;;drawer
   :invite-friends                        "Invite friends"
   :faq                                   "FAQ"
   :switch-users                          "Switch users"
   :feedback                              "Got feedback?\nShake your phone!"
   :view-all                              "View all"
   :current-network                       "Current network"

   ;;chat
   :is-typing                             "is typing"
   :and-you                               "and you"
   :search-chat                           "Search chat"
   :members                               {:one   "1 member"
                                           :other "{{count}} members"
                                           :zero  "no members"}
   :members-active                        {:one   "1 member"
                                           :other "{{count}} members"
                                           :zero  "no members"}
   :public-group-status                   "Public"
   :active-online                         "Online"
   :active-unknown                        "Unknown"
   :available                             "Available"
   :no-messages                           "No messages"
   :suggestions-requests                  "Requests"
   :suggestions-commands                  "Commands"
   :faucet-success                        "Faucet request has been received"
   :faucet-error                          "Faucet request error"

   ;;sync
   :sync-in-progress                      "Syncing..."
   :sync-synced                           "In sync"

   ;;messages
   :status-sending                        "Sending"
   :status-pending                        "Pending"
   :status-sent                           "Sent"
   :status-seen-by-everyone               "Seen by everyone"
   :status-seen                           "Seen"
   :status-delivered                      "Delivered"
   :status-failed                         "Failed"

   ;;datetime
   :datetime-ago-format                   "{{number}} {{time-intervals}} {{ago}}"
   :datetime-second                       {:one   "second"
                                           :other "seconds"}
   :datetime-minute                       {:one   "minute"
                                           :other "minutes"}
   :datetime-hour                         {:one   "hour"
                                           :other "hours"}
   :datetime-day                          {:one   "day"
                                           :other "days"}
   :datetime-multiple                     "s"
   :datetime-ago                          "ago"
   :datetime-yesterday                    "yesterday"
   :datetime-today                        "today"

   ;;profile
   :profile                               "Profile"
   :edit-profile                          "Edit profile"
   :report-user                           "REPORT USER"
   :message                               "Message"
   :username                              "Username"
   :not-specified                         "Not specified"
   :public-key                            "Public key"
   :phone-number                          "Phone number"
   :email                                 "Email"
   :update-status                         "Update your status..."
   :add-a-status                          "Add a status..."
   :status-prompt                         "Create a status to help people know about the things you are offering. You can use #hashtags too."
   :add-to-contacts                       "Add to contacts"
   :in-contacts                           "In contacts"
   :remove-from-contacts                  "Remove from contacts"
   :start-conversation                    "Start conversation"
   :send-transaction                      "Send transaction"
   :share-qr                              "Share QR"
   :error-incorrect-name                  "Please select another name"
   :error-incorrect-email                 "Incorrect e-mail"
   :profile-testnet-icon                  "Testnet"
   :profile-testnet-text                  "Ropsten Testnet Only. Please do not send real ETH or SNT to your address"

   ;;make_photo
   :image-source-title                    "Profile image"
   :image-source-make-photo               "Capture"
   :image-source-gallery                  "Select from gallery"
   :image-source-cancel                   "Cancel"

   ;;sharing
   :sharing-copy-to-clipboard             "Copy to clipboard"
   :sharing-share                         "Share..."
   :sharing-cancel                        "Cancel"

   :browsing-title                        "Browse"
   :browsing-browse                       "@browse"
   :browsing-open-in-web-browser          "Open in web browser"
   :browsing-cancel                       "Cancel"

   ;;sign-up
   :contacts-syncronized                  "Your contacts have been synchronized"
   :confirmation-code                     (str "Thanks! We've sent you a text message with a confirmation "
                                               "code. Please provide that code to confirm your phone number")
   :incorrect-code                        (str "Sorry the code was incorrect, please enter again")
   :generate-passphrase                   (str "I'll generate a passphrase for you so you can restore your "
                                               "access or log in from another device")
   :phew-here-is-your-passphrase          "*Phew* that was hard, here is your passphrase, *write this down and keep this safe!* You will need it to recover your account."
   :here-is-your-passphrase               "Here is your passphrase, *write this down and keep this safe!* You will need it to recover your account."
   :here-is-your-signing-phrase           "Here is your signing phrase. You will use it to verify your transactions. *Write it down and keep it safe!*"
   :written-down                          "Make sure you had securely written it down"
   :phone-number-required                 "Tap here to enter your phone number & I'll find your friends"
   :shake-your-phone                      "Find a bug or have a suggestion? Just ~shake~ your phone!"
   :intro-status                          "Chat with me to setup your account and change your settings!"
   :intro-message1                        "Welcome to Status\nTap this message to set your password & get started!"
   :account-generation-message            "Gimmie a sec, I gotta do some crazy math to generate your account!"
   :move-to-internal-failure-message      "We need to move some important files from external to internal storage. To do this, we need your permission. We won't be using external storage in future versions."
   :debug-enabled                         "Debug server has been launched! You can now execute *status-dev-cli scan* to find the server from your computer on the same network."

   ;;phone types
   :phone-e164                            "International 1"
   :phone-international                   "International 2"
   :phone-national                        "National"
   :phone-significant                     "Significant"

   ;;chats
   :chats                                 "Chats"
   :new-chat                              "New chat"
   :delete-chat                           "Delete chat"
   :new-group-chat                        "New group chat"
   :new-public-group-chat                 "Join public chat"
   :edit-chats                            "Edit chats"
   :search-chats                          "Search chats"
   :empty-topic                           "Empty topic"
   :topic-format                          "Wrong format [a-z0-9\\-]+"
   :public-group-topic                    "Topic"

   ;;discover
   :discover                              "Discover"
   :none                                  "None"
   :search-tags                           "Type your search tags here"
   :popular-tags                          "Popular hashtags"
   :recent                                "Recent statuses"
   :no-statuses-discovered                "No statuses discovered"
   :no-statuses-found                     "No statuses found"
   :chat                                  "Chat"
   :all                                   "All"
   :public-chats                          "Public chats"
   :soon                                  "Soon"
   :public-chat-user-count                "{{count}} people"

   ;;settings
   :settings                              "Settings"

   ;;contacts
   :contacts                              "Contacts"
   :new-contact                           "New contact"
   :delete-contact                        "Delete contact"
   :delete-contact-confirmation           "This contact will be removed from your contacts"
   :remove-from-group                     "Remove from group"
   :edit-contacts                         "Edit contacts"
   :search-contacts                       "Search contacts"
   :show-all                              "SHOW ALL"
   :contacts-group-dapps                  "ÐApps"
   :contacts-group-people                 "People"
   :contacts-group-new-chat               "Start new chat"
   :choose-from-contacts                  "Choose from contacts"
   :no-contacts                           "No contacts yet"
   :show-qr                               "Show QR"
   :enter-address                         "Enter address"
   :more                                  "more"

   ;;group-settings
   :remove                                "Remove"
   :save                                  "Save"
   :delete                                "Delete"
   :change-color                          "Change color"
   :clear-history                         "Clear history"
   :mute-notifications                    "Mute notifications"
   :leave-chat                            "Leave chat"
   :delete-and-leave                      "Delete and leave"
   :chat-settings                         "Chat settings"
   :edit                                  "Edit"
   :add-members                           "Add members"
   :blue                                  "Blue"
   :purple                                "Purple"
   :green                                 "Green"
   :red                                   "Red"

   ;;commands
   :money-command-description             "Send money"
   :location-command-description          "Send location"
   :phone-command-description             "Send phone number"
   :phone-request-text                    "Phone number request"
   :confirmation-code-command-description "Send confirmation code"
   :confirmation-code-request-text        "Confirmation code request"
   :send-command-description              "Send command"
   :request-command-description           "Send request"
   :keypair-password-command-description  ""
   :help-command-description              "Help"
   :request                               "Request"
   :chat-send-eth                         "{{amount}} ETH"
   :chat-send-eth-to                      "{{amount}} ETH to {{chat-name}}"
   :chat-send-eth-from                    "{{amount}} ETH from {{chat-name}}"

   ;;location command
   :your-current-location                 "Your current location"
   :places-nearby                         "Places nearby"
   :search-results                        "Search results"
   :dropped-pin                           "Dropped pin"
   :location                              "Location"
   :open-map                              "Open Map"
   :sharing-copy-to-clipboard-address     "Copy the Address"
   :sharing-copy-to-clipboard-coordinates "Copy coordinates"

   ;;new-group
   :group-chat-name                       "Chat name"
   :empty-group-chat-name                 "Please enter a name"
   :illegal-group-chat-name               "Please select another name"
   :new-group                             "New group"
   :reorder-groups                        "Reorder groups"
   :group-name                            "Group name"
   :edit-group                            "Edit group"
   :delete-group                          "Delete group"
   :delete-group-confirmation             "This group will be removed from your groups. This will not affect contacts"
   :delete-group-prompt                   "This will not affect contacts"
   :group-members                         "Group members"
   :contact-s                             {:one   "contact"
                                           :other "contacts"}

   ;;participants
   :add-participants                      "Add Participants"
   :remove-participants                   "Remove Participants"

   ;;protocol
   :received-invitation                   "received chat invitation"
   :removed-from-chat                     "removed you from group chat"
   :left                                  "left"
   :invited                               "invited"
   :removed                               "removed"
   :You                                   "You"

   ;;new-contact
   :add-new-contact                       "Add new contact"
   :import-qr                             "Import"
   :scan-qr                               "Scan QR"
   :swow-qr                               "Show QR"
   :name                                  "Name"
   :whisper-identity                      "Whisper Identity"
   :address-explication                   "Maybe here should be some text explaining what an address is and where to look for it"
   :enter-valid-address                   "Please enter a valid address or scan a QR code"
   :enter-valid-public-key                "Please enter a valid public key or scan a QR code"
   :contact-already-added                 "The contact has already been added"
   :can-not-add-yourself                  "You can't add yourself"
   :unknown-address                       "Unknown address"

   ;;login
   :connect                               "Connect"
   :address                               "Address"
   :password                              "Password"
   :login                                 "Login"
   :sign-in-to-status                     "Sign in to Status"
   :sign-in                               "Sign in"
   :wrong-password                        "Wrong password"
   :enter-password                        "Enter password"

   ;;recover
   :recover-from-passphrase               "Recover from passphrase"
   :recover-explain                       "Please enter the passphrase for your password to recover access"
   :passphrase                            "Passphrase"
   :recover                               "Recover"
   :enter-valid-passphrase                "Please enter a passphrase"
   :enter-valid-password                  "Please enter a password"
   :twelve-words-in-correct-order         "12 words in correct order"

   ;;accounts
   :recover-access                        "Recover access"
   :add-account                           "Add account"
   :create-new-account                    "Create new account"

   ;;wallet-qr-code
   :done                                  "Done"

   ;;validation
   :invalid-phone                         "Invalid phone number"
   :amount                                "Amount"
   :not-enough-eth                        (str "Not enough ETH on balance "
                                               "({{balance}} ETH)")

   ;;transactions
   :confirm                               "Confirm"
   :confirm-transactions                  {:one   "Confirm transaction"
                                           :other "Confirm {{count}} transactions"
                                           :zero  "No transactions"}
   :transactions-confirmed                {:one   "Transaction confirmed"
                                           :other "{{count}} transactions confirmed"
                                           :zero  "No transactions confirmed"}
   :transaction                           "Transaction"
   :unsigned-transactions                 "Unsigned transactions"
   :no-unsigned-transactions              "No unsigned transactions"
   :enter-password-transactions           {:one   "Confirm transaction by entering your password"
                                           :other "Confirm transactions by entering your password"}
   :status                                "Status"
   :pending-confirmation                  "Pending confirmation"
   :recipient                             "Recipient"
   :one-more-item                         "One more item"
   :fee                                   "Fee"
   :estimated-fee                         "Est. fee"
   :value                                 "Value"
   :to                                    "To"
   :from                                  "From"
   :data                                  "Data"
   :got-it                                "Got it"
   :contract-creation                     "Contract Creation"
   :block                                 "Block"
   :hash                                  "Hash"
   :gas-limit                             "Gas limit"
   :gas-price                             "Gas price"
   :gas-used                              "Gas Used"
   :cost-fee                              "Cost/Fee"
   :nonce                                 "Nonce"
   :confirmations                         "confirmations"
   :confirmations-helper-text             "If you want to be sure your transaction will not be compromise wait until it gets at least 10 blocks confirmations"
   :copy-transaction-hash                 "Copy transaction hash"
   :open-on-etherscan                     "Open on Etherscan"

   ;;webview
   :web-view-error                        "oops, error"

   ;;testfairy warning
   :testfairy-title                       "Warning!"
   :testfairy-message                     "You are using app installed from a nightly build. For testing purposes this build includes session recording if wifi connection is used, so all your interaction with app is saved (as video and log) and might be used by development team to investigate possible issues. Saved video/log do not include your passwords. Recording is done only if app is installed from a nightly build. Nothing is recorded if app is installed from PlayStore or TestFlight."

   ;; wallet
   :wallet                                "Wallet"
   :wallets                               "Wallets"
   :your-wallets                          "Your wallets"
   :main-wallet                           "Main Wallet"
   :wallet-error                          "Error Loading Data"
   :wallet-send                           "Send"
   :wallet-request                        "Request"
   :wallet-exchange                       "Exchange"
   :wallet-assets                         "Assets"
   :wallet-add-asset                      "Add asset"
   :wallet-total-value                    "Total value"
   :wallet-settings                       "Wallet Settings"
   :signing-phrase-description            "Sign the transaction by entering your password. Make sure that the words above match your secret signing phrase"
   :wallet-insufficient-funds             "Insufficient funds"
   :request-transaction                   "Request Transaction"
   :send-request                          "Send request"
   :share                                 "Share"
   :currency                              "Currency"
   :transactions                          "Transactions"
   :transaction-details                   "Transaction details"
   :transactions-sign                     "Sign"
   :transactions-sign-all                 "Sign all"
   :transactions-sign-transaction         "Sign Transaction"
   :transactions-sign-later               "Sign Later"
   :transactions-sign-all-text            "Sign the transaction by entering your password.\nMake sure that the words above match your secret signing phrase"
   :transactions-sign-input-placeholder   "Enter your password"
   :transactions-history                  "History"
   :transactions-unsigned                 "Unsigned"
   :transactions-history-empty            "You don't have a history transactions"
   :transactions-unsigned-empty           "You don't have unsigned transactions"
   :transactions-filter-title             "Filter History"
   :transactions-filter-tokens            "Tokens"
   :transactions-filter-type              "Type"
   :transactions-filter-select-all        "Select all"
   :view-transaction-details              "View Transaction Details"
   :transaction-description               "If you want to be sure you transaction will not be compromised wait until it gets at least 10 block confirmations"
   :transaction-sent                      "Transaction Sent"
   :transaction-moved-text                "The transaction has been successfully moved to “Unsigned”"
   :transaction-moved-title               "Transaction Moved"
   :sign-later-title                      "Sign Transaction Later?"
   :sign-later-text                       "You will be able to sign in the transaction history"
   :not-applicable                        "Not applicable for unsigned transactions"

   ;; Wallet Send
   :wallet-send-transaction               "Send Transaction"
   :wallet-send-step                      "Step {{step}} of {{number}}"
   :wallet-choose-recipient               "Choose Recipient"
   :wallet-choose-from-contacts           "Choose From Contacts"
   :wallet-address-from-clipboard         "Use Address From Clipboard"
   :wallet-invalid-address                "Address is invalid"
   :wallet-browse-photos                  "Browse Photos"
   :validation-amount-invalid             "Amount is not valid"
   :validation-amount-invalid-number      "Amount is not a valid number"
   :validation-amount-is-too-precise      "Amount is too precise. The smallest unit you can send is 1 Wei (1x10^-18 ETH)"



   ;network settings
   :new-network                           "New network"
   :add-network                           "Add network"
   :add-new-network                       "Add new network"
   :existing-networks                     "Existing networks"
   :add-json-file                         "Add a JSON file"
   :paste-json-as-text                    "Paste JSON as text"
   :paste-json                            "Paste JSON"
   :specify-rpc-url                       "Specify a RPC URL"
   :edit-rpc-url                          "Edit a RPC URL"
   :edit-network-config                   "Edit network config"
   :connected                             "Connected"
   :process-json                          "Process JSON"
   :error-processing-json                 "Error processing JSON"
   :rpc-url                               "RPC URL"
   :remove-network                        "Remove network"
   :network-settings                      "Network settings"
   :edit-network-warning                  "Be careful, editing the network data may disable this network for you"
   :connecting-requires-login             "Connecting to another network requires login"
   :close-app-title                       "Warning!"
   :close-app-content                     "App will be closed. When you restart it selected network will be used."
   :close-app-button                      "Confirm"})
