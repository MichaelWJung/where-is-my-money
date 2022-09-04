(ns money.desktop.core
  (:require [money.desktop.components :as c]
            [money.core.app-data :as d]
            [money.core.app-data-test :refer (test-data)]
            [money.screens.account-screen :as as]
            [money.desktop.presenters.account-screen-presenters :as asp]
            [re-frame.core :as rf]
            [reagent.dom]))

(rf/reg-event-db
 :initialize
 (fn [_ _]
   {:app-data test-data
    :account-id 4}))

(rf/reg-event-db
 :change-account
 (fn [db [_ new-account-id]]
   (assoc db :account-id new-account-id)))

(rf/reg-sub
  :account-id
  (fn [db _]
    (:account-id db)))

(rf/reg-sub
 :app-data
 (fn [db _]
   (:app-data db)))

(rf/reg-sub
  :accounts
  :<- [:app-data]
  (fn [app-data _]
    (::d/accounts app-data)))

(rf/reg-sub
  :transactions
  :<- [:app-data]
  (fn [app-data _]
    (::d/transactions app-data)))

(rf/reg-sub
  :account-transactions
  :<- [:transactions]
  (fn [transactions [_ account-id]]
    (as/filter-transactions-by-account transactions @account-id)))

(rf/reg-sub
  :presented-account-screen-data
  (fn [_ _]
    (let [account-id (rf/subscribe [:account-id])]
      [(rf/subscribe [:account-transactions account-id])
       (rf/subscribe [:accounts])
       account-id]))
  (fn [[transactions accounts account-id] _]
    (asp/present-account-screen transactions accounts account-id)))

(defn ui []
  (let [data (rf/subscribe [:presented-account-screen-data])]
    [:<>
     [c/ledger {:data @data}]]))

(defn render
  []
  (reagent.dom/render [ui]
                      (js/document.getElementById "app")))

(defn ^:dev/after-load clear-cache-and-render!
  []
  ;; The `:dev/after-load` metadata causes this function to be called
  ;; after shadow-cljs hot-reloads code. We force a UI update by clearing
  ;; the Reframe subscription cache.
  (rf/clear-subscription-cache!)
  (render))

(defn run
  []
  (rf/dispatch-sync [:initialize]) ;; put a value into application state
  (render))                         ;; mount the application's ui into '<div id="app" />'

