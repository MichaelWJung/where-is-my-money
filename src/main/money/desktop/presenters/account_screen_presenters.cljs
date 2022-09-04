(ns money.desktop.presenters.account-screen-presenters
  (:require [money.core.account :as a]
            [money.core.ledger-entry :as le]
            [money.core.money :as m]
            [money.core.transaction :as t]
            [money.screens.account-screen :as as]
            [goog.string :as gstring]
            [goog.string.format]))

(defn present-date [date]
  (str date))

(defn present-description [description]
  description)

(defn present-transfer [other-ledger-entries accounts]
  (::a/name (accounts (::le/account-id (first other-ledger-entries)))))

(defn present-debit [account-ledger-entry]
  (let [amount (get-in account-ledger-entry [::le/amount ::m/amount])]
    (if (>= amount 0)
      ""
      (gstring/format "%.2f" (/ (- amount) 100)))))

(defn present-credit [account-ledger-entry]
  (let [amount (get-in account-ledger-entry [::le/amount ::m/amount])]
    (if (<= amount 0)
      ""
      (gstring/format "%.2f" (/ amount 100)))))

(defn present-balance [balance]
  (gstring/format "%.2f" (/ (::m/amount balance) 100)))

(defn present-account-screen-line [transaction accounts]
  {:date (present-date (::t/date transaction))
   :description (present-description (::t/description transaction))
   :transfer (present-transfer (:other-ledger-entries transaction) accounts)
   :debit (present-debit (:account-ledger-entry transaction))
   :credit (present-credit (:account-ledger-entry transaction))
   :balance (present-balance (:balance transaction))})

(defn present-account-screen [transactions accounts account-id]
  (map #(present-account-screen-line % accounts)
       (as/get-account-screen-data transactions account-id)))
