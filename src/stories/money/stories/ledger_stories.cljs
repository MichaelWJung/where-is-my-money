(ns money.stories.ledger-stories
  (:require [money.desktop.components.ledger :refer [ledger]]
            [reagent.core :as r]))

(def ^:export default
  #js {:title "Ledger"
       :component (r/reactify-component ledger)})

(defn ^:export AccountOverview []
  (r/as-element [ledger "account"]))

