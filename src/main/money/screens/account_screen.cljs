(ns money.screens.account-screen
  (:require [money.core.account :as a]
            [money.core.ledger-entry :as le]
            [money.core.transaction :as t]))

(defn- get-ledger-entries [[k v]]
  (::t/ledger-entries v))

(defn- ledger-entries-contain-account? [entries account-id]
  (some #(= (::le/account-id %) account-id) entries))

(defn filter-transactions-by-account [transactions account-id]
  (filter #(-> %
               get-ledger-entries
               (ledger-entries-contain-account? account-id))
          transactions))

(defn- get-date [[k v]]
  (::t/date v))

(defn sort-transactions-by-date [transactions]
  (sort-by get-date transactions))
