(defproject blockchain "0.1.1-BURNINGASS"
  :description "A simple clojure implementation of a blockchain"
  :url "https://github.com/paoloo/blockchain"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.5.1"]
                 [clj-http "3.7.0"]
                 [cheshire "5.8.0"]
                 [ring/ring-defaults "0.2.1"]
                 [ring/ring-json "0.4.0"]]

  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler blockchain.api/app
         :port 8090}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]]}})
