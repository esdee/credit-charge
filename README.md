# credit-charge

This is a Clojure version of the code challenge repo
This challenge was blogged about by Donn Felkner
http://www.donnfelker.com/how-to-hire-programmers/

This version uses
Compojure, Hiccup and Datomic

I wrote it to practice modelling traditional relational data using Datomic.

The schema and seed data for the Datomic db are in the /resources directory.

## Prerequisites

You will need [Leiningen][1] 1.7.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## Running

Clone the project
cd into teh cloned directory
Open up a repl - lein repl
In the repl - (require '[credit-charge.handler :refer (start)])
Then (start)

Open up a browser to http://localhost:8080/charges

## License

Feel free to do with it as you will