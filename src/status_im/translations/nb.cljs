(ns status-im.translations.nb)

(def translations
  {
   ;common
   :members-title                         "Medlemmer"
   :not-implemented                       "!ikke implementert"
   :chat-name                             "Kallenavn"
   :notifications-title                   "Notifikasjoner og lyd"
   :offline                               "Avlogget"
   :search-for                            "Søk etter..."
   :cancel                                "Avbryt"
   :next                                  "Neste"
   :type-a-message                        "Skriv en melding..."
   :type-a-command                        "Start å skrive en kommando..."
   :error                                 "Feil"
   :unknown-status-go-error               "Ukjent status-go feil"
   :node-unavailable                      "Ingen Ethereum node kjørende"

   :camera-access-error                   "For å tillate bruk av kamera, gå til systeminstillinger og sørg for at Status > Kamera er aktivert."
   :photos-access-error                   "For å tillate bruk av bilder, gå til systeminstillinger og sørg for at Status > Bilder er aktivert."

   ;drawer
   :invite-friends                        "Inviter venner"
   :faq                                   "FAQ"
   :switch-users                          "Bytt brukere"
   :feedback                              "Tilbakemelding?\nRist telefonen!"
   :view-all                              "Vis alle"
   :current-network                       "Nåværende nettverk"

   ;chat
   :is-typing                             "skriver"
   :and-you                               "og du"
   :search-chat                           "Søk i meldinger"
   :members                               {:one   "1 medlem"
                                           :other "{{count}} medlemmer"
                                           :zero  "ingen medlemmer"}
   :members-active                        {:one   "1 medlem"
                                           :other "{{count}} medlemmer"
                                           :zero  "ingen medlemmer"}
   :public-group-status                   "Offentlig"
   :active-online                         "Pålogget"
   :active-unknown                        "Ukjent"
   :available                             "Tilgjengelig"
   :no-messages                           "Ingen meldinger"
   :suggestions-requests                  "Forespørsler"
   :suggestions-commands                  "Kommandoer"
   :faucet-success                        "Du har mottatt en faucet forespørsel"
   :faucet-error                          "Faucet forespørselen feilet"

   ;sync
   :sync-in-progress                      "Synkroniserer..."
   :sync-synced                           "Synkronisert"

   ;messages
   :status-sending                        "Sender"
   :status-pending                        "Venter"
   :status-sent                           "Sendt"
   :status-seen-by-everyone               "Sett av alle"
   :status-seen                           "Sett"
   :status-delivered                      "Levert"
   :status-failed                         "Feilet"

   ;datetime
   :datetime-ago-format                   "{{number}} {{time-intervals}} {{ago}}"
   :datetime-second                       {:one   "sekund"
                                           :other "sekunder"}
   :datetime-minute                       {:one   "minutt"
                                           :other "minutter"}
   :datetime-hour                         {:one   "time"
                                           :other "timer"}
   :datetime-day                          {:one   "dag"
                                           :other "dager"}
   :datetime-multiple                     "er"
   :datetime-ago                          "siden"
   :datetime-yesterday                    "i går"
   :datetime-today                        "i dag"

   ;profile
   :profile                               "Profil"
   :edit-profile                          "Rediger profil"
   :report-user                           "RAPPORTER BRUKER"
   :message                               "Melding"
   :username                              "Brukernavn"
   :not-specified                         "Ikke spesifisert"
   :public-key                            "Offentlig nøkkel"
   :phone-number                          "Telefonnummer"
   :email                                 "E-post"
   :update-status                         "Oppdater din status..."
   :add-a-status                          "Legg til en status..."
   :status-prompt                         "Legg til en status slik at andre forstår hva du tilbyr. Bruk gjerne #hashtag."
   :add-to-contacts                       "Legg til i kontakter"
   :in-contacts                           "I kontaker"
   :remove-from-contacts                  "Fjern fra kontakter"
   :start-conversation                    "Start en samtale"
   :send-transaction                      "Send overføring"
   :share-qr                              "Del QR-kode"
   :error-incorrect-name                  "Vennligst velg et annet navn"
   :error-incorrect-email                 "Feil e-post adresse"

   ;;make_photo
   :image-source-title                    "Profilbilde"
   :image-source-make-photo               "Opptak"
   :image-source-gallery                  "Velg fra bilder"
   :image-source-cancel                   "Avbryt"

   ;;sharing
   :sharing-copy-to-clipboard             "Kopier til utklippstavle"
   :sharing-share                         "Del..."
   :sharing-cancel                        "Avbryt"

   :browsing-title                        "Utforsk"
   :browsing-browse                       "@utforsk"
   :browsing-open-in-web-browser          "Åpne i nettleser"
   :browsing-cancel                       "Avbryt"

   ;sign-up
   :contacts-syncronized                  "Dine kontaker har blitt synkronisert"
   :confirmation-code                     (str "Topp! Vi har sendt deg en tekstmelding med bekreftelseskode."
                                               "Vennligst send oss tilbake koden for å verifisere at dette er din telefon")
   :incorrect-code                        (str "Beklager, prøv på ny")
   :generate-passphrase                   (str "Jeg genererer en passordfrase til deg, så du kan gjenopprette tilgang "
                                               "eller logge på fra andre enheter")

   :phew-here-is-your-passphrase          "*Phew* det var vrient, her er din nye passordfrase, *skriv den ned og hold den for deg selv!* Du vil trenge den for å kunne gjenopprette kontoen."
   :here-is-your-passphrase               "Her er din nye passordfrase, *skriv den ned og hold den for deg selv!* Du vil trenge den for å kunne gjenopprette kontoen."
   :here-is-your-signing-phrase           "Her er din signeringsfrase. Du vil bruke den til å verifisere dine transaksjoner. *skriv den ned og hold den for deg selv!*"
   :written-down                          "Vær sikker på at du har skrevet den ned"
   :phone-number-required                 "Trykk her og skriv ditt telefonnummer, så skal jeg finne vennene dine"
   :shake-your-phone                      "Har du funnet en feil eller har du tilbakemelding til oss? Bare ~rist~ på telefonen!"
   :intro-status                          "Chat med meg for å sette opp kontoen din og endre instillinger!"
   :intro-message1                        "Velkommen til Status\nTrykk på denne beskjeden for å sette et passord og komme i gang!"
   :account-generation-message            "Gi meg et øyeblikk! Jeg må granske universet for å generere en konto til deg!"
   :move-to-internal-failure-message      "Vi må flytte noen viktige filer fra ekstern til intern lagring. For å kunne gjøre dette, trenger vi din godkjenning. Vi kommer ikke til å bruke ekstern lagring i fremtidige versjoner."
   :debug-enabled                         "Debug server har blitt satt i gang! Du kan nå kjøre *status-dev-cli scan* for å finne serveren på PC'n din fra samme nettverk."

   ;phone types
   :phone-e164                            "Internasjonal 1"
   :phone-international                   "Internasjonal 2"
   :phone-national                        "Nasjonal"
   :phone-significant                     "Betydningsfull"

   ;chats
   :chats                                 "Chats"
   :new-chat                              "Ny melding"
   :delete-chat                           "Fjern melding"
   :new-group-chat                        "Ny gruppemelding"
   :new-public-group-chat                 "Bli med i offentlig chat"
   :edit-chats                            "Rediger melding"
   :search-chats                          "Søk meldinger"
   :empty-topic                           "Tomt emne"
   :topic-format                          "Feil format [a-z0-9\\-]+"
   :public-group-topic                    "Emne"

   ;discover
   :discover                              "Oppdag"
   :none                                  "Ingen"
   :search-tags                           "Søk etter etikett her"
   :popular-tags                          "Populære etiketter"
   :recent                                "Nylig"
   :no-statuses-discovered                "Ingen status oppdaget"
   :no-statuses-found                     "Ingen status funnet"

   ;settings
   :settings                              "Instillinger"

   ;contacts
   :contacts                              "Kontaker"
   :new-contact                           "Ny kontakt"
   :delete-contact                        "Slett kontakt"
   :delete-contact-confirmation           "Kontakten vil bli fjernet fra kontaktlisten din"
   :remove-from-group                     "Fjern fra gruppen"
   :edit-contacts                         "Rediger kontakter"
   :search-contacts                       "Søk kontakter"
   :show-all                              "VIS ALLE"
   :contacts-group-dapps                  "ÐApps"
   :contacts-group-people                 "Folk"
   :contacts-group-new-chat               "Start ny chat"
   :choose-from-contacts                  "Velg fra kontaker"
   :no-contacts                           "Ingen kontaker enda"
   :show-qr                               "Vis QR-kode"
   :enter-address                         "Adresse"
   :more                                  "mer"

   ;group-settings
   :remove                                "Fjern"
   :save                                  "Lagre"
   :delete                                "Slett"
   :change-color                          "Endre farge"
   :clear-history                         "Fjern historikk"

   :mute-notifications                    "Demp notifikasjoner"

   :leave-chat                            "Forlat chat"
   :delete-and-leave                      "Slett og forlat"
   :chat-settings                         "Chat instillinger"
   :edit                                  "Rediger"
   :add-members                           "Legg til medlemmer"
   :blue                                  "Blå"
   :purple                                "Lilla"
   :green                                 "Grønn"
   :red                                   "Rød"

   ;commands
   :money-command-description             "Send penger"
   :location-command-description          "Send posisjon"
   :phone-command-description             "Send telefonnummer"
   :phone-request-text                    "Etterspør telefonnummer"
   :confirmation-code-command-description "Send bekreftelseskode"
   :confirmation-code-request-text        "Etterspør befkreftelseskode"
   :send-command-description              "Send kommando"
   :request-command-description           "Send forespørsel"
   :keypair-password-command-description  ""
   :help-command-description              "Hjelp"
   :request                               "Forespørsel"
   :chat-send-eth                         "{{amount}} ETH"
   :chat-send-eth-to                      "{{amount}} ETH til {{chat-name}}"
   :chat-send-eth-from                    "{{amount}} ETH fra {{chat-name}}"

   ;location command
   :your-current-location                 "Din nåværende posisjon"
   :places-nearby                         "Steder i nærheten"
   :search-results                        "Søkeresultat"
   :dropped-pin                           "Slipp nål"
   :location                              "Posisjon"
   :open-map                              "Åpne kart"
   :sharing-copy-to-clipboard-address     "Kopier adressen"
   :sharing-copy-to-clipboard-coordinates "Kopier koordinater"

   ;new-group
   :group-chat-name                       "Chat navn"
   :empty-group-chat-name                 "Skriv et navn"
   :illegal-group-chat-name               "Vennligst velg et annet navn"
   :new-group                             "Ny gruppe"
   :reorder-groups                        "Organiser grupper"
   :group-name                            "Gruppenavn"
   :edit-group                            "Rediger gruppe"
   :delete-group                          "Slett gruppe"
   :delete-group-confirmation             "Denne slettes fra gruppen, men vil fortsatt være tilgjengelig fra kontakter"
   :delete-group-prompt                   "Dette vil ikke affektere kontakter"
   :group-members                         "Gruppemedlemmer"
   :contact-s                             {:one   "kontakt"
                                           :other "kontaker"}

   ;participants
   :add-participants                      "Legg til deltager"
   :remove-participants                   "Fjern deltager"

   ;protocol
   :received-invitation                   "Motatt chat-invitasjon"
   :removed-from-chat                     "fjernet deg fra gruppechatten"
   :left                                  "venstre"
   :invited                               "invitert"
   :removed                               "fjernet"
   :You                                   "Du"

   ;new-contact
   :add-new-contact                       "Legg til ny kontakt"
   :import-qr                             "Importer"
   :scan-qr                               "Skan QR-kode"
   :swow-qr                               "Vis QR-kode"
   :name                                  "Navn"
   :whisper-identity                      "Hvisk identiteten"
   :address-explication                   "Her bør det kanskje være tekst som forklarer hva en adresse er og hvor du finner den"
   :enter-valid-address                   "Skriv en adresse eller skan en QR-kode"
   :enter-valid-public-key                "Skriv en offentlig nøkkel eller skan en QR-kode"
   :contact-already-added                 "Kontakten er allerede i kontaktlisten"
   :can-not-add-yourself                  "Du kan ikke legge til deg selv"
   :unknown-address                       "Ukjent adresse"

   ;login
   :connect                               "Koble til"
   :address                               "Adresse"
   :password                              "Passord"
   :login                                 "Logg på"
   :sign-in-to-status                     "Logg på Status"
   :sign-in                               "Logg på"
   :wrong-password                        "Feil passord"

   ;recover
   :recover-from-passphrase               "Gjenopprett fra passordfrase"
   :recover-explain                       "Vennligst skriv din passordfrase for å gjenopprette tilgang"
   :passphrase                            "Passordfrase"
   :recover                               "Gjenopprett"
   :enter-valid-passphrase                "Vennligst skriv passordfrase"
   :enter-valid-password                  "Vennligst skriv passord"
   :twelve-words-in-correct-order         "12 ord i riktig rekkefølge"

   ;accounts
   :recover-access                        "Få tilbake tilgang"
   :add-account                           "Legg til konto"
   :create-new-account                    "Opprett konto"

   ;wallet-qr-code
   :done                                  "Ferdig"

   ;validation
   :invalid-phone                         "Feil telefonnummer"
   :amount                                "Beløp"
   :not-enough-eth                        (str "Du har ikke nok ETH på kontoen ({{balance}} ETH)")

   ;transactions
   :confirm                               "Bekreft"
   :confirm-transactions                  {:one   "Bekreft transaksjon"
                                           :other "Bekreft {{count}} transaksjon"
                                           :zero  "Ingen transaksjoner"}
   :transactions-confirmed                {:one   "Transaksjon bekreftet"
                                           :other "{{count}} transaksjoner bekreftet"
                                           :zero  "Ingen transaksjoner bekreftet"}
   :transaction                           "Transaksjon"
   :unsigned-transactions                 "Usigner transaksjon"
   :no-unsigned-transactions              "Ingen usignerte transaksjoner"
   :enter-password-transactions           {:one   "Bekreft transaksjon ved å skrive passord"
                                           :other "Bekreft transaksjon ved å skrive passord"}
   :status                                "Status"
   :pending-confirmation                  "Venter på bekreftelse"
   :recipient                             "Mottager"
   :one-more-item                         "En artikkel til"
   :fee                                   "Avgift"
   :estimated-fee                         "Estimert avgift"
   :value                                 "Verdi"
   :to                                    "Til"
   :from                                  "Fra"
   :data                                  "Data"
   :got-it                                "Mottatt"
   :contract-creation                     "Kontrakt opprettelse"

   ;:webview
   :web-view-error                        "oops, feil"

   ;;testfairy warning
   :testfairy-title                       "Advarsel!"
   :testfairy-message                     "Du bruker en nattlig versjon av applikasjonen. I den nattlige versjonen tar vi opp video og kjører en del logging som kan bli tatt i bruk av våre utviklere for å etterforske potensielle problemer. Dette gjelder kun den nattlige versjonen, versjoner som er installert fra PlayStore eller TestFlight blir ikke logget."

   ;; wallet
   :wallet                                "Lommebok"
   :wallets                               "Lommebøker"
   :your-wallets                          "Dine lommebøker"
   :main-wallet                           "Hovedlommebok"
   :wallet-error                          "Feil ved lasting av data"
   :wallet-send                           "Send"
   :wallet-request                        "Forespørsel"
   :wallet-exchange                       "Exchange"
   :wallet-assets                         "Assets"
   :wallet-add-asset                      "Add asset"
   :wallet-total-value                    "Total verdi"
   :wallet-settings                       "Lommeboksinstillinger"
   :transactions                          "Transaksjoner"
   :transactions-sign                     "Signer"
   :transactions-sign-all                 "Signer alle"
   :transactions-sign-all-text            "Signer transaksjonen ved å bruke ditt passord.\nSjekk at ordene ovenfor passer med din hemmlige signeringsfrase"
   :transactions-sign-input-placeholder   "Skriv passordfrase"
   :transactions-history                  "Historikk"
   :transactions-unsigned                 "Usignert"
   :transactions-history-empty            "Du har ingen tidligere transaksjoner"
   :transactions-unsigned-empty           "Du har ingen usignerte transaksjoner"
   :transactions-filter-title             "Filtrer historikk"
   :transactions-filter-tokens            "Tokens"
   :transactions-filter-type              "Type"
   :transactions-filter-select-all        "Velg alle"})
