(ns nolipservice.pastebin
  (:require [clj-http.client :as client]
            [clj-http.util :as util]
            [clojure.xml :as xml]
            [clojure.java.io :as io]
            [environ.core :refer [env]]))

(defn new-paste
  ([s]
   (new-paste s s))
  ([t c]
   (client/post "https://pastebin.com/api/api_post.php" {:form-params {"api_dev_key" (env :pastebin-api-dev-key) 
                                                                       "api_user_key" (env :pastebin-api-user-key)
                                                                       "api_paste_private" "0"
                                                                       "api_paste_expire_date" "2W"
                                                                       "api_option" "paste"
                                                                       "api_paste_name" t
                                                                       "api_paste_code" c}})))

(defn- list-pastes- []
  (let [resp (client/post "https://pastebin.com/api/api_post.php" {:form-params {"api_dev_key" (env :pastebin-api-dev-key)
                                                                                 "api_user_key" (env :pastebin-api-user-key)
                                                                                 "api_results_limit" "1000"
                                                                                 "api_option" "list"}})]
    (:body resp)))

(defn list-pastes []
  (xml/parse (io/input-stream (.getBytes (str "<root>" (list-pastes-) "</root>")))))
