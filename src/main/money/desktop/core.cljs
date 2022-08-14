(ns money.desktop.core
  (:require [reagent.dom]
            [re-frame.core :as rf]))

(defn ui
  []
  [:div
   [:h1 "Hello world!"]])

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

