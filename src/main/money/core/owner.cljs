(ns money.core.owner
  (:require [clojure.spec.alpha :as s]))

(s/def ::name string?)

(s/def ::owner
  (s/keys :req [::name]))
