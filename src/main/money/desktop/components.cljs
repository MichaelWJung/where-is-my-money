(ns money.desktop.components
  (:require [reagent.core :as r]
            ["/components/ledger$default" :as Ledger]
            ["/components/ledger_input$default" :as LedgerInput]))

(def ledger (r/adapt-react-class Ledger))
(def ledger-input (r/adapt-react-class LedgerInput))
