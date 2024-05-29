(defproject blockchain "0.1.1-BURNINGASS"
  :description "A simple clojure implementation of a blockchain"
  :url "https://github.com/paoloo/blockchain"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.5.1"]
                 [clj-http "3.7.0"]
                 [cheshire "5.8.0"]
                 [ring/ring-defaults "0.2.1"]
                 [ring/ring-json "0.4.0"]
                 ; Clojure data.JSON library
                 [org.clojure/data.json "0.2.6"]
                 ; ClojureScript - Clojure that compiles to JS
                 [org.clojure/clojurescript "1.10.773"]
                 ; Reagent - React for ClojureScript
                 [reagent "1.2.0"]
                 ; add react to the project
                 [cljsjs/react "17.0.2-0"]
                 [cljsjs/react-dom "17.0.2-0"]
                 ; add ajax to the project
                 [cljs-ajax "0.5.1"]
                 ; figwheel - hot code reloading
                 [figwheel "0.5.19"]]

  :plugins [[lein-ring "0.12.5"]
            ; figwheel - hot code reloading
            [lein-figwheel "0.5.19"]
            ; cljsbuild - ClojureScript build tool
            [lein-cljsbuild "1.1.7"]
            ; do - run shell commands
            [lein-pdo "0.1.1"]]

  :resource-paths ["resources" "target"]
  :clean-targets ^{:protect false} [:target-path]
  :target-path "target/%s"
  :cljsbuild {:builds {:client
                      {:source-paths ["src"]
                       :compiler {:output-dir "target/public/client"
                                  :asset-path "client"
                                  :output-to  "resources/public/client.js"}}}}

  :ring {:handler blockchain.api/app
         :port 8090}

  :figwheel {:repl false
             :hawk-options    {:watcher :polling}
             :http-server-root "public"}

  :profiles  {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}

              :dev {:cljsbuild
                   {:builds {:client
                             {:figwheel {:on-jsload "client.app/run"}
                              :compiler {:main "client.app"
                                         :optimizations :none}}}}}

              :prod {:cljsbuild
                    {:builds {:client
                              {:compiler {:optimizations :advanced
                                          :elide-asserts true
                                          :pretty-print false}}}}}

              :test {:dependencies [[javax.servlet/servlet-api "2.5"]
                                   [ring/ring-mock "0.3.0"]]}}

   :aliases {"up" ["pdo" "cljsbuild" "auto" "client," "ring" "server-headless"]}
)
