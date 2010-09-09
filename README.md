Inspired by http://github.com/MrHus/rql

# What is mql

mql is clojure library to query map data structures

## Usage

Given a map

(def m
     {:cid
      {
       :visits {
                :id-1 {
                       :last-ts "1284166000040"
                       :first-ts "1274166000000"
                       :duration "40"}
                :id-2 {
                       :last-ts "1274166000040"
                       :first-ts "1274166000000"
                       :duration "40"}
                :id-3 {
                       :last-ts "1264166000040"
                       :first-ts "1274166000000"
                       :duration "40"}}
       
       :promo{
              :id-1 {:promo "p2"}
              :id-2 {:promo "p1"}}
       
       :purchase {
                  :id-1
                  {:order-id "order-id-1"
                   :total-dollars "970.00"
                   :purchase? "true",
                   :merchant-total-dollars "1000.00"}
                  :id-3
                  {:order-id "order-id-2"
                   :total-dollars "1000.00"
                   :purchase? ""
                   :merchant-total-dollars "1000.00"}}}})

## Select: simple select

   (select [:cid :promo] m)  
   will return         
           {:id-1 {:promo "p2"} :id-2 {:promo "p1"}} 

## Select: with filtering

   (select [:cid :purchase] (where [* :total-dollars] :gt 980) m) 
   will return
          {:id-1
              {:order-id "order-id-1"
               :total-dollars "970.00"
               :purchase? "true",
               :merchant-total-dollars "1000.00"}}

           
   You can use :gt, :lt, :ge, :le and :eq as logical operator in where clause. In where clause for key-seq you can use * if a key is dynamic. The last key in where clause key-seq should not be *. 

## Limitations:

- There could be only one * in Where Clause
- Select clause cannot have * now

## License

Copyright (C) 2010 Siva Jagadeesan

Distributed under the Eclipse Public License, the same as Clojure.

