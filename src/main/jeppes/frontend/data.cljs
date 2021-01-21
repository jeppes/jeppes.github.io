(ns jeppes.frontend.data)

(defn fetch-json! [url]
  (-> url
      (js/fetch)
      (.then #(.json %))
      (.then #(js->clj % :keywordize-keys true))))

(defn fetch-text! [url]
  (-> url
      (js/fetch)
      (.then #(.text %))))
