(ns status-im.ui.screens.wallet.add-new.views
  (:require-macros [status-im.utils.views :refer [defview letsubs]])
  (:require [status-im.ui.components.react :as react]
            [status-im.ui.screens.hardwallet.pin.views :as pin.views]
            [status-im.i18n :as i18n]
            [re-frame.core :as re-frame]
            [status-im.ui.components.colors :as colors]
            [reagent.core :as reagent]
            [cljs.spec.alpha :as spec]
            [status-im.multiaccounts.db :as multiaccounts.db]
            [status-im.ui.components.toolbar :as toolbar]
            [status-im.ui.components.topbar :as topbar]
            [status-im.utils.utils :as utils.utils]
            [status-im.ui.components.text-input.view :as text-input]
            [status-im.ui.components.icons.vector-icons :as icons]
            [status-im.ui.screens.wallet.account-settings.views :as account-settings]
            [status-im.ethereum.core :as ethereum]
            [status-im.utils.security :as security]
            [clojure.string :as string]
            [status-im.utils.platform :as platform]))

(defn- request-camera-permissions []
  (let [options {:handler :wallet.add-new/qr-scanner-result}]
    (re-frame/dispatch
     [:request-permissions
      {:permissions [:camera]
       :on-allowed
       #(re-frame/dispatch [:wallet.add-new/qr-scanner-allowed options])
       :on-denied
       #(utils.utils/set-timeout
         (fn []
           (utils.utils/show-popup (i18n/label :t/error)
                                   (i18n/label :t/camera-access-error)))
         50)}])))

(defn add-account-topbar [type]
  (let [title (case type
                :generate :t/generate-an-account
                :watch :t/add-watch-account
                :seed :t/add-seed-account
                :key :t/add-private-key-account
                "")]
    [topbar/topbar
     (merge {:title title}
            (when (= type :watch)
              {:accessories [{:icon    :qr
                              :handler #(request-camera-permissions)}]}))]))

(defn common-settings [account]
  [react/view {:margin-horizontal 16 :margin-top 30}
   [text-input/text-input-with-label
    {:label               (i18n/label :t/account-name)
     :auto-focus          false
     :default-value       (:name account)
     :accessibility-label :add-account-enter-account-name
     :placeholder         (i18n/label :t/account-name)
     :on-change-text      #(re-frame/dispatch [:set-in [:add-account :account :name] %])}]
   [react/text {:style {:margin-top 30}} (i18n/label :t/account-color)]
   [react/touchable-highlight
    {:on-press #(re-frame/dispatch
                 [:show-popover
                  {:view  [account-settings/colors-popover (:color account)
                           (fn [new-color]
                             (re-frame/dispatch [:set-in [:add-account :account :color] new-color])
                             (re-frame/dispatch [:hide-popover]))]
                   :style {:max-height "60%"}}])}
    [react/view {:height      52 :margin-top 12 :background-color (:color account) :border-radius 8
                 :align-items :flex-end :justify-content :center :padding-right 12}
     [icons/icon :main-icons/dropdown {:color colors/white}]]]])

(defn settings [{:keys [type scanned-address password-error account-error]}
                entered-password]
  [react/view {:margin-horizontal 16}
   (if (= type :watch)
     [text-input/text-input-with-label
      {:label               (i18n/label :t/wallet-key-title)
       :auto-focus          false
       :default-value       scanned-address
       :placeholder         (i18n/label :t/enter-address)
       :accessibility-label :add-account-enter-watch-address
       :on-change-text      #(re-frame/dispatch [:set-in [:add-account :address] %])}]
     [text-input/text-input-with-label
      {:label               (i18n/label :t/password)
       :parent-container    {:margin-top 30}
       :auto-focus          false
       :placeholder         (i18n/label :t/enter-your-password)
       :secure-text-entry   true
       :text-content-type   :none
       :accessibility-label :add-account-enter-password
       :error               (when password-error (i18n/label :t/add-account-incorrect-password))
       :on-change-text      #(do
                               (re-frame/dispatch [:set-in [:add-account :password-error] nil])
                               (reset! entered-password %))}])
   (when (= type :seed)
     [text-input/text-input-with-label
      {:parent-container    {:margin-top 30}
       :label               (i18n/label :t/recovery-phrase)
       :auto-focus          false
       :placeholder         (i18n/label :t/multiaccounts-recover-enter-phrase-title)
       :auto-correct        false
       :keyboard-type       "visible-password"
       :multiline           true
       :style               (when platform/android?
                              {:flex 1})
       :height              95
       :error               account-error
       :accessibility-label :add-account-enter-seed
       :on-change-text
       #(do
          (re-frame/dispatch [:set-in [:add-account :account-error] nil])
          (re-frame/dispatch [:set-in [:add-account :seed] (security/mask-data (string/lower-case %))]))}])
   (when (= type :key)
     [text-input/text-input-with-label
      {:parent-container    {:margin-top 30}
       :label               (i18n/label :t/private-key)
       :auto-focus          false
       :placeholder         (i18n/label :t/enter-a-private-key)
       :auto-correct        false
       :keyboard-type       "visible-password"
       :error               account-error
       :secure-text-entry   true
       :accessibility-label :add-account-enter-private-key
       :text-content-type   :none
       :on-change-text
       #(do
          (re-frame/dispatch [:set-in [:add-account :account-error] nil])
          (re-frame/dispatch [:set-in [:add-account :private-key] (security/mask-data %)]))}])])

(defview add-account []
  (letsubs [{:keys [type account] :as add-account} [:add-account]
            add-account-disabled? [:add-account-disabled?]
            entered-password      (reagent/atom "")]
    [react/keyboard-avoiding-view {:style {:flex 1}}
     [add-account-topbar type]
     [react/scroll-view {:keyboard-should-persist-taps :handled
                         :style                        {:flex 1}}
      [settings add-account entered-password]
      [common-settings account]]
     [toolbar/toolbar
      {:show-border? true
       :right
       {:type                :next
        :label               :t/add-account
        :accessibility-label :add-account-add-account-button
        :on-press            #(re-frame/dispatch [:wallet.accounts/add-new-account
                                                  (ethereum/sha3 @entered-password)])
        :disabled?           (or add-account-disabled?
                                 (and
                                  (not (= type :watch))
                                  (not (spec/valid? ::multiaccounts.db/password @entered-password))))}}]]))

(defview pin []
  (letsubs [pin         [:hardwallet/pin]
            status      [:hardwallet/pin-status]
            error-label [:hardwallet/pin-error-label]]
    [react/keyboard-avoiding-view {:style {:flex 1}}
     [topbar/topbar]
     [pin.views/pin-view
      {:pin               pin
       :status            status
       :title-label       :t/current-pin
       :description-label :t/current-pin-description
       :error-label       error-label
       :step              :export-key}]]))