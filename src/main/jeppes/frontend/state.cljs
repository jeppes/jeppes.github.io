(ns jeppes.frontend.state
  (:require
   [reagent.core :as r]
   [clojure.string :refer [join]]
   [clojure.core.match :refer [match]]
   [jeppes.frontend.data :refer [fetch-text! fetch-json!]]))

(def initial-state {:route []
                    :repos {}
                    :markdown {}
                    :blogs ["intentional-errors" "make-smaller-changes" "evolvable-apis"
                            "maybe-not-reflections" "oops" "funktioner"]})

(defonce state (r/atom initial-state))

(defn- log! [& msg]
  (print "[State]" (join " " msg)))

(defn- parse-repos [repos]
  (as-> repos $
    (filter #(some #{(:name %)} (@state :blogs)) $)
    (map #(select-keys % [:id :name :description :created_at]) $)
    (zipmap (map :name $) $)))

(defn fetch-repos! []
  (log! "Fetching Repositories from Github")
  (->  "https://api.github.com/users/jeppes/repos"
       (fetch-json!)
       (.then parse-repos)
       (.then #(swap! state assoc :repos %))))

(defn- download-blog-post! [name]
  (let [existing-post (-> @state :markdown (get name))]
    (if existing-post
      (log! "Already loaded:" name)
      (do (log! "Downloading:" name)
          (-> (str "https://raw.githubusercontent.com/jeppes/" name "/master/README.md")
              (fetch-text!)
              (.then #(swap! state assoc-in [:markdown name] %)))))))

(defn- scroll-to-top! []
  (.scrollTo js/window (js-obj "top" 0)))

(defn navigate-to! [route]
  (when (not= route (@state :route))
    (swap! state assoc :route route)
    ;; Download blog posts as a side-effect of navigating to them)
    (match route
      ["blog" post] (download-blog-post! post)
      :else nil)
    (scroll-to-top!)
    (log! "Navigated to" route)))
