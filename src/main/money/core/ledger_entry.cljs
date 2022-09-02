(ns money.core.ledger-entry
  (:require [cljs.spec.alpha :as s]
            [money.core.money :as m]))

(s/def ::account-id int?)
; Note: debit is negative, credit is positive
(s/def ::amount ::m/money)
(s/def ::description string?)

(s/def ::ledger-entry
  (s/keys :req [::account-id ::amount]
          :opt [::description]))
