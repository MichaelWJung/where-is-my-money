(ns money.core.account-test
  (:require [cljs.spec.alpha :as s]
            [cljs.test :refer (deftest is testing)]
            [money.core.account :as a]))

(deftest account-spec-test
  (let [acc1 {::a/name "Salary"
              ::a/currency-id 0
              ::a/parent-id 0
              ::a/owner-id 0
              ::a/type ::a/income}]

    (testing "Account needs to either have both link keys or none"
      (is (s/valid? ::a/account acc1))
      (is (s/valid? ::a/account (assoc acc1
                                       ::a/linked-account-id 2
                                       ::a/link-type ::a/mirrored)))
      (is (not (s/valid? ::a/account (assoc acc1 ::a/linked-account-id 2))))
      (is (not (s/valid? ::a/account (assoc acc1 ::a/link-type ::a/mirrored)))))

    (testing "Accounts need to be in a sorted map"
      (is (s/valid? ::a/accounts (sorted-map 0 acc1 1 acc1)))
      (is (not (s/valid? ::a/accounts {0 acc1 1 acc1}))))
    ))
