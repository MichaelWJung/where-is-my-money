(ns money.core.account-test
  (:require [cljs.spec.alpha :as s]
            [cljs.test :refer (deftest is testing)]
            [money.core.account :as a]))

(deftest account-spec-test
  (let [acc {::a/name "Salary"
             ::a/currency-id 0
             ::a/parent-id 0
             ::a/owner-id 0
             ::a/type ::a/income}]
    (testing "Needs to either have both link keys or none"
      (is (s/valid? ::a/account acc))
      (is (s/valid? ::a/account (assoc acc
                                       ::a/linked-account-id 2
                                       ::a/link-type ::a/mirrored)))
      (is (not (s/valid? ::a/account (assoc acc ::a/linked-account-id 2))))
      (is (not (s/valid? ::a/account (assoc acc ::a/link-type ::a/mirrored))))))
  )
