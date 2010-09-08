(ns mql.core)

(def ops {:gt >
          :lt <
          :ge >=
          :le <=
          :eq =})

(defn select 
  ([ks m]
      (get-in m ks))
  ([ks f m]
     (f (select ks m))))

(defn nks [m pks nk]
  (if (= * nk)
    (keys (get-in m pks))
    (list nk)))

(defn key-seq-copy [pks nks]
  (apply concat (map (fn [pk] (map #(concat pk (vector %)) nks)) pks)))

(defn key-seq [m kys]
  (loop [ks [[]] k (first kys) rkys (rest kys)]
    (if (nil? k)
      ks
      (recur (key-seq-copy ks (nks m (first ks) k)) (first rkys) (rest rkys)))))

(defn valid-for-one? [mh key-seq ops-fn v]
  (if (not (nil? (get-in mh key-seq)))
    (ops-fn (read-string (get-in mh key-seq)) v)))

(defn valid? [me key-seqs ops-fn v]
  (let [mh (apply hash-map me)]
    (some #(identity %) (map #(valid-for-one? mh % ops-fn v) key-seqs))))

(defn where [ks o v]
  (let [ops-fn (o ops)]
    (fn [sm]
      (let [key-seqs (key-seq sm ks)]
        (filter (fn [me] (valid? me key-seqs ops-fn v)) sm)))))