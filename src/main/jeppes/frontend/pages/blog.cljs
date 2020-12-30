(ns jeppes.frontend.pages.blog
  (:require
   [reagent.core :as r]
   [goog.string :as gstring]
   [clojure.string :refer [join]]
   [jeppes.frontend.markdown :refer [markdown]]
   [jeppes.frontend.data :refer [fetch-text!]]
   [jeppes.frontend.router :refer [link]]))

(defn- download-blog-post! [name on-complete]
  (-> (str "https://raw.githubusercontent.com/jeppes/" name "/master/README.md")
      (fetch-text!)
      (.then on-complete)))

(defn- format-date [date-string]
  (let [options #js {:year "numeric" :month "long" :day "numeric"}]
    (-> date-string
        (js/Date.)
        (.toLocaleDateString "en-US" options))))

(defn- placeholder [length]
  [:p [:span.placeholder (->> (gstring/unescapeEntities "&nbsp;")
                              (repeat)
                              (take length)
                              (join))]])
(defn- blog-placeholder []
  [:<>
   [placeholder 16]
   [placeholder 37]
   [placeholder 36]
   [placeholder 38]
   [:br]
   [placeholder 16]
   [placeholder 38]
   [placeholder 35]
   [placeholder 36]
   [:br]
   [placeholder 16]
   [placeholder 37]
   [placeholder 36]
   [placeholder 38]])

(defn blog-preview [{name :name description :description updated-at :updated_at}]
  [link ["blog" name]
   [:article.blog-preview
    [:h2 name]
    (if description
      [:p description]
      [placeholder 30])
    (if updated-at
      [:p "Updated " (format-date updated-at)]
      [placeholder 16])]])

(defn blog-page [name]
  (let [state (r/atom {})]
    (download-blog-post! name #(swap! state assoc :text %))
    (fn [_]
      [:article.blog
       (if (@state :text)
         [markdown (@state :text)]
         [blog-placeholder])])))
