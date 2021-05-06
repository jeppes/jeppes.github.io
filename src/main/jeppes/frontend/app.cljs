(ns jeppes.frontend.app
  (:require
   [reagent.dom :as dom]
   [clojure.core.match :refer [match]]
   [jeppes.frontend.state :refer [state fetch-repos! navigate-to!]]
   [jeppes.frontend.router :refer [install-routes!]]
   [jeppes.frontend.pages.blog :refer [blog-page]]
   [jeppes.frontend.pages.home :refer [home-page]]
   [jeppes.frontend.pages.notfound :refer [not-found]]))

(defn root-page [state]
  (match (@state :route)
    ["blog" name]  [blog-page (merge {:name name} @state)]
    [] [home-page @state]
    :else [not-found]))

(defn mount []
  (dom/render [root-page state]
              (js/document.getElementById "root")))

(defn ^:dev/after-load start []
  (mount)
  (install-routes! #(navigate-to! %)))

(defn init! []
  (fetch-repos!)
  (start))