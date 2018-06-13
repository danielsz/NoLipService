(ns nolipservice.google-analytics
  (:require [clj-http.client :as client]))


(defn report [cid el v & {:keys [debug] :or {debug false}}]
  (let [endpoint (if debug
                   "https://www.google-analytics.com/debug/collect"
                   "https://www.google-analytics.com/collect")]
    (client/post endpoint {:form-params {:v "1"
                                         :tid "UA-42102404-4"
                                         :t "event"
                                         :ec "meyvn"
                                         :ea "build"
                                         :cid cid
                                         :el el
                                         :ev v
                                         :ni "1"}})))

