(ns money.subs
  (:require [re-frame.core :as rf]
            [money.db :as db]
            [money.core.account :as a]
            [money.core.presenters.account-presenter :as ap]
            [money.core.presenters.transaction-presenter :as tp]
            [money.core.screens.account :as sa]
            [money.core.screens.transaction :as st]
            [money.core.transaction :as t]))

(rf/reg-sub
  :accounts
  (fn [db _]
    (get-in db [::db/data ::a/accounts])))

(rf/reg-sub
  :transactions
  (fn [db _]
    (get-in db [::db/data ::t/transactions])))

(rf/reg-sub
  :current-account
  (fn [db _]
    ; (prn "db " db)
    (get-in db [::db/screen-states ::sa/account-screen-state ::sa/account-id])))

(rf/reg-sub
  :transaction-screen-state
  (fn [db _]
    (get-in db [::db/screen-states ::st/transaction-screen-state])))

(rf/reg-sub
  :current-account-transactions
  :<- [:transactions]
  :<- [:current-account]
  (fn [[transactions current-account] _]
    (t/get-account-transactions transactions current-account)))

(rf/reg-sub
  :account-names
  :<- [:current-account]
  :<- [:accounts]
  (fn [[current-account accounts] _]
    ; (prn "accs" accounts)
    ; (prn "current" current-account)
    (ap/present-account-list accounts current-account)))

(rf/reg-sub
  :current-account-reduced-transactions
  :<- [:current-account]
  :<- [:current-account-transactions]
  :<- [:accounts]
  (fn [[current-account transactions accounts] _]
    (ap/reduce-transactions transactions accounts current-account)))

(rf/reg-sub
  :account-overview
  :<- [:current-account-reduced-transactions]
  :<- [:accounts]
  (fn [[reduced-transactions accounts] _]
    ; (prn "rt a" reduced-transactions accounts)
    (ap/present-transactions reduced-transactions accounts "en-US")))

(rf/reg-sub
  :current-screen
  (fn [db _]
    (::db/navigation db)))

(rf/reg-sub
  :transaction-screen
  :<- [:transaction-screen-state]
  :<- [:accounts]
  (fn [[screen-state accounts] _]
    (if (nil? screen-state)
      nil
      (tp/present-transaction-screen screen-state accounts))))

(rf/reg-sub
  :transaction-screen-description
  :<- [:transaction-screen]
  (fn [screen _]
    (::tp/description screen)))

(rf/reg-sub
  :transaction-screen-amount
  :<- [:transaction-screen]
  (fn [screen _]
    (::tp/amount screen)))

(rf/reg-sub
  :transaction-screen-date
  :<- [:transaction-screen]
  (fn [screen _]
    (::tp/date screen)))

(rf/reg-sub
  :transaction-screen-ok-button-text
  :<- [:transaction-screen]
  (fn [screen _]
    (::tp/ok-button-text screen)))

(rf/reg-sub
  :transaction-screen-selected-account
  :<- [:transaction-screen]
  (fn [screen _]
    (::tp/selected-account screen)))

(rf/reg-sub
  :transaction-screen-accounts
  :<- [:transaction-screen]
  (fn [screen _]
    (::tp/accounts screen)))
