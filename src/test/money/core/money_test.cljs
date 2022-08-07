(ns money.core.money-test
  (:require [cljs.spec.alpha :as s]
            [cljs.test :refer (deftest is testing)]
            [clojure.core :refer [ExceptionInfo]]
            [money.core.money :as m]))

(deftest money-spec-test
  (is (s/valid? ::m/money {::m/amount 123 ::m/currency-id 0}))
  (is (not (s/valid? ::m/money {::m/amount 123.4 ::m/currency-id 0})))
  (is (not (s/valid? ::m/money {::m/amount 123 ::m/currency-id "USD"}))))

(deftest add-test
  (let [usd1 {::m/amount 1 ::m/currency-id 0}
        usd2 {::m/amount 2 ::m/currency-id 0}
        usd3 {::m/amount 3 ::m/currency-id 0}
        chf1 {::m/amount 1 ::m/currency-id 1}
        chf2 {::m/amount 2 ::m/currency-id 1}]

    (testing "Can add money of same currency"
      (is (= (m/add-money usd1 usd2) usd3))
      (is (= (m/add-money usd1 usd1) usd2))
      (is (= (m/add-money chf1 chf1) chf2)))

    (testing "Adding money of different currencies fails"
      (is (thrown? ExceptionInfo (m/add-money usd1 chf1))))))

