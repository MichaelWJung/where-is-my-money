(ns money.core.account
  (:require [cljs.spec.alpha :as s]))

; trading account is for multi-currency transactions
; TODO: use keywords without namespaces instead
(def account-type? #{::asset ::liability ::equity ::income ::expense ::trading})

(def link-type? #{::identical ::mirrored})

(s/def ::name string?)
(s/def ::currency-id int?)
(s/def ::parent-id (s/nilable int?))
(s/def ::owner-id int?)
(s/def ::type account-type?)
(s/def ::linked-account-id int?)
(s/def ::link-type link-type?)
(s/def ::account-id-pair (s/coll-of int? :kind vector? :count 2 :distinct true))

(defn link-defined-properly? [acc]
  (let [linked-account-id? (contains? acc ::linked-account-id)
        link-type? (contains? acc ::link-type)]
    (if (or linked-account-id? link-type?)
      (and linked-account-id? link-type?)
      true)))

(defn sorted-map? [m]
  (instance? cljs.core/PersistentTreeMap m))

(s/def ::account
  (s/and (s/keys :req [::name ::currency-id ::parent-id ::owner-id ::type]
          :opt [::linked-account-id ::link-type])
         link-defined-properly?))

(s/def ::accounts
  (s/map-of int? ::account :kind sorted-map?))

(s/def ::virtual-account
  (s/keys :req [::name ::account-id-pair ::parent-id]))
