(ns jeppes.frontend.pages.home
  (:require [jeppes.frontend.router :refer [link]]
            [jeppes.frontend.pages.blog :refer [blog-preview]]))

(defn home-page [{blog-posts :blogs repos :repos}]
  [:div.home-page
   [:section
    [:h1 "Hej " [link {:id "hello"} "https://github.com/jeppes/" "👋"]]
    [:p "I am a programmer based in Stockholm working at "
     [link "https://www.sanalabs.com/" "Sana Labs"] "."]
    [:p "Previously, I've worked on various software related things at "
     [link "https://www.spotify.com/" "Spotify"] ", "
     [link "https://www.mentimeter.com/" "Mentimeter"] ", "
     [link "https://www.ef.com/" "EF"] ", and "
     [link "https://www.lifesum.com/" "Lifesum"] "."]
    [:p "My background is in computer science and I have a particular interest "
     "in functional programming languages and patterns."]]
   [:h1 "Posts"]
   [:section.posts
    (for [name blog-posts]
      ^{:key name} [blog-preview (merge {:name name} (get repos name))])]
   [:section.projects
    [:h1 "Projects"]
    [:ul
     [:li [link "https://github.com/spotify/diffuser" "Dif(fuser)"] " - A library for declaratively bridging data and side effects."]
     [:li [link "https://github.com/jeppes/Switcher" "Switcher"] " - A library for pattern matching in SwiftUI."]
     [:li [link "https://github.com/spotify/Mobius.swift" "Mobius.swift"] " - A state management library I helped build while at Spotify."]
     [:li [link "https://github.com/jeppes/tills" "tills"] " - A toy programming language."]]
    [:p "Check out my " [link "https://github.com/jeppes" "Github"] " for more."]]])
