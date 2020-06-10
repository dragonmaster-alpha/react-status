(ns status-im.ui.components.search-input.view
  (:require [reagent.core :as reagent]
            [status-im.i18n :as i18n]
            [quo.core :as quo]
            [quo.design-system.colors :as colors]))

(defn search-input [{:keys [search-active?]}]
  (let [input-ref      (atom nil)
        search-active? (or search-active? (reagent/atom nil))]
    (fn [{:keys [on-focus on-change on-cancel search-filter auto-focus]}]
      [quo/text-input {:placeholder     (i18n/label :t/search)
                       :blur-on-submit  true
                       :multiline       false
                       :ref             #(reset! input-ref %)
                       :default-value   search-filter
                       :auto-focus      auto-focus
                       :on-cancel       on-cancel
                       :auto-correct    false
                       :auto-capitalize :none
                       :input-style     {:height 36}
                       :before          {:icon      :main-icons/search
                                         :style     {:padding-horizontal 8}
                                         :on-press  #(.focus ^js @input-ref)
                                         :icon-opts {:color (:icon-02  @colors/theme)}}
                       :on-focus        #(do
                                           (when on-focus
                                             (on-focus search-filter))
                                           (reset! search-active? true))
                       :on-blur         #(reset! search-active? false)
                       :on-change       (fn [e]
                                          (let [^js native-event (.-nativeEvent ^js e)
                                                text             (.-text native-event)]
                                            (when on-change
                                              (on-change text))))}])))
