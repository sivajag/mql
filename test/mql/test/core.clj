(ns mql.test.core
  (:use [mql.core] :reload)
  (:use [clojure.test]))

(def m
     {:cid
      {
       :visits {
                :id-1 {
                               :last-ts "1274166000040"
                               :first-ts "1274166000000"
                               :duration "40"}
                :id-2 {
                               :last-ts "1274166000040"
                               :first-ts "1274166000000"
                               :duration "40"}
                :id-3 {
                               :last-ts "1274166000040"
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

(deftest test-simple-select
  (is (= (select [:cid :promo] m) {:id-1 {:promo "p2"}
                                   :id-2 {:promo "p1"}})))