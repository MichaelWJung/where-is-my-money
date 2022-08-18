(ns money.desktop.components
  (:require [reagent.core :as r]
            ["/components/ledger$default" :as Ledger]))

(def ledger (r/adapt-react-class Ledger))
