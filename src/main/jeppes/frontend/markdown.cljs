(ns jeppes.frontend.markdown
  (:require
   [reagent.core :as r]
   ["react-markdown" :as ReactMarkdown]
   ["react-syntax-highlighter" :refer [PrismAsyncLight]]
   ["react-syntax-highlighter/dist/esm/styles/prism" :refer [darcula]]))

(defn- highlight [^js args]
  (r/create-element PrismAsyncLight #js{:style darcula
                              :children (.-value args)
                              :language (.-language args)}))

(defn markdown [text]
  [:> ReactMarkdown {:renderers {:code highlight}} text])
