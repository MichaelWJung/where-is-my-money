(ns money.desktop.components
  (:require [reagent.core :as r]
            ["/components/ledger" :as l]))

(def ledger (r/adapt-react-class (.-default l)))
