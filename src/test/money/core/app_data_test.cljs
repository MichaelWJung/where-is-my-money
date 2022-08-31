(ns money.core.app-data-test
  (:require [cljs.spec.alpha :as s]
            [cljs.test :refer (deftest is testing)]
            [money.core.app-data :as d]
            [money.core.account :as a]
            [money.core.currency :as c]
            [money.core.owner :as o]
            [money.core.transaction :as t]
            ))

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
        ::a/type ::a/asset
        ::a/linked-account-id 104
        ::a/link-type ::a/identical}

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
     )

   ::d/currencies
   (sorted-map)

   ::d/owners
   (sorted-map)

   ::d/transactions
   (sorted-map)})

(deftest sorted-map?-test
  (is (d/sorted-map? (sorted-map 0 "val0" 1 "val1")))
  (is (not (d/sorted-map? {0 "val0" 1 "val1"}))))

(deftest specs-test
  (is (s/valid? ::d/data test-data)))
