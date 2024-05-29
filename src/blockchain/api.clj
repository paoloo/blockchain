(ns blockchain.api
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.json :as middleware]
            [blockchain.impl :as impl]))

(defroutes app-routes
  (GET  "/"                 []  (impl/serve-index))
  (GET  "/mine"             []  (impl/mine))
  (GET  "/chain"            []  (impl/chain))
  (POST "/transactions/new" req (impl/transaction-new req))
  (POST "/nodes/register"   req (impl/nodes-register req))
  (GET  "/nodes/resolve"    []  (impl/nodes-resolve))
  (route/resources              "/")
  (route/not-found              "Not Found"))

(def app
  (do
    (println (str "Initialzing blockchain with identifier " (impl/get-node-id) "."))
    (println "Inserting genesis block...")
    (impl/genesis-block)
    (println "Genesis block inserted. Starting API...")
    (->
      (wrap-defaults app-routes (assoc-in site-defaults [:security :anti-forgery] false))
      (middleware/wrap-json-body {:keywords? true})
      middleware/wrap-json-response)))
