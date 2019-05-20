(ns status-im.utils.ethereum.resolver
  (:require [status-im.utils.ethereum.ens :as ens])
  (:refer-clojure :exclude [name]))

(def default-hash "0x0000000000000000000000000000000000000000000000000000000000000000")
(defn contenthash [registry ens-name cb]
  (ens/resolver registry
                ens-name
                #(ens/contenthash % ens-name cb)))

(defn content [registry ens-name cb]
  (ens/resolver registry
                ens-name
                #(ens/content % ens-name cb)))

(defn name [registry ens-name cb]
  (ens/resolver registry
                ens-name
                #(ens/name % ens-name cb)))

(defn pubkey
  [registry ens-name cb]
  {:pre [(ens/is-valid-eth-name? ens-name)]}
  (ens/resolver registry
                ens-name
                #(ens/pubkey % ens-name cb)))
