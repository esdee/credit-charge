(ns credit-charge.queries
  (:require [datomic.api :refer (q db) :as d]))

(def ^:private dbase (atom nil))

(defn initialize!
  [conn]
  (reset! dbase (db conn)))

(defn get-charges
  ([] (get-charges false))
  ([raw?]
   (let [charges (q '[:find ?amount ?createdAt ?status ?symbol ?firstName ?lastName
                      :where
                      [?ccc :creditCardCharge/amount ?amount]
                      [?ccc :creditCardCharge/createdAt ?createdAt]
                      [?ccc :creditCardCharge/status ?statusId]
                      [?statusId :db/ident ?status]
                      [?ccc :creditCardCharge/currency ?currency]
                      [?currency :currency/symbol ?symbol]
                      [?ccc :creditCardCharge/customer ?customer]
                      [?customer :customer/firstName ?firstName]
                      [?customer :customer/lastName ?lastName]]
                    @dbase)]
     (if raw?
       ; just return the seq of tuples as is
       charges
       ; turn the seq of tuples into a seq of maps
       (->> charges
            (map list (repeat [:amount :createdAt :status :symbol :firstName :lastName]))
            (map (fn [[ks vs]] (zipmap ks vs))))))))