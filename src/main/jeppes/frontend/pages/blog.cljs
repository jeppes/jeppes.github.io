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

(defn blog-preview [{name :name description :description created-at :created_at}]
  [link ["blog" name]
   [:article.blog-preview
    [:h2 name]
    (if (and description created-at)
      [:<>
       [:p description]
       [:p (format-date created-at)]]
      [:<>
       [placeholder 30 :small]
       [:br]
       [placeholder 16 :small]])]])

(defn- blog-preview-small [{name :name description :description current-name :current-name created-at :created_at}]
  [link ["blog" name]
   [:div.blog-preview-small
    {:class (when (= name current-name) "selected")}
    [:h3 name]
    (if (and description created-at)
      [:<>
       [:p description]
       [:p (format-date created-at)]]
      [:<>
       [placeholder 39 :small]
       [placeholder 28 :small]])]])

(defn- blog-entry [_]
  (let [state (r/atom {})]
    (fn [name]
      (when (not= name (@state :name))
        (reset! state {:name name})
        (download-blog-post! name #(swap! state assoc :text %)))
      [:article.blog-post
       [link [""] "Home"]
       (if (@state :text)
         [markdown (@state :text)]
         [blog-placeholder])
       [:hr]
       [:p
        "If you want to leave feedback on this post, view it on "
        [link (str "https://github.com/jeppes/" name)  "Github"] "."]])))

(defn blog-page [{name :name blogs :blogs repos :repos}]
   [:div.blog-page
    [blog-entry name]
    [:ul
     (for [post-name blogs]
       ^{:key post-name}
       [:li [blog-preview-small (merge {:name post-name
                                        :current-name name}
                                       (get repos post-name))]])]])
