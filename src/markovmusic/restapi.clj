(ns markovmusic.restapi
  (:require [compojure.route]
            [compojure.handler]
            [markovmusic.chain :as chain]
            )
  (:use [ring.adapter.jetty :only (run-jetty)]
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

(defn more-music
  []
  (->> twinkle-star
       chain/generate-frequency-matrix
       chain/generate
       (take 512)
       )
  )

;(let [sequence (->> reich-degrees
                    ;)]
  ;(player/play-fixed-length-notes piano (now) 200
                                  ;(degrees->pitches sequence :diatonic :C4))
  ;)

(defroutes app*
  (GET "/" request "Welcome! Try our v0 API at /0")
  (GET "/0/:machine" [machine] (clojure.string/join " " (map #(if (nil? %) "_" (name %)) (more-music))))
  ;(GET "/0/:machine" [machine] machine)
  (compojure.route/not-found "Sorry, there's nothing here."))

(def app (compojure.handler/api app*))
