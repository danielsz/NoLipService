(ns nolipservice.paste-ee
  (:require [clj-http.client :as client]
            [cheshire.core :refer :all]
            [environ.core :refer [env]]))

(defn new-paste
  ([s]
   (new-paste s s s))
  ([d n c]
   (client/post "https://api.paste.ee/v1/pastes" {:form-params {:key (env :paste-ee-api-key)
                                                                :description d
                                                                :sections [{:name n
                                                                            :contents c}]}
                                                  :socket-timeout 1000
                                                  :conn-timeout 1000
                                                  :throw-entire-message? true
                                                  :content-type :json
                                                  :as :json})))

(defn list-pastes []
  (client/get "https://api.paste.ee/v1/pastes" {:query-params {:key (env :paste-ee-api-key)
                                                               :perpage 25}
                                                :as :json}))
