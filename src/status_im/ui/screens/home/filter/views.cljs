(ns status-im.ui.screens.home.filter.views
  (:require [status-im.ui.components.list.views :as list]
            [status-im.ui.screens.home.styles :as styles]
            [status-im.ui.components.react :as react]
            [status-im.i18n :as i18n]
            [status-im.ui.components.colors :as colors]
            [status-im.ui.screens.home.views.inner-item :as inner-item]
            [status-im.utils.utils :as utils]
            [status-im.ui.components.animation :as animation]
            [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [status-im.ui.components.icons.vector-icons :as icons]))

(def animation-duration 150)

(defn search-input [_ {:keys [on-cancel on-focus on-change]}]
  (let [input-is-focused? (reagent/atom false)
        input-ref (reagent/atom nil)]
    (fn [search-filter]
      (let [show-cancel? (or @input-is-focused?
                             search-filter)]
        [react/view {:style styles/search-container}
         [react/view {:style styles/search-input-container}
          [icons/icon :main-icons/search {:color           colors/gray
                                          :container-style {:margin-left  6
                                                            :margin-right 2}}]
          [react/text-input {:placeholder     (i18n/label :t/search)
                             :blur-on-submit  true
                             :multiline       false
                             :ref             #(reset! input-ref %)
                             :style           styles/search-input
                             :default-value   search-filter
                             :on-focus        #(do
                                                 (when on-focus
                                                   (on-focus search-filter))
                                                 (reset! input-is-focused? true))
                             :on-change       (fn [e]
                                                (let [native-event (.-nativeEvent e)
                                                      text         (.-text native-event)]
                                                  (when on-change
                                                    (on-change text))))}]]
         (when show-cancel?
           [react/touchable-highlight
            {:on-press #(do
                          (when on-cancel
                            (on-cancel))
                          (.blur @input-ref)
                          (reset! input-is-focused? false))
             :style {:margin-left 16}}
            [react/text {:style {:color colors/blue}}
             (i18n/label :t/cancel)]])]))))

(defonce search-input-state
  (reagent/atom {:show?  false
                 :height (animation/create-value
                          (- styles/search-input-height))
                 :to-hide? false}))

(defn show-search!
  []
  (when-not (:to-hide? @search-input-state)
    (swap! search-input-state assoc :show? true)
    (animation/start
     (animation/timing (:height @search-input-state)
                       {:toValue         0
                        :duration        animation-duration
                        :easing          (.out (animation/easing)
                                               (.-quad (animation/easing)))
                        :useNativeDriver true})
     #(swap! search-input-state assoc :to-hide? true))))

(defn update-search-state!
  []
  (let [visible? (:to-hide? @search-input-state)]
    (swap! search-input-state assoc :show? visible?)
    (animation/set-value (:height @search-input-state)
                         (if visible? 0 (- styles/search-input-height)))))

(defn hide-search!
  []
  (utils/set-timeout
   #(swap! search-input-state assoc :show? false)
   350)
  (animation/start
   (animation/timing (:height @search-input-state)
                     {:toValue         (- styles/search-input-height)
                      :duration        animation-duration
                      :easing          (.in (animation/easing)
                                            (.-quad (animation/easing)))
                      :useNativeDriver true})
   #(swap! search-input-state assoc :to-hide? false)))

(defn search-input-wrapper
  [search-filter]
  (reagent/create-class
   {:component-will-unmount
    #(update-search-state!)
    :component-did-mount
    #(update-search-state!)
    :reagent-render
    (fn [search-filter]
      [search-input search-filter
       {:on-cancel #(do
                      (re-frame/dispatch [:search/filter-changed nil])
                      (hide-search!))
        :on-focus  (fn [search-filter]
                     (when-not search-filter
                       (re-frame/dispatch [:search/filter-changed ""])))
        :on-change (fn [text]
                     (re-frame/dispatch [:search/filter-changed text]))}])}))

(defn home-filtered-items-list
  [chats]
  [list/section-list
   {:style                       {:margin-bottom -35}
    :sections                    [{:title :t/chats
                                   :data  chats}]
    :key-fn                      first
    ;; true by default on iOS
    :stickySectionHeadersEnabled false
    :render-section-header-fn
    (fn [{:keys [title data]}]
      [react/view {:style {:height 40}}
       [react/text {:style styles/filter-section-title}
        (i18n/label title)]])
    :render-section-footer-fn
    (fn [{:keys [title data]}]
      (when (empty? data)
        [list/big-list-item
         {:text          (i18n/label :t/no-result)
          :text-color    colors/gray
          :hide-chevron? true
          :action-fn     #()
          :icon          (case title
                           "messages" :main-icons/one-on-one-chat
                           "browser" :main-icons/browser
                           "chats" :main-icons/message)
          :icon-color    colors/gray}]))
    :render-fn                   (fn [home-item]
                                   [inner-item/home-list-item home-item])}])
