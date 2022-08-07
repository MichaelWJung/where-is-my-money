(ns money.core.money
  (:require [cljs.spec.alpha :as s]))

(s/def ::amount int?)
(s/def ::currency-id int?)

(s/def ::money (s/keys :req [::amount ::currency-id]))

(defn- is-money? [v]
  (and (map? v)))

(defn add-money [m1 m2]
  (let [c1 (::currency-id m1)
        c2 (::currency-id m2)]
    (if (not= c1 c2)
      (throw (ex-info "cannot add money with different currencies"
                      {:currency1 c1
                       :currency2 c2}))
      {::amount (+ (::amount m1) (::amount m2))
       ::currency-id c1})))
