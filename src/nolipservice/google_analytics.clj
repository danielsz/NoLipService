(ns nolipservice.google-analytics
  (:require [clj-http.client :as client]))


(defn report [el v]
  (client/post "https://www.google-analytics.com/collect" {:form-params {:v "1"
                                                                         :tid "UA-42102404-4"
                                                                         :t "event"
                                                                         :ec "meyvn"
                                                                         :ea "build"
                                                                         :el el
                                                                         :ev v
                                                                         :uid "org.danielsz"
                                                                         :ni "1"}}))

