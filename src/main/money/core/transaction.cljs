(ns money.core.transaction
  (:require [cljs.spec.alpha :as s]
            [money.core.account :as a]
            [money.core.ledger-entry :as le]
            [money.core.money :as m]))

(s/def ::id int?)
(s/def ::description string?)
(s/def ::date int?) ; unix time

(s/def ::transaction
  (s/keys :req [::id ::description ::le/ledger-entries]))

(defn- single-owner-entries-valid? [entries]
  (let [amounts (map ::le/amount entries)
        by-currencies (group-by ::m/currency-id amounts)

        sum-up-single-currency
        (fn [[currency amounts]]
          (reduce m/add-money {::m/amount 0 ::m/currency-id currency} amounts))

        sums (map sum-up-single-currency by-currencies)]
    (every? #(zero? (::m/amount %)) sums)))

(defn- get-entry-owner-id [entry accounts]
  (get-in accounts [(::le/account-id entry) ::a/owner-id]))

(defn- satisfies-link? [entry entries accounts]
  (let [account (get accounts (::le/account-id entry))]
    (if-not (contains? account ::a/linked-account-id)
      true
      (let [linked-id (::a/linked-account-id account)
            by-acc-id (group-by ::a/account-id entries)]
        (contains? by-acc-id linked-id)))))

(defn- referenced-accounts-exist? [entries accounts]
  (let [account-ids (map ::le/account-id entries)]
    (every? #(contains? accounts %) account-ids)))

(defn- check-per-owner-and-currency-balances [entries accounts]
  (let [entries-with-owners
        (map #(vector (get-entry-owner-id % accounts) %) entries)
        entries-by-owner
        (map #(map second %) (vals (group-by first entries-with-owners)))]
    (every? single-owner-entries-valid? entries-by-owner)))

(defn transaction-valid? [transaction accounts]
  (let [entries (::le/ledger-entries transaction)]
    (and (s/valid? ::transaction transaction)
         (referenced-accounts-exist? entries accounts)
         (check-per-owner-and-currency-balances entries accounts)
         (every? #(satisfies-link? % entries accounts) entries))))

