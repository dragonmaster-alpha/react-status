(ns status-im.ui.screens.popover.views
  (:require-macros [status-im.utils.views :as views])
  (:require [status-im.ui.components.animation :as anim]
            [reagent.core :as reagent]
            [status-im.ui.components.react :as react]
            [re-frame.core :as re-frame]
            [status-im.ui.screens.wallet.signing-phrase.views :as signing-phrase]
            [status-im.ui.screens.wallet.request.views :as request]))

(defn hide-panel-anim
  [bottom-anim-value alpha-value window-height]
  (anim/start
   (anim/parallel
    [(anim/spring bottom-anim-value {:toValue         (- window-height)
                                     :useNativeDriver true})
     (anim/timing alpha-value {:toValue         0
                               :duration        500
                               :useNativeDriver true})])))

(defn show-panel-anim
  [bottom-anim-value alpha-value]
  (anim/start
   (anim/parallel
    [(anim/spring bottom-anim-value {:toValue         0
                                     :useNativeDriver true})
     (anim/timing alpha-value {:toValue         0.4
                               :duration        500
                               :useNativeDriver true})])))

(defn popover-view [popover window-height]
  (let [bottom-anim-value (anim/create-value window-height)
        alpha-value       (anim/create-value 0)
        clear-timeout     (atom nil)
        current-popover   (reagent/atom nil)
        update?           (reagent/atom nil)]
    (reagent/create-class
     {:component-will-update (fn [_ [_ popover _]]
                               (when @clear-timeout (js/clearTimeout @clear-timeout))
                               (cond
                                 @update?
                                 (do (reset! update? false)
                                     (show-panel-anim bottom-anim-value alpha-value))

                                 (and @current-popover popover)
                                 (do (reset! update? true)
                                     (js/setTimeout #(reset! current-popover popover) 600)
                                     (hide-panel-anim bottom-anim-value alpha-value (- window-height)))

                                 popover
                                 (do (reset! current-popover popover)
                                     (show-panel-anim bottom-anim-value alpha-value))

                                 :else
                                 (do (reset! clear-timeout (js/setTimeout #(reset! current-popover nil) 500))
                                     (hide-panel-anim bottom-anim-value alpha-value (- window-height)))))
      :reagent-render        (fn []
                               (when @current-popover
                                 (let [view (:view @current-popover)]
                                   [react/view {:position :absolute :top 0 :bottom 0 :left 0 :right 0}
                                    [react/animated-view {:style {:flex 1 :background-color :black :opacity alpha-value}}]
                                    [react/animated-view {:style
                                                          {:position  :absolute
                                                           :height    window-height
                                                           :left      0
                                                           :right     0
                                                           :transform [{:translateY bottom-anim-value}]}}
                                     [react/touchable-highlight {:style {:flex 1} :on-press #(re-frame/dispatch [:hide-popover])}
                                      [react/view {:flex 1 :align-items :center :justify-content :center}
                                       [react/view {:background-color :white
                                                    :border-radius    16
                                                    :margin           32
                                                    :shadow-offset    {:width 0 :height 2}
                                                    :shadow-radius    8
                                                    :shadow-opacity   1
                                                    :shadow-color     "rgba(0, 9, 26, 0.12)"}
                                        (cond
                                          (vector? view)
                                          view

                                          (= :signing-phrase view)
                                          [signing-phrase/signing-phrase]

                                          (= :share-account view)
                                          [request/share-address]

                                          :else
                                          [view])]]]]])))})))

(views/defview popover []
  (views/letsubs [popover [:popover/popover]
                  {window-height :height} [:dimensions/window]]
    [popover-view popover window-height]))