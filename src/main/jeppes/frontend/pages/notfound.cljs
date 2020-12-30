(ns jeppes.frontend.pages.notfound
  (:require [jeppes.frontend.router :refer [link]]))

(defn not-found []
  [:<>
   [:p "404 - Page not found"]
   [:p "Take me " [link [""] "home"] "."]])