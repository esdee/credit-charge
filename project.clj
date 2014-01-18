(defproject credit-charge "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.6"]
                 [com.datomic/datomic-free "0.9.4384"]
                 [ring/ring-jetty-adapter "1.2.1"]
                 [hiccup "1.0.4"]]
  :plugins [[lein-ring "0.8.10"]]
  ;:ring {:handler credit-charge.handler/app}
  :profiles
  {:dev {:dependencies [;[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})