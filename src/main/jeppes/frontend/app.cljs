(ns jeppes.frontend.app
  (:require #_[reagent.core :as r]
            [reagent.dom :as dom]))

(defn home-page []
  [:<>
   [:h1 "Hello this is me"]
   [:p "This is true"]
   [:p "This is also true"]])

(defn mount []
  (dom/render [home-page]
              (js/document.getElementById "root")))

(defn init! [] (mount))

(defn ^:dev/after-load start []
  (init!))