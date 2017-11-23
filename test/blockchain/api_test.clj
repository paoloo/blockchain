(ns blockchain.api-test
  (:require [clojure.test :refer :all]
            [cheshire.core :as json]
            [ring.mock.request :as mock]
            [blockchain.api :refer :all]))

(deftest test-app
  (testing "chain setup and genesis block"
    (let [response (app (mock/request :get "/chain"))]
      (is (= (:status response) 200))
      (is (= ((json/parse-string (:body response) true) :length) 1))))

  (testing "mining"
    (let [response (app (mock/request :get "/mine"))]
      (is (= (:status response) 200))
      (is (= ((json/parse-string (:body response) true) :message) "New block forged"))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))

(deftest test-consensus
  (testing "add new node"
    (let [fields {:node "http://localhost:8090"}
          response (app (-> (mock/request :post "/nodes/register")
                            (mock/content-type "application/json")
                            (mock/body (json/generate-string fields))))]
      (is (= (:status response) 200))
      (is (= ((json/parse-string (:body response) true) :message) "Node inserted")))))


