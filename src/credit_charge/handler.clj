(ns credit-charge.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.adapter.jetty :refer (run-jetty)]
            [hiccup.core :refer (html)]
            [credit-charge.database :as db]
            [credit-charge.queries :as q]))

(defn charges-list
  [charges filter-token & [alert]]
  (let [->time #(let [gc (java.util.GregorianCalendar.)
                      _ (.setTimeInMillis gc %)]
                  (.getTime gc))]
    [:table (when alert {:class alert})
     (for [charge (filter #(= filter-token (:status %)) charges)]
       [:tr
        [:td (str (:firstName charge) " " (:lastName charge))]
        [:td (str (:symbol charge) (float (/ (:amount charge) 100)))]
        [:td (->time (:createdAt charge))]])]))

(defn charges-page
  [charges]
  (html
   [:head]
   [:body
    [:div#charges
     [:h1 "Failed Charges"]
     (charges-list charges :creditCardCharge.status/failed "alert-failed")
     [:h1 "Disputed Charges"]
     (charges-list charges :creditCardCharge.status/disputed)
     [:h1 "Successful Charges"]
     (charges-list charges :creditCardCharge.status/succeeded)]]))

(defroutes app-routes
  (GET "/charges" []
       (charges-page (q/get-charges)))
  (route/resources "/")
  (route/not-found "Not Found"))

(defn start
  "Start the app with the test data"
  []
  (let [uri "datomic:mem://creditCharges"]
    (do
      (q/initialize! (db/recreate-database! uri
                                            "resources/credit_card_schema.dtm"
                                            "resources/credit_card_seed_data.dtm"))
      (run-jetty (handler/site app-routes)
                 {:port 8080 :join? false}))))