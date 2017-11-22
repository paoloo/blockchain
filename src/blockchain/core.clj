(ns blockchain.core
  (:require [blockchain.utils :as utils]))

(def chain (atom []))

(def current-transactions (atom []))

(defn chain-add
  [-data]
  (swap! chain conj -data))

(defn chain-reset
  [new-chain]
  (reset! chain new-chain))

(defn last-block
  []
  (last @chain))

(defn transactions-add
  [-data]
  (swap! current-transactions conj -data))

(defn transactions-reset
  []
  (reset! current-transactions []))

(defn new-transaction
  [sender recipient amount]
  (transactions-add (into (sorted-map) {:sender sender :recipient recipient :amount amount}))
  (inc (get-in (last-block) [:index])))

(defn new-block
  ([proof previous-hash]
   (do
    (chain-add (into (sorted-map) {:index (count @chain) :timestamp (System/currentTimeMillis) :transactions @current-transactions :proof proof :previous-hash previous-hash}))
    (transactions-reset)
    (count @chain)))
  ([proof] (new-block proof (utils/sha256hash (str (last-block))))))
