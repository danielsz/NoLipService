(ns nolipservice.core
  (:require [nolipservice.google-analytics :as ga]
            [clojure.java.browse :refer [browse-url]]
            [clojure.string :as str]
            [clojure.java.io :as io])
  (:import [java.util Scanner InputMismatchException]
           [java.util UUID]))

(def report- ga/report)

(defn prompt []
  (let [sc (Scanner. (System/in))
        resp (try (-> sc
                      (.next (re-pattern "[Yy]|[Nn]")))
                  (catch InputMismatchException e
                    (println "Please answer [Y]es or [N]o")
                    nil))]
    (or resp (recur))))

(def report)

(defn opt-in [project]
  (let [dir (io/file (str (System/getProperty "user.home") "/.nolipservice/" (str/lower-case (:name project))))]
    (if (.exists dir)
      (alter-var-root #'report (constantly (let [uuid (.getName (first (.listFiles dir)))]
                                              (partial report- uuid))))
      (do (println (:opt-in project) "Do you accept? (Y/N)")
          (let [resp (str/lower-case (prompt))]
            (case resp
              "y" (let [uuid-file (str dir "/" (UUID/randomUUID))]
                    (and (io/make-parents uuid-file) (spit uuid-file ""))
                    (recur project))
              "n" (do (browse-url (:url project))
                      (println (:opt-out project))
                      (System/exit 0))))))))
