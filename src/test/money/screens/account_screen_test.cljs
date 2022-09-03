(ns money.screens.account-screen-test
  (:require [clojure.test :refer (deftest is testing)]
            [money.core.ledger-entry :as le]
            [money.core.money :as m]
            [money.core.transaction :as t]
            [money.screens.account-screen :as as]))

(defn- ledger-entry [account-id amount]
  {::le/account-id account-id
   ::le/amount {::m/amount amount
                ::m/currency-id 0}})

(def ^:private transactions
  (sorted-map
    2 {::t/description "Cinema"
       ::t/date 3000000
       ::t/ledger-entries [(ledger-entry 3 2230)
                           (ledger-entry 6 -2230)]}

    5 {::t/description "Bowling"
       ::t/date 1000000
       ::t/ledger-entries [(ledger-entry 11 3300)
                           (ledger-entry 6 -3300)]}

    6 {::t/description "Playing pinball"
       ::t/date 2000000
       ::t/ledger-entries [(ledger-entry 3 1000)
                           (ledger-entry 3 200)
                           (ledger-entry 6 -1200)]}))

(deftest filter-transactions-by-account-test
  (testing "Only returns transactions whose ledger entries contain account id"
    (is (= (as/filter-transactions-by-account transactions 6)
           (seq transactions)))
    (is (= (as/filter-transactions-by-account transactions 11)
           (seq (select-keys transactions [5]))))
    (is (= (as/filter-transactions-by-account transactions 3)
           (seq (select-keys transactions [2 6]))))))

(deftest remove-nth-test
  (is (= (as/remove-nth [1 2 3 4 5] 2) [3 [1 2 4 5]]))
  (is (= (as/remove-nth [1 2 3 4 5] 0) [1 [2 3 4 5]]))
  (is (= (as/remove-nth [1 2 3 4 5] 4) [5 [1 2 3 4]]))
  (is (= (as/remove-nth [1 2 3 4 5] 5) [nil [1 2 3 4 5]])))

(deftest go-over-ledger-entries-test
  (let [e1 (ledger-entry 0 100)
        e2 (ledger-entry 1 200)
        e3 (ledger-entry 2 -300)]
    (is (= (as/go-over-ledger-entries [e1 e2 e3])
           [[e1 [e2 e3]] [e2 [e1 e3]] [e3 [e1 e2]]]))))

(deftest get-ledger-entries-for-account-test
  (let [e1 (ledger-entry 0 100)
        e2 (ledger-entry 1 200)
        e3 (ledger-entry 0 -300)]
    (is (= (as/get-ledger-entries-for-account [e1 e2 e3] 0)
           [[e1 [e2 e3]] [e3 [e1 e2]]]))))

(deftest create-modified-account-screen-transaction-test
  (testing
    (str "Replaces ::t/ledger-entries by :account-ledger-entry and "
         ":other-ledger-entries")
    (is (= (as/create-modified-account-screen-transaction
             (get transactions 2) 3)
           [{::t/description "Cinema"
             ::t/date 3000000
             :account-ledger-entry (ledger-entry 3 2230)
             :other-ledger-entries [(ledger-entry 6 -2230)]}])))
  (testing
    (str "Returns multiple transactions if an account is used multiple times "
         "in ledger entries")
    (is (= (as/create-modified-account-screen-transaction
             (get transactions 6) 3)
           [{::t/description "Playing pinball"
             ::t/date 2000000
             :account-ledger-entry (ledger-entry 3 1000)
             :other-ledger-entries [(ledger-entry 3 200)
                                    (ledger-entry 6 -1200)]}
            {::t/description "Playing pinball"
             ::t/date 2000000
             :account-ledger-entry (ledger-entry 3 200)
             :other-ledger-entries [(ledger-entry 3 1000)
                                    (ledger-entry 6 -1200)]}]))))

(deftest sort-transactions-by-date-test
  (testing "Transactions are sorted by date in ascending order"
    (is (= (as/sort-transactions-by-date (vals transactions))
           (map transactions [5 6 2])))))

(deftest build-account-screen-transactions-test
  (testing
    (str "Collects all modified transactions from originating from incoming "
         "transactions")
    (is (= (as/build-account-screen-transactions transactions 3)
           [{::t/description "Playing pinball"
             ::t/date 2000000
             :account-ledger-entry (ledger-entry 3 1000)
             :other-ledger-entries [(ledger-entry 3 200)
                                    (ledger-entry 6 -1200)]}
            {::t/description "Playing pinball"
             ::t/date 2000000
             :account-ledger-entry (ledger-entry 3 200)
             :other-ledger-entries [(ledger-entry 3 1000)
                                    (ledger-entry 6 -1200)]}
            {::t/description "Cinema"
             ::t/date 3000000
             :account-ledger-entry (ledger-entry 3 2230)
             :other-ledger-entries [(ledger-entry 6 -2230)]}]))))
