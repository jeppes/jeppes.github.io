(ns jeppes.frontend.markdown
  (:require
   ["react-markdown" :as ReactMarkdown]))

(defn markdown [text]
  [:> ReactMarkdown text])
