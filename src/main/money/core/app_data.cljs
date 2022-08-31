(ns money.core.app-data
  (:require [clojure.spec.alpha :as s]
            [money.core.account :as a]
            [money.core.currency :as c]
            [money.core.owner :as o]
            [money.core.transaction :as t]))

(defn sorted-map? [m]
  (instance? cljs.core/PersistentTreeMap m))

(s/def ::accounts
  (s/map-of int? ::a/account :kind sorted-map?))
(s/def ::currencies
  (s/map-of int? ::c/currency :kind sorted-map?))
(s/def ::owners
  (s/map-of int? ::o/owners :kind sorted-map?))
(s/def ::transactions
  (s/map-of int? ::t/transaction :kind sorted-map?))

(s/def ::data
  (s/keys :req [::accounts ::currencies ::owners ::transactions]))


