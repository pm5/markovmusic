(ns markovmusic.restapi
  (:require [compojure.route]
            [compojure.handler]
            [markovmusic.chain :as chain]
            )
  (:use [ring.adapter.jetty :only (run-jetty)]
        [ring.middleware.params :only (wrap-params)]
        [compojure.core :only (GET POST DELETE defroutes)]
        ))

; Reich degrees

(def reich-degrees [:vi :vii :i+ :_ :vii :_ :i+ :vii :vi :_ :vii :_])

(def twinkle-star [:i nil :i nil :v nil :v nil :vi nil :vi nil :v nil nil nil
                   :iv nil :iv nil :iii nil :iii nil :ii nil :ii nil :i nil nil nil
                   :v nil :v nil :iv nil :iv nil :iii nil :iii nil :ii nil nil nil
                   :v nil :v nil :iv nil :iv nil :iii nil :iii nil :ii nil nil nil
                   :i nil :i nil :v nil :v nil :vi nil :vi nil :v nil nil nil
                   :iv nil :iv nil :iii nil :iii nil :ii nil :ii nil :i nil nil nil
                   ])

(def memory twinkle-star)

(def music
  {:memory memory
   :more (defn more-music
           []
           (->> memory
                chain/generate-frequency-matrix
                chain/generate
                (take 512)
                ))
   :learn (defn learn-music
            [sequence]
            (def memory (concat memory sequence)))
   :forget (defn forget-music
             []
             (def memory twinkle-star))
   })

(defn serialize
  [sequence]
  (clojure.string/join " " (map #(if (nil? %) "_" (name %)) sequence)))

(defn unserialize
  [text]
  (map #(keyword %) (clojure.string/split text #" ")))

(defroutes app*
  (GET "/" request "Welcome! Try our v0 API at /0")
  (GET "/0/:musicbox" [musicbox] (serialize ((music :more))))
  (GET "/0/:musicbox/raw" [musicbox] (serialize memory))
  (POST "/0/:musicbox" [musicbox sequence] ((music :learn) (unserialize sequence)) "")
  (DELETE "/0/:musicbox" [musicbox] ((music :forget)) "")
  (compojure.route/not-found "Sorry, there's nothing here."))

(def app (wrap-params (compojure.handler/api app*)))
