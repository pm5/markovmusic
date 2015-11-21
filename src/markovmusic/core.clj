(ns markovmusic.core
  (:require [markovmusic.player :as player]
            [markovmusic.chain :as chain]
            [markovmusic.midi :as midi]
            [markovmusic.restapi :as api])
  (:use [overtone.live]
        [overtone.inst.piano]
        [ring.adapter.jetty :only (run-jetty)]
        [environ.core :refer [env]]
        ))

; REST API
;(def server (run-jetty #'api/app {:port 8080 :join? false}))
;(.stop server)

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 8080))]
    (run-jetty #'api/app {:port port :join? false})))

; Two tigers

(def two-tigers [:i nil :ii nil :iii nil :i nil
                 :i nil :ii nil :iii nil :i nil
                 :iii nil :iv nil :v nil nil nil
                 :iii nil :iv nil :v nil nil nil
                 :v :vi :v :iv :iii nil :i nil
                 :v :vi :v :iv :iii nil :i nil
                 :i nil :v- nil :i nil nil nil
                 :i nil :v- nil :i nil nil nil])

;(let [sequence (->> two-tigers
                    ;)]
  ;(player/play-fixed-length-notes piano (now) 200
                                  ;(degrees->pitches sequence :major :C4))
  ;)

; Twinkle twinkle little star

(def twinkle-star [:i nil :i nil :v nil :v nil :vi nil :vi nil :v nil nil nil
                   :iv nil :iv nil :iii nil :iii nil :ii nil :ii nil :i nil nil nil
                   :v nil :v nil :iv nil :iv nil :iii nil :iii nil :ii nil nil nil
                   :v nil :v nil :iv nil :iv nil :iii nil :iii nil :ii nil nil nil
                   :i nil :i nil :v nil :v nil :vi nil :vi nil :v nil nil nil
                   :iv nil :iv nil :iii nil :iii nil :ii nil :ii nil :i nil nil nil
                   ])

;(let [sequence (->> twinkle-star
                    ;chain/generate-frequency-matrix
                    ;chain/generate
                    ;(take 512)
                    ;cycle
                    ;)]
  ;(player/play-fixed-length-notes piano (now) 200
                                  ;(degrees->pitches sequence :major :C4))
  ;)

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
