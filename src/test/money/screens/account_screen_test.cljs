(ns money.screens.account-screen-test
  (:require [clojure.test :refer (deftest is testing)]
            [money.core.ledger-entry :as le]
            [money.core.money :as m]
            [money.core.transaction :as t]
            [money.screens.account-screen :as as]))

(deftest filter-transactions-by-account-test
  (let [transactions
        (sorted-map
          2 {::t/description "Cinema"
             ::t/date 1000000
             ::t/ledger-entries [{::le/account-id 3
                                  ::le/amount {::m/amount 2230
                                               ::m/currency-id 0}}
                                 {::le/account-id 6
                                  ::le/amount {::m/amount -2230
                                               ::m/currency-id 0}}]}

          5 {::t/description "Bowling"
             ::t/date 2000000
             ::t/ledger-entries [{::le/account-id 11
                                  ::le/amount {::m/amount 3300
                                               ::m/currency-id 0}}
                                 {::le/account-id 6
                                  ::le/amount {::m/amount -3300
                                               ::m/currency-id 0}}]}

          6 {::t/description "Playing pinball"
             ::t/date 3000000
             ::t/ledger-entries [{::le/account-id 3
                                  ::le/amount {::m/amount 1200
                                               ::m/currency-id 0}}
                                 {::le/account-id 6
                                  ::le/amount {::m/amount -1200
                                               ::m/currency-id 0}}]})]

    (testing "Only returns transactions whose ledger entries contain account id"
      (is (= (as/filter-transactions-by-account transactions 6) transactions))
      (is (= (as/filter-transactions-by-account transactions 11)
             (select-keys transactions [5])))
      (is (= (as/filter-transactions-by-account transactions 3)
             (select-keys transactions [2 6]))))))
