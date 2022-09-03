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

(defn remove-nth [coll n]
  [(get coll n) (concat (take n coll) (drop (inc n) coll))])

(defn go-over-ledger-entries [entries]
  (for [n (range 0 (count entries))]
    (remove-nth entries n)))

(defn get-ledger-entries-for-account [entries account-id]
  (filter #(= account-id (::le/account-id (first %)))
          (go-over-ledger-entries entries)))

(defn create-modified-account-screen-transaction [transaction account-id]
  (let [ledger-entries (::t/ledger-entries transaction)
        without-ledger-entries (dissoc transaction ::t/ledger-entries)]
    (map #(-> without-ledger-entries
              (assoc :account-ledger-entry (first %))
              (assoc :other-ledger-entries (second %)))
         (get-ledger-entries-for-account ledger-entries account-id))))

(defn sort-transactions-by-date [transactions]
  (sort-by ::t/date transactions))

(defn build-account-screen-transactions [transactions account-id]
  (sort-transactions-by-date
    (flatten (map #(create-modified-account-screen-transaction
                     (second %) account-id)
                  transactions))))
