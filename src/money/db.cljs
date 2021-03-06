(ns money.db
  (:require [cljs.spec.alpha :as s]
            [money.core.account :as a]
            [money.core.transaction :as t]
            [money.core.screens.account :as sa]
            [money.core.screens.transaction :as st]))

(s/def ::currencies (constantly true))
(s/def ::data (s/keys :req [::a/accounts ::t/transactions ::currencies]))

(s/def ::screen-states (s/keys :req [::sa/account-screen-state
                                     ::st/transaction-screen-state]))

(s/def ::navigation #{::transaction-screen ::home-screen ::account-overview})

(s/def ::db-ready? boolean?)
(s/def ::ui-ready? boolean?)
(s/def ::startup (s/keys :req [::db-ready? ::ui-ready?]))

(s/def ::db (s/keys :req [::data ::navigation ::startup]))

(defn- index-to-amount [i]
  (mod (* i 123) 129))

(defn- get-date [i]
  (+ 1578830400000 (* i 86400000)))

(defn- generate-splits
  [[account1 account2] amount]
  [{::t/description ""
    ::t/amount amount
    ::t/account account1}
   {::t/description ""
    ::t/amount (- amount)
    ::t/account account2}])

(defn- generate-transaction [i]
  [i {::t/id i
      ::t/description (str "Transaktion " i)
      ::t/splits (generate-splits [0 (+ 1 (mod i 5))] (index-to-amount i))
      ::t/date (get-date i)}])

(defn generate-transactions []
  (into {} (map generate-transaction (range 30))))

(defn- get-account-name [i]
  (case i
    0 "Bargeld"
    1 "Girokonto"
    2 "Lebensmittel"
    3 "Kraftstoff"
    4 "Versicherungen"
    5 "Forderungen"
    (str "Konto " i)))

(defn- generate-account [i]
  [i {::a/name (get-account-name i)
      ::a/currency 0
      ::a/parent nil
      ::a/type :normal}])

(defn generate-accounts []
  (into {} (map generate-account (range 6))))

(def default-db
  {::data {::t/transactions {}
          ::a/accounts (conj {} (generate-account 0))
          ::currencies []}
   ::screen-states {::sa/account-screen-state
                    {::sa/account-id 0}
                    ::st/transaction-screen-state
                    {::st/description ""
                     ::st/date 1
                     ::st/account-id 0
                     ::st/amount 0.0
                     ::st/id 0
                     ::st/new? true}}
   ::navigation ::home-screen
   ::startup {::db-ready? false ::ui-ready? false}
   :highest-ids {:transaction 1}})

(def generated-db
  {::data {::t/transactions (generate-transactions)
          ::a/accounts (generate-accounts)
          ::currencies []}
   :highest-ids {:transaction 1}})
