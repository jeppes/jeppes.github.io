(ns jeppes.frontend.app
  (:require [reagent.core :as r]
            [reagent.dom :as dom]
            [clojure.core.match :refer [match]]
            [jeppes.frontend.router :refer [install-routes! link]]
            [jeppes.frontend.pages.blog :refer [blog-page]]
            [jeppes.frontend.pages.home :refer [home-page]]
            [jeppes.frontend.pages.notfound :refer [not-found]]
            [jeppes.frontend.data :refer [fetch-json!]]))

(defonce state (r/atom {:route []
                        :repos []
                        :blogs ["oops" "maybe-not-reflections"]}))

(defonce init-state (-> "https://api.github.com/users/jeppes/repos"
                        (fetch-json!)
                        (.then #(zipmap (map :name %) %))
                        (.then #(swap! state assoc :repos %))))

(defn root-page [state]
  (match (@state :route)
    ["blog" post] [blog-page post]
    [] [home-page @state] 
    :else [not-found]))

(defn mount []
  (dom/render [root-page state]
              (js/document.getElementById "root")))

(defn init! [] 
  (mount)
  (install-routes! #(swap! state assoc :route %)))

(defn ^:dev/after-load start []
  (init!))