(ns money.core.ledger-entry
  (:require [cljs.spec.alpha :as s]
            [money.core.money :as m]))

(s/def ::account-id int?)
(s/def ::amount ::m/money)
(s/def ::description string?)
(s/def ::owner-id int?)

(s/def ::ledger-entry
  (s/keys :req [::account-id ::amount ::owner-id]
          :opt [::description]))
(s/def ::ledger-entries
  (s/coll-of ::ledger-entry :kind vector?))
