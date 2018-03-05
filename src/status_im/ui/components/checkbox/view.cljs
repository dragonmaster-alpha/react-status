(ns status-im.ui.components.checkbox.view
  (:require [status-im.ui.components.checkbox.styles :as styles]
            [status-im.ui.components.react :as react]
            [status-im.utils.platform :as platform]))

(defn checkbox [{:keys [on-value-change checked?]}]
  (if platform/android?
    [react/view {:style styles/wrapper}
     [react/check-box {:on-value-change on-value-change
                       :value           checked?}]]
    [react/touchable-highlight (merge {:style styles/wrapper}
                                      (when on-value-change {:on-press #(on-value-change (not checked?))}))
     [react/view (styles/icon-check-container checked?)
      (when checked?
        [react/icon :check_on styles/check-icon])]]))
