(ns money.desktop.presenters.account-screen-presenters-test
  (:require [clojure.test :refer (deftest is testing)]
            [money.core.account :as a]
            [money.core.money :as m]
            [money.core.ledger-entry :as le]
            [money.desktop.presenters.account-screen-presenters :as asp]))

(def ^:private accounts
  (sorted-map
    0 {::a/name "Opening Balances"
       ::a/type ::a/equity}
    1 {::a/name "Cash"
       ::a/type ::a/asset}
    2 {::a/name "Checking Account"
       ::a/type ::a/asset}
    3 {::a/name "Entertainment"
       ::a/type ::a/expense}
    4 {::a/name "Utilities"
       ::a/type ::a/expense}
    5 {::a/name "Salary"
       ::a/type ::a/income}
    6 {::a/name "Credit Card"
       ::a/type ::a/liability}))

(deftest present-date-test
  (testing "converted to string"
    (is (= (asp/present-date 12345) "12345"))))

(deftest present-description-test
  (testing "Is presented unchanged"
    (is (= (asp/present-description "abcd") "abcd"))))

(deftest present-transfer-test
  (testing "Exactly one other ledger entry: name of respective account"
    (is (= (asp/present-transfer [{::le/account-id 2}] accounts)
           "Checking Account"))))

(deftest present-debit-test
  (testing "Amount is < 0: value as string"
    (is (= (asp/present-debit {::le/amount {::m/amount -12345}})
           "123.45")))
  (testing "Amount is >= 0: empty string"
    (is (= (asp/present-debit {::le/amount {::m/amount 12345}})
           ""))
    (is (= (asp/present-debit {::le/amount {::m/amount 0}})
           ""))))

(deftest present-credit-test
  (testing "Amount is > 0: value as string"
    (is (= (asp/present-credit {::le/amount {::m/amount 12345}})
           "123.45")))
  (testing "Amount is <= 0: empty string"
    (is (= (asp/present-credit {::le/amount {::m/amount -12345}})
           ""))
    (is (= (asp/present-credit {::le/amount {::m/amount 0}})
           ""))))

(deftest present-balance-test
  (testing "Amount as string"
    (is (= (asp/present-balance {::m/amount 12345}) "123.45"))))
