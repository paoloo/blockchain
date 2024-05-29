(ns blockchain.impl
  (:require [blockchain.core :as core]
            [blockchain.proof :as proof]
            [blockchain.nodes :as nodes]
            [blockchain.utils :as utils]))

(def node-identifier (utils/uuidv4))

(defn get-node-id
  []
  node-identifier)

(defn output-json
  [status-code information]
  {:status status-code
   :headers {"Content-Type" "application/json"}
   :body information })

(defn json-ok
  [information]
  (output-json 200 information))

(defn genesis-block
  []
  (core/new-block 100 1))

(defn chain
  []
  (json-ok {:length (count @core/chain) :chain  @core/chain} ))

(defn mine
  []
  (let
   [proof (proof/proof-of-work (get-in (core/last-block) [:proof]))]
   (do
     (core/new-transaction "0" (get-node-id) 1)
     (core/new-block proof)
     (json-ok {:message "New block forged" :index (get-in (core/last-block) [:index]) :transactions (get-in (core/last-block) [:transactions]) :proof proof :previous_hash (get-in (core/last-block) [:previous-hash]) }))))

(defn transaction-new
  [x]
  (if (and (get-in x [:body :sender]) (get-in x [:body :recipient]) (get-in x [:body :amount]))
    (json-ok {:message (str "Transaction will be added to Block " (str (core/new-transaction (get-in x [:body :sender]) (get-in x [:body :recipient]) (get-in x [:body :amount]))))})
    (output-json 400 {:error "A parameter is missing on the request." })))

(defn nodes-register
  [x]
  (json-ok (nodes/register-node (get-in x [:body :node] ))))

(defn nodes-resolve
  []
  (let [k (nodes/resolve-conflicts)]
    (if (= (type k) (type ""))
      k
      "All conflicts were resolved. Chain was updated")))

(defn serve-index "serve-index"
  []
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    (slurp "resources/public/index.html")})
