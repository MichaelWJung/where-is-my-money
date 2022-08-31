(ns money.core.currency
  (:require [clojure.spec.alpha :as s]
            [clojure.string :refer (trim)]))

(defn- is-trimmed? [s]
  (= s (trim s)))

(s/def ::name (s/and string? is-trimmed?))
(s/def ::symbol (s/and string? is-trimmed?))

(s/def ::currency
  (s/keys :req [::name ::symbol]))

