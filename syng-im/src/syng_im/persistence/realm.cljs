(ns syng-im.persistence.realm
  (:require [cljs.reader :refer [read-string]]
            [syng-im.utils.logging :as log]
            [syng-im.utils.types :refer [to-string]])
  (:refer-clojure :exclude [exists?]))

(set! js/Realm (js/require "realm"))

(def opts {:schema [{:name       "Contact"
                     :properties {:phone-number     "string"
                                  :whisper-identity "string"
                                  :name             "string"
                                  :photo-path       "string"}}
                    {:name       :kv-store
                     :primaryKey :key
                     :properties {:key   "string"
                                  :value "string"}}
                    {:name       :msgs
                     :primaryKey :msg-id
                     :properties {:msg-id       "string"
                                  :from         "string"
                                  :to           "string"
                                  :content      "string"    ;; TODO make it ArrayBuffer
                                  :content-type "string"
                                  :timestamp    "int"
                                  :chat-id      "string"
                                  :outgoing     "bool"}}]})

(def realm (js/Realm. (clj->js opts)))

(def schema-by-name (->> (:schema opts)
                         (mapv (fn [{:keys [name] :as schema}]
                                 [name schema]))
                         (into {})))

(defn field-type [schema-name field]
  (get-in schema-by-name [schema-name :properties field]))

(defn write [f]
  (.write realm f))

(defn create
  ([schema-name obj]
   (create schema-name obj false))
  ([schema-name obj update?]
   (.create realm (to-string schema-name) (clj->js obj) update?)))

(defmulti to-query (fn [schema-name operator field value]
                     operator))

(defmethod to-query :eq [schema-name operator field value]
  (let [value (to-string value)
        query (str (name field) "=" (if (= "string" (field-type schema-name field))
                                      (str "\"" value "\"")
                                      value))
        ;_     (log/debug query)
        ]
    query))

(defn get-by-field [schema-name field value]
  (-> (.objects realm (name schema-name))
      (.filtered (to-query schema-name :eq field value))))

(defn sorted [results field-name order]
  (.sorted results (to-string field-name) (if (= order :asc)
                                            false
                                            true)))

(defn page [results from to]
  (js/Array.prototype.slice.call results from to))

(defn single [result]
  (-> (aget result 0)))

(defn single-cljs [result]
  (some-> (aget result 0)
          (js->clj :keywordize-keys true)))

(defn decode-value [{:keys [key value]}]
  (read-string value))

(defn delete [obj]
  (write (fn []
           (.delete realm obj))))

(defn exists? [schema-name field value]
  (> (.-length (get-by-field schema-name field value))
     0))

(defn get-count [objs]
  (.-length objs))

(defn get-list [schema-name]
  (vals (js->clj (.objects realm schema-name) :keywordize-keys true)))
