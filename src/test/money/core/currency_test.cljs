(ns money.core.currency-test
  (:require [clojure.spec.alpha :as s]
            [clojure.test :refer (deftest is testing)]
            [money.core.currency :as c]))

(deftest currency-spec-test
  (is (s/valid? ::c/currency {::c/name "USD" ::c/symbol "$"}))
  (is (s/valid? ::c/currency {::c/name "CHF" ::c/symbol "SFr"}))
  (is (not (s/valid? ::c/currency {::c/name "USD " ::c/symbol "$"})))
  (is (not (s/valid? ::c/currency {::c/name "USD" ::c/symbol "$ "}))))
