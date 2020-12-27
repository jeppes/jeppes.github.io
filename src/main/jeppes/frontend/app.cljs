(ns jeppes.frontend.app
  (:require #_[reagent.core :as r]
            [reagent.dom :as dom]))

(defn link
  ([url text]
   (link {} url text))
  ([props url text]
   [:a (merge props {:href url :target "_blank"}) text]))

(defn home-page []
  [:<>
   [:section
    [:h1 "Hej " [link {:id "hello"} "https://github.com/jeppes/" "👋"]]
    [:p "I am a programmer based in Stockholm working at "
     [link "https://www.sanalabs.com/" "Sana Labs"] "."]
    [:p "Previously, I've worked worked on various software related things at "
     [link "https://www.spotify.com/" "Spotify"] ", "
     [link "https://www.mentimeter.com/" "Mentimeter"] ", "
     [link "https://www.ef.com/" "EF"] ", and "
     [link "https://www.lifesum.com/" "Lifesum"] "."]
    [:p "My background is in computer science and I have a particular interest "
     "in functional programming languages and patterns."]]
   [:section.projects
    [:h1 "Projects"]
    [:ul
     [:li [link "https://github.com/spotify/diffuser" "Dif(fuser)"] " - A library for declaratively bridging data and side effects"]
     [:li [link "https://github.com/jeppes/Switcher" "Switcher"] " - A library for pattern matching in SwiftUI."]
     [:li [link "https://github.com/spotify/Mobius.swift" "Mobius.swift"] " - A state management library I helped build while at Spotify."]
     [:li [link "https://github.com/jeppes/tills" "tills"] " - A toy programming language."]
     [:li [link "https://github.com/jeppes/oops" "OOPs"] " - A blog post about functional programming." ]]
    [:p "Check out my " [link "https://github.com/jeppes" "Github"] " for more"]]])

(defn mount []
  (dom/render [home-page]
              (js/document.getElementById "root")))

(defn init! [] (mount))

(defn ^:dev/after-load start []
  (init!))

  ;; <h1>Hej <a href="https://github.com/jeppes/" id="hello">👋</a></h1>