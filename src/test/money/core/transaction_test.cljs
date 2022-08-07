(ns money.core.transaction-test
  (:require [cljs.spec.alpha :as s]
            [cljs.test :refer (deftest is testing)]
            [money.core.ledger-entry :as le]
            [money.core.money :as m]
            [money.core.transaction :as t]))

(deftest transaction-validity-test
  (let [usd10 {::le/account-id 0
               ::le/amount {::m/amount 10 ::m/currency-id 0}
               ::le/owner-id 0}
        usd-10 {::le/account-id 0
                ::le/amount {::m/amount -10 ::m/currency-id 0}
                ::le/owner-id 0}
        usd0 {::le/account-id 0
              ::le/amount {::m/amount 0 ::m/currency-id 0}
              ::le/owner-id 0}
        chf10 {::le/account-id 10
               ::le/amount {::m/amount 10 ::m/currency-id 1}
               ::le/owner-id 0}
        chf0 {::le/account-id 10
              ::le/amount {::m/amount 0 ::m/currency-id 1}
              ::le/owner-id 0}
        chf-10 {::le/account-id 10
                ::le/amount {::m/amount -10 ::m/currency-id 1}
                ::le/owner-id 0}

        transaction
        {::t/id 0
         ::t/description "Groceries"}

        make-trans
        (fn [entries] (assoc transaction ::le/ledger-entries entries))]

    (testing "Single-currency transaction needs to sum up to 0 to be valid"
      (is (t/transaction-valid? (make-trans [usd10 usd-10])))
      (is (t/transaction-valid? (make-trans [])))
      (is (t/transaction-valid? (make-trans [usd0])))
      (is (not (t/transaction-valid? (make-trans [usd10]))))
      (is (not (t/transaction-valid? (make-trans [usd10 usd10])))))

    (testing
      (str "In a multi-currency transaction each currency needs to sum up to 0 "
           "individually")
      (is (t/transaction-valid? (make-trans [usd0 chf0])))
      (is (t/transaction-valid? (make-trans [usd10 usd-10 chf10 chf-10])))
      (is (not (t/transaction-valid? (make-trans [usd-10 chf10])))))

    (testing "Transaction needs to satisfy specs"
      (is (not (t/transaction-valid?
                 (dissoc (make-trans [usd10 usd-10]) ::t/description)))))))
