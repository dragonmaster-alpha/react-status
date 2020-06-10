(ns quo.components.tooltip
  (:require [reagent.core :as reagent]
            [oops.core :refer [oget]]
            [quo.animated :as animated]
            [quo.react-native :as rn]
            [quo.design-system.colors :as colors]
            [quo.design-system.spacing :as spacing]
            [status-im.ui.components.icons.vector-icons :as vector-icons]))

(def ^:private initial-height 22)

(defn tooltip-style [{:keys [bottom-value animation]}]
  (merge
   (:base spacing/padding-horizontal)
   {:position    :absolute
    :align-items :center
    :left        0
    :right       0
    :top         (- bottom-value)
    :opacity     animation
    :transform   [{:translateY (animated/mix animation 10 0)}]}))

(defn container-style []
  {:z-index        2
   :align-items    :center
   :shadow-radius  16
   :shadow-opacity 1
   :shadow-color   (:shadow-01 @colors/theme)
   :shadow-offset  {:width 0 :height 4}})

(defn content-style []
  (merge (:base spacing/padding-horizontal)
         {:padding-vertical 6
          :elevation        2
          :background-color (:ui-background @colors/theme)
          :border-radius    8}))

(defn tooltip []
  (let [layout      (reagent/atom {:height initial-height})
        animation-v (animated/value 0)
        animation   (animated/with-timing-transition
                      animation-v
                      {:easing (:ease-in animated/easings)})
        on-layout   (fn [evt]
                      (let [width  (oget evt "nativeEvent" "layout" "width")
                            height (oget evt "nativeEvent" "layout" "height")]
                        (reset! layout {:width  width
                                        :height height})))]
    (fn  [{:keys [bottom-value]} & children]
      [:<>
       [animated/code {:exec (animated/set animation-v 1)}]
       [animated/view {:style          (tooltip-style {:bottom-value (- (get @layout :height)
                                                                        bottom-value)
                                                       :animation    animation})
                       :pointer-events :box-none}
        [animated/view {:style          (container-style)
                        :pointer-events :box-none}
         (into [rn/view {:style     (content-style)
                         :on-layout on-layout}]
               children)
         [vector-icons/icon :icons/tooltip-tip {:width           18
                                                :height          8
                                                :container-style {:elevation 3}
                                                :color           (:ui-background @colors/theme)}]]]])))
