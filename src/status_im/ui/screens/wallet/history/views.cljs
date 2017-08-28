(ns status-im.ui.screens.wallet.history.views
  (:require-macros [status-im.utils.views :refer [defview letsubs]])
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [status-im.components.button.view :as btn]
            [status-im.components.checkbox.view :as chk]
            [status-im.components.react :as rn]
            [status-im.components.list.views :as list]
            [status-im.components.tabs.styles :as tst]
            [status-im.components.tabs.views :as tabs]
            [status-im.components.toolbar-new.actions :as act]
            [status-im.components.toolbar-new.view :as toolbar]
            [status-im.ui.screens.wallet.history.styles :as history-styles]
            [status-im.utils.utils :as utils]
            [status-im.i18n :as i18n]))

(defn on-sign-transaction
  [m]
  ;; TODO(yenda) implement
  (utils/show-popup "TODO" "Sign Transaction"))

(defn on-delete-transaction
  [m]
  ;; TODO(yenda) implement
  (utils/show-popup "TODO" "Delete Transaction"))

(defn unsigned-action []
  [toolbar/text-action #(rf/dispatch [:navigate-to-modal :wallet-transactions-sign-all])
   (i18n/label :t/transactions-sign-all)])

(def history-action
  {:icon      :icons/filter
   :icon-opts {}
   :handler   #(utils/show-popup "TODO" "Not implemented") #_(rf/dispatch [:navigate-to-modal :wallet-transactions-sign-all])})

(defn toolbar-view [view-id]
  [toolbar/toolbar2 {}
   toolbar/default-nav-back
   [toolbar/content-title (i18n/label :t/transactions)]
   (if (= @view-id :wallet-transactions-unsigned)
     [unsigned-action]
     [toolbar/actions
      [history-action]])])

(defn action-buttons [m]
  [rn/view {:style history-styles/action-buttons}
   [btn/primary-button {:text (i18n/label :t/transactions-sign) :on-press #(on-sign-transaction m)}]
   [btn/secondary-button {:text (i18n/label :t/delete) :on-press #(on-delete-transaction m)}]])

(defn- unsigned? [state] (= "unsigned" state))

(defn- transaction-icon [k color] {:icon k :style (history-styles/transaction-icon-background color)})

(defn- transaction-type->icon [s]
  (case s
    "incoming" (transaction-icon :icons/arrow-left :red)
    "outgoing" (transaction-icon :icons/arrow-right :red)
    "postponed"  (transaction-icon :icons/arrow-right :red)
    "unsigned" (transaction-icon :icons/dots-horizontal :red)
    "pending" (transaction-icon :icons/dots-horizontal :red)
    (throw (str "Unknown transaction type: " s))))

(defn render-transaction [{:keys [to state] {:keys [value symbol]} :content :as m}]
  [list/item
   [list/item-icon (transaction-type->icon state)]
   [list/item-content
    (str value " " symbol) (str (i18n/label :t/to) " " to)
    (if (unsigned? state)
      [action-buttons m])]
   [list/item-icon {:icon :icons/forward}]])

;; TODO(yenda) hook with re-frame

(defn empty-text [s]
  [rn/text {:style history-styles/empty-text} s])

(defview history-list []
  (letsubs [pending-transactions   [:wallet/pending-transactions]
            postponed-transactions [:wallet/postponed-transactions]
            sent-transactions      [:wallet/sent-transactions]]
    [rn/scroll-view
     [list/section-list {:sections        [{:title "Postponed"
                                            :key :postponed
                                            :data postponed-transactions}
                                           {:title "Pending"
                                            :key :pending
                                            :data pending-transactions}
                                           ;; TODO(yenda) :sent transactions shouldbe grouped by day and have their :key / :title adapted
                                           {:title "01 Sep"
                                            :key :sent-sep
                                            :data sent-transactions}]
                         :render-fn       render-transaction
                         :empty-component (empty-text (i18n/label :t/transactions-history-empty))}]]))

(defview unsigned-list [transactions]
  []
  [rn/scroll-view
   [list/flat-list {:data            transactions
                    :render-fn       render-transaction
                    :empty-component (empty-text (i18n/label :t/transactions-unsigned-empty))}]])

(defn- unsigned-transactions-title [transactions]
  (let [count (count transactions)]
    (str (i18n/label :t/transactions-unsigned)
         (if (pos? count) (str " " count)))))

(defn- tab-list [unsigned-transactions]
  [{:view-id :wallet-transactions-unsigned
    :title   (unsigned-transactions-title unsigned-transactions)
    :screen  [unsigned-list unsigned-transactions]}
   {:view-id :wallet-transactions-history
    :title   (i18n/label :t/transactions-history)
    :screen  [history-list]}])

(defn- tab->index [tabs] (reduce #(assoc %1 (:view-id %2) (count %1)) {} tabs))

(defn get-tab-index [tabs view-id]
  (get (tab->index tabs) view-id 0))

;; Sign all

(defview sign-all []
  []
  [rn/keyboard-avoiding-view {:style history-styles/sign-all-view}
   [rn/view {:style history-styles/sign-all-done}
    [btn/primary-button {:style    history-styles/sign-all-done-button
                         :text     (i18n/label :t/done)
                         :on-press #(rf/dispatch [:navigate-back])}]]
   [rn/view {:style history-styles/sign-all-popup}
    [rn/text {:style history-styles/sign-all-popup-sign-phrase} "one two three"] ;; TODO hook
    [rn/text {:style history-styles/sign-all-popup-text} (i18n/label :t/transactions-sign-all-text)]
    [rn/view {:style history-styles/sign-all-actions}
     [rn/text-input {:style             history-styles/sign-all-input
                     :secure-text-entry true
                     :placeholder       (i18n/label :t/transactions-sign-input-placeholder)}]
     [btn/primary-button {:text (i18n/label :t/transactions-sign-all) :on-press #(on-sign-transaction %)}]]]])

;; Filter history

(defn- item-tokens [{:keys [symbol label checked?]}]
  [list/item
   [list/item-icon (transaction-type->icon "pending")] ;; TODO add proper token data
   [list/item-content label symbol]
   [chk/checkbox  {:checked? true #_checked?}]])

(defn- item-type [{:keys [id label checked?]}]
  [list/item
   [list/item-icon (transaction-type->icon id)]
   [list/item-content label]
   [chk/checkbox checked?]])

(def filter-data
  [{:title (i18n/label :t/transactions-filter-tokens)
    :key :tokens
    :renderItem (list/wrap-render-fn item-tokens)
    :data [{:symbol "GNO" :label "Gnosis"}
           {:symbol "SNT" :label "Status Network Token"}
           {:symbol "SGT" :label "Status Genesis Token"}
           {:symbol "GOL" :label "Golem"}]}
   {:title (i18n/label :t/transactions-filter-type)
    :key :type
    :renderItem (list/wrap-render-fn item-type)
    :data [{:id :incoming  :label "Incoming"}
           {:id :outgoing  :label "Outgoing"}
           {:id :pending   :label "Pending"}
           {:id :postponed :label "Postponed"}]}])

(defview filter-history []
  []
  [rn/view
   [toolbar/toolbar2 {}
    [toolbar/nav-clear-text (i18n/label :t/done)]
    [toolbar/content-title (i18n/label :t/transactions-filter-title)]
    [toolbar/text-action #(utils/show-popup "TODO" "Select All")
     (i18n/label :t/transactions-filter-select-all)]]
   [rn/scroll-view
    [list/section-list {:sections filter-data}]]])

;; TODO(jeluard) whole swipe logic
;; extract navigate-tab action (on tap)

(defn- main-section [view-id unsigned-transactions]
  (let [tabs (tab-list unsigned-transactions)]
    [rn/view {:style history-styles/main-section}
     [tabs/tabs {:selected-view-id @view-id
                 :tab-list         tabs}]
     [rn/swiper (merge tst/swiper
                       {:index (get-tab-index tabs @view-id)
                        :loop  false})
      ;:ref                    #(reset! swiper %)
      ;:on-momentum-scroll-end (on-scroll-end swiped? scroll-ended @view-id)

      (doall
        (map-indexed (fn [index {screen :screen}]
                       (with-meta screen {:key index} )) tabs))]]))

;; TODO(yenda) must reflect selected wallet

(defview transactions []
  [unsigned-transactions [:wallet/unsigned-transactions]]
  (let [view-id (r/atom :wallet-transactions-history)]
    [rn/view {:style history-styles/wallet-transactions-container}
     [toolbar-view view-id]
     [main-section view-id unsigned-transactions]]))
