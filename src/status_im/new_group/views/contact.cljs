(ns status-im.new-group.views.contact
  (:require-macros [status-im.utils.views :refer [defview]])
  (:require [re-frame.core :refer [subscribe dispatch dispatch-sync]]
            [status-im.components.react :refer [view]]
            [status-im.contacts.views.contact-inner :refer [contact-inner-view]]
            [status-im.components.item-checkbox :refer [item-checkbox]]
            [status-im.new-group.styles :as st]))

(defn on-toggle [whisper-identity]
  (fn [checked?]
    (println checked?)
    (let [action (if checked? :select-contact :deselect-contact)]
      (dispatch [action whisper-identity]))))

(defview new-group-contact [{:keys [whisper-identity] :as contact}]
  [checked [:is-contact-selected? whisper-identity]]
  [view st/contact-container
   [item-checkbox
    {:onToggle (on-toggle whisper-identity)
     :checked  checked
     :size     30}]
   [contact-inner-view contact]])
