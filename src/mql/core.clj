(ns mql.core)

(defn select [ks m]
  (get-in m ks))