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

(defn- placeholder
  ([length] [placeholder length :p])
  ([length element]
   [element {:class-name "placeholder"}
    [:span (->> (gstring/unescapeEntities "&nbsp;")
                (repeat)
                (take length)
                (join))]]))

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
    (if (and description updated-at)
      [:<>
       [:div [:small description]]
       [:br]
       [:div [:small (format-date updated-at)]]]
      [:<>
       [placeholder 30 :small]
       [:br]
       [placeholder 16 :small]])]])

(defn- blog-preview-small [{name :name description :description updated-at :updated_at}]
  [link ["blog" name]
   [:div.blog-preview-small
    [:p name]
    (if description
      [:small description]
      [placeholder 16 :small])]])

(defn blog-page [{name :post blogs :blogs repos :repos}]
  (let [state (r/atom {})]
    (download-blog-post! name #(swap! state assoc :text %))
    (fn [{blogs :blogs repos :repos}]
      [:div.blog-page
       [:article.blog-post
        (if (@state :text)
          [markdown (@state :text)]
          [blog-placeholder])]
       [:ul
        (for [name blogs]
          ^{:key name} [:li [blog-preview-small (merge {:name name} (get repos name))]])]])))
