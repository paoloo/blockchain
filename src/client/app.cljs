(ns client.app
    (:require [ajax.core :refer [GET POST]]
              [reagent.core :as r]
              [reagent.dom :as rdom]
              [clojure.string :as str]))

(.log js/console "[+] react+reagent+clojurescript carregado! yey!!")

(defonce timer (r/atom (js/Date.)))

(defonce time-updater (js/setInterval
                       #(reset! timer (js/Date.)) 1000))

(defonce chain (r/atom []))
(defonce size (r/atom 0))

(defn update-chain [response]
  (do
    ;update chain size
    (reset! size (:length response))
    ;update chain
    (let [new-chain (:chain response)]
      (if (not= new-chain @chain)
          (reset! chain new-chain)))))

(defn fetch-chain []
  (GET "/chain" {:response-format :json :keywords? true :handler update-chain}))

(defonce chain-updater
  (js/setInterval fetch-chain 5000))

;; ===================================================================================
;; [REACT COMPONENTS]
;; ===================================================================================

(defn greeting [message]
  [:h1 message])

(defn clock []
  (let [time-str (-> @timer .toTimeString (str/split " ") first)]
    [:div.example-clock
     {:style {:color "#f34"}}
     time-str]))

(defn show-chain-count []
    [:div
     "There are "
     [:strong (str @size)]
     " blocks."])

(defn show-latest-blocks []
  (let [latest-3 (take-last 3 @chain)]
    [:h2 "Latest 3 chain blocks"]
    [:div#list
     [:ul
      (for [block latest-3]
        [:li
         [:div
          [:pre (str "index: " (:index block) " " (str "Timestamp: " (:timestamp block)) " "  (str "Nonce: " (:proof block))  ) ]
          [:pre.small (str "Transaction metadata: " (:transactions block))]]])]]))


(defn create-transaction-component []
  [:div.transaction-container
   [:div
     [:label "sender:"]
     [:input {:type "text" :id "sender"}]]
   [:div
     [:label "recipient:"]
     [:input {:type "text" :id "recipient"}]]
   [:div
     [:label "amount:"]
     [:input {:type "text" :id "amount"}]]
   [:div
     [:button {:on-click #(POST "/transactions/new" {:params {:sender (.-value (js/document.getElementById "sender"))
                                                          :recipient (.-value (js/document.getElementById "recipient"))
                                                          :amount (.-value (js/document.getElementById "amount"))}
                                                   :headers {"Content-Type" "application/json"}
                                                   :format :json
                                                   :keywords? true
                                                   :handler notify-transaction
                                                   })}
                                                  "Create Transaction"]
     [:button {:on-click #(GET "/mine" {})} "Mine"]]])

(defn notify-transaction [response]
    (do
      (set! (.-value (js/document.getElementById "sender")) "")
      (set! (.-value (js/document.getElementById "recipient")) "")
      (set! (.-value (js/document.getElementById "amount")) "")
      ))


(defn dashboard-screen []
  [:div
   [greeting "Transactions happening at the moment"]
   [clock]
   [show-chain-count]
   [show-latest-blocks]
   [create-transaction-component]
   ])

(defn ^:export run []
  (rdom/render [dashboard-screen] (js/document.getElementById "app")))
