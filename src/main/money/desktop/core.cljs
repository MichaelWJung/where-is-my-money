(ns money.desktop.core
  (:require [money.desktop.components :as c]
            [re-frame.core :as rf]
            [reagent.dom]))

(rf/reg-event-db
 :initialize
 (fn [_ _]
   {:color "red"}))

(rf/reg-event-db
 :change-color
 (fn [db [_ new-color-value]]
   (assoc db :color new-color-value)))

(rf/reg-sub
 :color
 (fn [db _]
   (:color db)))

(defn ui
  []
  [:<>
   [c/ledger-input {:value @(rf/subscribe [:color])
                    :on-change #(rf/dispatch-sync [:change-color %])}]
   [:p {:style {:color @(rf/subscribe [:color])}} "I haz color"]])

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

