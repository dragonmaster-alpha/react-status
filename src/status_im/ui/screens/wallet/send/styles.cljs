(ns status-im.ui.screens.wallet.send.styles
  (:require [status-im.ui.components.colors :as colors]))

(defn sheet [small-screen?]
  {:background-color        colors/white
   :border-top-right-radius 16
   :border-top-left-radius  16
   :padding-bottom          (if small-screen? 40 60)})

(defn header [small-screen?]
  {:flex-direction  :row
   :align-items     :center
   :justify-content :space-between
   :padding-top     (when-not small-screen? 16)
   :padding-left    16})

(def set-max-button
  {:height             35
   :border-radius      40
   :background-color   colors/blue-light
   :margin-horizontal  12
   :align-self         :flex-start
   :margin-bottom      12
   :align-items        :center
   :justify-content    :center
   :padding-horizontal 12})