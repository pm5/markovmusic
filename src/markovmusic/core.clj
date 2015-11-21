(ns markovmusic.core
  (:require [markovmusic.chain :as chain]
            [markovmusic.midi :as midi]
            [markovmusic.restapi :as api])
  (:use [ring.adapter.jetty :only (run-jetty)]
        [environ.core :refer [env]]
        ))

; REST API

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 8080))]
    (run-jetty #'api/app {:port port :join? false})))

; CTMC

;(let [sequence (take 32 (chain/generate-ct (chain/generate-frequency-matrix reich-degrees) {:value nil :duration 0}))]
  ;(player/play piano (now) sequence :major :A3)
  ;)

;(let [sequence (take 64 (chain/generate-ct (chain/generate-frequency-matrix two-tigers) {:value nil :duration 0}))]
  ;(player/play piano (now) sequence :major :C4)
  ;)

;(let [sequence (take 128 (chain/generate-ct (chain/generate-frequency-matrix twinkle-star) {:value nil :duration 0}))]
  ;(player/play piano (now) sequence :major :C4)
  ;)

; MIDI

;(player/play-duration-notes piano (now) (midi/read-file "resources/WTCBkI/Fugue1.mid" 1))

; XXX needs refactor to map reduce
;(let [sequence
      ;(map #(assoc % :freq (% :value))
           ;(take 128
                 ;(chain/generate-ct
                   ;(chain/generate-frequency-matrix
                     ;(map #(% :freq) (midi/read-file "resources/WTCBkI/Fugue1.mid" 1))))))]
  ;(player/play piano (now) sequence))
