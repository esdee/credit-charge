(ns credit-charge.database
  (:require [datomic.api :refer (q db) :as d]))

(defn initialize-database!
  "Deletes database, creates database, transacts the schema"
  [uri schema-file]
  (let [_ (d/delete-database uri)
        _ (d/create-database uri)
        conn (d/connect uri)
        _ @(d/transact conn
                      (read-string (slurp schema-file)))]
    conn))


(defn seed-database!
  "Seed the database with data from a file"
  [conn seed-data-file]
  (do
    @(d/transact conn
               (read-string (slurp seed-data-file)))
    conn))

(defn recreate-database!
  "Drop the database, recreate it with schema and seed it with data."
  [uri schema-file seed-data-file]
  (seed-database!
   (initialize-database! uri schema-file)
   seed-data-file))