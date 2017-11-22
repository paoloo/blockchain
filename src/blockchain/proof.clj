(ns blockchain.proof
  (:require [blockchain.utils :as utils]))

(defn valid-proof
  [last_proof proof]
  (=
   (subs (.toString (.reverse (StringBuilder. (utils/sha256hash (str (str last_proof) (str proof)))))) 0 4)
   "0000" ))

(defn proof-of-work
  [last_proof]
  (loop [x 0] (if (valid-proof last_proof x) x (recur (inc x)))))
