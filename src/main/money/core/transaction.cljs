(ns money.core.transaction
  (:require [cljs.spec.alpha :as s]
            [money.core.ledger-entry :as le]
            [money.core.money :as m]))

(s/def ::id int?)
(s/def ::description string?)
(s/def ::date int?) ; unix time

(s/def ::transaction
  (s/keys :req [::id ::description ::le/ledger-entries]))

(defn transaction-valid? [transaction]
  (if-not (s/valid? ::transaction transaction)
    false
    (let [entries (::le/ledger-entries transaction)
          amounts (map ::le/amount entries)
          by-currencies (group-by ::m/currency-id amounts)

          sum-up-single-currency
          (fn [[currency amounts]]
            (reduce m/add-money {::m/amount 0 ::m/currency-id currency} amounts))

          sums (map sum-up-single-currency by-currencies)]
    (every? #(zero? (::m/amount %)) sums))))

