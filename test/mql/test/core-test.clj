(ns mql.test.core-test
  (:use [mql.core] :reload)
  (:use [clojure.test]))

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

(deftest test-key-seq-copy
 (are [x y] (= x y)
      '((:cid :promo :id-1) (:cid :promo :id-2)) (key-seq-copy [[:cid :promo]] [:id-1 :id-2])
      '((:cid :promo1 :id) (:cid :promo2 :id)) (key-seq-copy [[:cid :promo1] [:cid :promo2]] [:id])))

(deftest test-key-sequence
  (are [x y] (= x y)
       [[:cid :promo :id-1 :promo] [:cid :promo :id-2 :promo]] (key-seq m [:cid :promo * :promo])
       ;[[:cid :purchase :id-1 :purchase?] [:cid :purchase :id-2 :purchase?]] (key-seq m [:cid * * :purchase?])
       ))

(deftest test-nks
  (are [x y] (= x y)
       '(:promo) (nks m [] :promo)
       '(:id-1 :id-2) (nks m [:cid :promo] *)))


(deftest test-simple-select
  (is (= (select [:cid :promo] m) {:id-1 {:promo "p2"}
                                   :id-2 {:promo "p1"}})))

(deftest test-select-with-where
  (is (= (keys (select [:cid :purchase] (where [* :total-dollars] :gt 980) m)) [:id-3]))
  (is (= (keys (select [:cid :visits] (where [* :last-ts] :ge 1274166000001) m)) [:id-1 :id-2])))


