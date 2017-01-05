(ns status-im.components.status-bar
  (:require [status-im.components.react :as ui]
            [status-im.utils.platform :refer [platform-specific]]))

(defn status-bar [{type :type
                   :or  {type :default}}]
  (let [{:keys [height
                bar-style
                translucent?
                color]} (get-in platform-specific [:component-styles :status-bar type])]
    [ui/view
     [ui/status-bar {:background-color color
                     :translucent      translucent?
                     :bar-style        bar-style}]
     [ui/view {:style {:height           height
                       :background-color color}}]]))