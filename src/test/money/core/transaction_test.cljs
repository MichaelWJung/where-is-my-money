(ns money.core.transaction-test
  (:require [cljs.spec.alpha :as s]
            [cljs.test :refer (deftest is testing)]
            [money.core.account :as a]
            [money.core.ledger-entry :as le]
            [money.core.money :as m]
            [money.core.transaction :as t]))

(deftest transaction-validity-test
  (let [usd10 {::le/account-id 0
               ::le/amount {::m/amount 10 ::m/currency-id 0}}
        usd-10 {::le/account-id 1
                ::le/amount {::m/amount -10 ::m/currency-id 0}}
        usd-10-other-owner {::le/account-id 100
                            ::le/amount {::m/amount -10 ::m/currency-id 0}}
        usd0 {::le/account-id 0
              ::le/amount {::m/amount 0 ::m/currency-id 0}}
        chf10 {::le/account-id 10
               ::le/amount {::m/amount 10 ::m/currency-id 1}}
        chf0 {::le/account-id 10
              ::le/amount {::m/amount 0 ::m/currency-id 1}}
        chf-10 {::le/account-id 10
                ::le/amount {::m/amount -10 ::m/currency-id 1}}

        accounts (sorted-map
                   0 {::a/name "Cash USD"
                      ::a/currency-id 0
                      ::a/parent-id nil
                      ::a/owner-id 0
                      ::a/type ::a/asset}
                   1 {::a/name "Income tax"
                      ::a/currency-id 0
                      ::a/parent-id nil
                      ::a/owner-id 0
                      ::a/type ::a/expense}
                   2 {::a/name "Shared Checkings Account"
                      ::a/currency-id 0
                      ::a/parent-id nil
                      ::a/owner-id 0
                      ::a/type ::a/asset
                      ::a/linked-account-id 102
                      ::a/link-type ::a/identical}
                   10 {::a/name "Cash CHF"
                       ::a/currency-id 1
                       ::a/parent-id nil
                       ::a/owner-id 0
                       ::a/type ::a/asset}
                   100 {::a/name "Cash USD"
                        ::a/currency-id 0
                        ::a/parent-id nil
                        ::a/owner-id 1
                        ::a/type ::a/asset}
                   102 {::a/name "Shared Checkings Account"
                        ::a/currency-id 0
                        ::a/parent-id nil
                        ::a/owner-id 1
                        ::a/type ::a/asset
                        ::a/linked-account-id 2
                        ::a/link-type ::a/identical})

        transaction
        {::t/id 0
         ::t/description "Groceries"}

        make-trans
        (fn [entries] (assoc transaction ::le/ledger-entries entries))]

    (testing "Referenced accounts need to exist"
      (is (not (t/transaction-valid? (make-trans [usd10 usd-10])
                                     (dissoc accounts 0)))))

    (testing "Single-currency transaction needs to sum up to 0 to be valid"
      (is (t/transaction-valid? (make-trans [usd10 usd-10]) accounts))
      (is (t/transaction-valid? (make-trans []) accounts))
      (is (t/transaction-valid? (make-trans [usd0]) accounts))
      (is (not (t/transaction-valid? (make-trans [usd10]) accounts)))
      (is (not (t/transaction-valid? (make-trans [usd10 usd10]) accounts))))

    (testing
      (str "In a multi-currency transaction each currency needs to sum up to 0 "
           "individually")
      (is (t/transaction-valid? (make-trans [usd0 chf0]) accounts))
      (is (t/transaction-valid?
            (make-trans [usd10 usd-10 chf10 chf-10]) accounts))
      (is (not (t/transaction-valid? (make-trans [usd-10 chf10]) accounts))))

    (testing
      (str "In a multi-owner transaction the entries of each owner need to sum "
           "up to 0 individually per currency")
      (is (not (t/transaction-valid?
                 (make-trans [usd10 usd-10-other-owner]) accounts))))

    (testing
      (str "If an account has an identical link account, the ledger entries "
           "need to be repeated for that account")
      (let [with-link-usd10 {::le/account-id 2
                          ::le/amount {::m/amount 10 ::m/currency-id 0}}
            linked-usd10 {::le/account-id 102
                        ::le/amount {::m/amount 10 ::m/currency-id 0}}
            linked-usd0 {::le/account-id 102
                       ::le/amount {::m/amount 0 ::m/currency-id 0}}]
        (is (t/transaction-valid?
              (make-trans [usd-10 usd-10-other-owner
                           with-link-usd10 linked-usd10])
              accounts))
        (is (not (t/transaction-valid?
                   (make-trans [usd-10 with-link-usd10]) accounts)))
        (is (not (t/transaction-valid?
                   (make-trans [usd-10 with-link-usd10 linked-usd0]) accounts)))
        (is (not (t/transaction-valid?
                   (make-trans [usd-10 usd-10 usd-10-other-owner
                                with-link-usd10 linked-usd10 with-link-usd10])
                   accounts)))))

    (testing "Transaction needs to satisfy specs"
      (is (not (t/transaction-valid?
                 (dissoc (make-trans [usd10 usd-10]) ::t/description)
                 accounts))))))
