
(ns jeppes.frontend.router
  (:import goog.history.Html5History)
  (:require [goog.events :as events]
            [goog.history.EventType :as EventType]
            [clojure.string :refer [split join]]))


(defn- dispatch! [token on-route-changed]
  (let [path (-> (split token "/") rest vec)]
    (on-route-changed path)))

(def history (Html5History.))

(defn- hook-browser-navigation! [on-route-changed]
  (doto history
   (events/listen
    EventType/NAVIGATE
    (fn [^js event]
      (dispatch! (.-token event) on-route-changed)))
    (.setUseFragment true)
    (.setPathPrefix "#")
    (.setEnabled true)))

(defn install-routes! [on-route-changed]
  (hook-browser-navigation! on-route-changed))

(defn- push! [url]
  (.setToken history (str "/" url)))

(defn link
  ([url text]
   (link {} url text))
  ([props url text]
   (cond
     (vector? url) [:a.link
                    (merge props
                           {:on-click #(push! (join "/" url))})
                    text]
     :else [:a.link (merge props {:href url}) text])))

(join "/" [])