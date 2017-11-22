(ns blockchain.nodes
  (:require [clj-http.client :as client]
            [cheshire.core :as json]
            [blockchain.core :as core]
            [blockchain.utils :as utils]))

(def nodes (atom []))

(defn node-add
  "Add a new node if it is not already there."
  [-data]
  (if-not (boolean (some #(= -data %) @nodes))
    (do (swap! nodes conj -data) {:message "Node inserted" :total-nodes @nodes})
    {:message "Node already inserted" :total-nodes @nodes}))

(defn register-node
  "Register a new node."
  [-url]
  (node-add (utils/URLnetLoc -url)))



(defn valid-chain
  "This method is responsible for checking if a chain is valid by looping through each block and verifying both the hash and the proof."
  [chain]
  (apply (fn [& x] (and (apply = x) (= (last x) true)))
         (rest
           (for [index (range (count chain)) :let [[before [current]] (split-at index chain)]]
             (= (utils/sha256hash(str(last before))) (get-in current [:previous-hash]))))) )

(defn resolve-conflicts
  "This method loops through all our neighbouring nodes, downloads their chains and verifies them using the above method. If a valid chain is found, whose length is greater than ours, we replace ours."
  []
  (if-not (= 0 (count @nodes))
    (doseq [node @nodes]
      (let [url (str "http://" node  "/chain")
            body (:body (client/get url))
            data (json/parse-string body true)
            length (data :length)
            chain (apply list (data :chain))]
        (if (and (> length (count @core/chain)) (valid-chain chain))
            (core/chain-reset (into '[] chain)))))
    "There are no registered nodes. There is no conflict to resolve"))
