(ns money.core.owner-test
  (:require [clojure.spec.alpha :as s]
            [clojure.test :refer (deftest is)]
            [money.core.owner :as o]))

(deftest owner-spec-test
  (is (s/valid? ::o/owner {::o/name "Michael"}))
  (is (not (s/valid? ::o/owner {::o/name 1})))
  (is (not (s/valid? ::o/owner {}))))
