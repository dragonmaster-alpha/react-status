(ns status-im.ethereum.stateofus
  (:require [clojure.string :as string]
            [status-im.ethereum.json-rpc :as json-rpc]
            [status-im.utils.config :as config]
            [status-im.ethereum.ens :as ens]))

(def domain "stateofus.eth")

(defn subdomain
  [username]
  (str username "." domain))

(defn username-with-domain
  "checks if the username is a status username or a ens name
  for that we check if there is a dot in the username, which
  would indicate that there is already a domain name so we don't
  concatenated stateofus domain to it"
  [username]
  (when (and (string? username)
             (seq username))
    (if (string/includes? username ".")
      username
      (subdomain username))))

(defn username [name]
  (when (and name (string/ends-with? name domain))
    (first (string/split name "."))))

(def old-registrars
  (merge
   {:mainnet "0xDB5ac1a559b02E12F29fC0eC0e37Be8E046DEF49"}
   (when config/test-stateofus?
     {:testnet "0x11d9F481effd20D76cEE832559bd9Aca25405841"})))

(def registrars-cache (atom {}))

(defn get-registrar [chain callback]
  (if-let [contract (get @registrars-cache chain)]
    (callback contract)
    (let [registry (get ens/ens-registries chain)]
      (ens/get-owner
       registry
       domain
       (fn [addr]
         (let [addr (or addr (get old-registrars chain))]
           (swap! registrars-cache assoc chain addr)
           (callback addr)))))))

(defn get-cached-registrar [chain]
  (get @registrars-cache chain (get old-registrars chain)))

(defn lower-case? [s]
  (when s
    (= s (string/lower-case s))))

(defn valid-username? [username]
  (boolean
   (and (lower-case? username)
        (re-find #"^[a-z0-9]+$" username))))

(defn get-expiration-time
  [registrar label-hash cb]
  (json-rpc/eth-call
   {:contract registrar
    :method "getExpirationTime(bytes32)"
    :params [label-hash]
    :outputs ["uint256"]
    :on-success
    (fn [[release-time]]
      ;;NOTE: returns a timestamp in s and we want ms
      (cb (* release-time 1000)))}))
