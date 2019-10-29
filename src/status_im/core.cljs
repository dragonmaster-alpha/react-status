(ns status-im.core
  (:require [re-frame.core :as re-frame]
            [status-im.utils.error-handler :as error-handler]
            [status-im.utils.platform :as platform]
            [status-im.ui.components.react :as react]
            [reagent.core :as reagent]
            status-im.transport.impl.receive
            status-im.transport.impl.send
            [status-im.react-native.js-dependencies :as js-dependencies]
            [status-im.utils.logging.core :as utils.logs]
            cljs.core.specs.alpha))

(if js/goog.DEBUG
  (.ignoreWarnings (.-YellowBox js-dependencies/react-native)
                   #js
                    ["re-frame: overwriting"
                     "Warning: componentWillMount is deprecated and will be removed in the next major version. Use componentDidMount instead. As a temporary workaround, you can rename to UNSAFE_componentWillMount."
                     "Warning: componentWillUpdate is deprecated and will be removed in the next major version. Use componentDidUpdate instead. As a temporary workaround, you can rename to UNSAFE_componentWillUpdate."])
  (aset js/console "disableYellowBox" true))

(defn init [app-root]
  (utils.logs/init-logs)
  (error-handler/register-exception-handler!)
  (re-frame/dispatch [:init/app-started])
  (.registerComponent react/app-registry "StatusIm" #(reagent/reactify-component app-root)))
