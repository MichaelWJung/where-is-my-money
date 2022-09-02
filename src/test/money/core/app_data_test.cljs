(ns money.core.app-data-test
  (:require [cljs.spec.alpha :as s]
            [cljs.test :refer (deftest is testing)]
            [money.core.app-data :as d]
            [money.core.account :as a]
            [money.core.currency :as c]
            [money.core.ledger-entry :as le]
            [money.core.money :as m]
            [money.core.owner :as o]
            [money.core.transaction :as t]
            ))

(def ^:private seconds-in-day (* 60 60 24))

(defn- date [days-increment]
  (+ 1661983200 (* days-increment seconds-in-day)))

(def test-data
  {::d/accounts
   (sorted-map
     0 {::a/name "Equity"
        ::a/currency-id 0
        ::a/parent-id nil
        ::a/owner-id 0
        ::a/type ::a/equity}

     1 {::a/name "Opening Balances"
        ::a/currency-id 0
        ::a/parent-id 0
        ::a/owner-id 0
        ::a/type ::a/equity}

     2 {::a/name "Assets"
        ::a/currency-id 0
        ::a/parent-id nil
        ::a/owner-id 0
        ::a/type ::a/asset}

     3 {::a/name "Cash"
        ::a/currency-id 0
        ::a/parent-id 2
        ::a/owner-id 0
        ::a/type ::a/asset}

     4 {::a/name "Checking Account"
        ::a/currency-id 0
        ::a/parent-id 2
        ::a/owner-id 0
        ::a/type ::a/asset}

     5 {::a/name "Expenses"
        ::a/currency-id 0
        ::a/parent-id nil
        ::a/owner-id 0
        ::a/type ::a/expense}

     6 {::a/name "Entertainment"
        ::a/currency-id 0
        ::a/parent-id 4
        ::a/owner-id 0
        ::a/type ::a/expense}

     7 {::a/name "Utilities"
        ::a/currency-id 0
        ::a/parent-id 4
        ::a/owner-id 0
        ::a/type ::a/expense}

     8 {::a/name "Income"
        ::a/currency-id 0
        ::a/parent-id nil
        ::a/owner-id 0
        ::a/type ::a/income}

     9 {::a/name "Salary"
        ::a/currency-id 0
        ::a/parent-id 8
        ::a/owner-id 0
        ::a/type ::a/income}

     10 {::a/name "Liabilities"
         ::a/currency-id 0
         ::a/parent-id nil
         ::a/owner-id 0
         ::a/type ::a/liability}

     11 {::a/name "Credit Card"
         ::a/currency-id 0
         ::a/parent-id 10
         ::a/owner-id 0
         ::a/type ::a/liability}

     12 {::a/name "Groceries"
         ::a/currency-id 0
         ::a/parent-id 4
         ::a/owner-id 0
         ::a/type ::a/expense}

     13 {::a/name "Shared Checking Account"
         ::a/currency-id 0
         ::a/parent-id 2
         ::a/owner-id 0
         ::a/type ::a/asset
         ::a/linked-account-id 104
         ::a/link-type ::a/identical})

   ::d/currencies
   (sorted-map
     0 {::c/name "CHF"
        ::c/symbol "SFr"}
     1 {::c/name "USD"
        ::c/symbol "$"}
     2 {::c/name "BTC"
        ::c/symbol "₿"})

   ::d/owners
   (sorted-map
     0 {::o/name "Michael"}
     1 {::o/name "Anne"})

   ::d/transactions
   (sorted-map
     0 {::t/description "Opening balance"
        ::t/date (date 0)
        ::t/ledger-entries [{::le/account-id 1
                             ::le/amount {::m/amount 10000
                                          ::m/currency-id 0}}
                            {::le/account-id 7
                             ::le/amount {::m/amount -10000
                                          ::m/currency-id 0}}]}

     1 {::t/description "Opening balance"
        ::t/date (date 0)
        ::t/ledger-entries [{::le/account-id 1
                             ::le/amount {::m/amount 1000000
                                          ::m/currency-id 0}}
                            {::le/account-id 4
                             ::le/amount {::m/amount -1000000
                                          ::m/currency-id 0}}]}

     2 {::t/description "Cinema"
        ::t/date (date 1)
        ::t/ledger-entries [{::le/account-id 3
                             ::le/amount {::m/amount 2230
                                          ::m/currency-id 0}}
                            {::le/account-id 6
                             ::le/amount {::m/amount -2230
                                          ::m/currency-id 0}}]}

     3 {::t/description "Electricity bill"
        ::t/date (date 2)
        ::t/ledger-entries [{::le/account-id 4
                             ::le/amount {::m/amount 17000
                                          ::m/currency-id 0}}
                            {::le/account-id 7
                             ::le/amount {::m/amount -17000
                                          ::m/currency-id 0}}]}

     4 {::t/description "Water bill"
        ::t/date (date 7)
        ::t/ledger-entries [{::le/account-id 4
                             ::le/amount {::m/amount 21723
                                          ::m/currency-id 0}}
                            {::le/account-id 7
                             ::le/amount {::m/amount -21723
                                          ::m/currency-id 0}}]}

     5 {::t/description "Bowling"
        ::t/date (date 4)
        ::t/ledger-entries [{::le/account-id 11
                             ::le/amount {::m/amount 3300
                                          ::m/currency-id 0}}
                            {::le/account-id 6
                             ::le/amount {::m/amount -3300
                                          ::m/currency-id 0}}]}

     6 {::t/description "Playing pinball"
        ::t/date (date 11)
        ::t/ledger-entries [{::le/account-id 3
                             ::le/amount {::m/amount 1200
                                          ::m/currency-id 0}}
                            {::le/account-id 6
                             ::le/amount {::m/amount -1200
                                          ::m/currency-id 0}}]}

     7 {::t/description "Video streaming service"
        ::t/date (date 13)
        ::t/ledger-entries [{::le/account-id 11
                             ::le/amount {::m/amount 1599
                                          ::m/currency-id 0}}
                            {::le/account-id 6
                             ::le/amount {::m/amount -1599
                                          ::m/currency-id 0}}]}

     8 {::t/description "Grocery shopping"
        ::t/date (date 17)
        ::t/ledger-entries [{::le/account-id 11
                             ::le/amount {::m/amount 13275
                                          ::m/currency-id 0}}
                            {::le/account-id 6
                             ::le/amount {::m/amount -13275
                                          ::m/currency-id 0}}]}

     9 {::t/description "Cash withdrawal"
        ::t/date (date 22)
        ::t/ledger-entries [{::le/account-id 3
                             ::le/amount {::m/amount -20000
                                          ::m/currency-id 0}}
                            {::le/account-id 4
                             ::le/amount {::m/amount 20000
                                          ::m/currency-id 0}}]}

     10 {::t/description "Salary"
         ::t/date (date 28)
         ::t/ledger-entries [{::le/account-id 4
                              ::le/amount {::m/amount -650000
                                           ::m/currency-id 0}}
                             {::le/account-id 9
                              ::le/amount {::m/amount 650000
                                           ::m/currency-id 0}}]}

     11 {::t/description "Pay back credit card debts"
         ::t/date (date 30)
         ::t/ledger-entries [{::le/account-id 4
                              ::le/amount {::m/amount 18174
                                           ::m/currency-id 0}}
                             {::le/account-id 11
                              ::le/amount {::m/amount -18174
                                           ::m/currency-id 0}}]})})

(deftest sorted-map?-test
  (is (d/sorted-map? (sorted-map 0 "val0" 1 "val1")))
  (is (not (d/sorted-map? {0 "val0" 1 "val1"}))))

(deftest specs-test
  (is (s/valid? ::d/data test-data)))

(deftest test-data-valid?-test
  (is (every? #(t/transaction-valid? % (::d/accounts test-data))
              (vals (::d/transactions test-data)))))
