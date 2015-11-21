(ns markovmusic.live
  (:require [markovmusic.player :as player]
            [markovmusic.sample :as sample]
            [markovmusic.chain :as chain])
  (:use [overtone.live]
        [overtone.inst.piano]))

(def reich-degrees [:vi :vii :i+ :_ :vii :_ :i+ :vii :vi :_ :vii :_])

(def twinkle-star [:i nil :i nil :v nil :v nil :vi nil :vi nil :v nil nil nil
                   :iv nil :iv nil :iii nil :iii nil :ii nil :ii nil :i nil nil nil
                   :v nil :v nil :iv nil :iv nil :iii nil :iii nil :ii nil nil nil
                   :v nil :v nil :iv nil :iv nil :iii nil :iii nil :ii nil nil nil
                   :i nil :i nil :v nil :v nil :vi nil :vi nil :v nil nil nil
                   :iv nil :iv nil :iii nil :iii nil :ii nil :ii nil :i nil nil nil
                   ])

(def two-tigers [:i nil :ii nil :iii nil :i nil
                 :i nil :ii nil :iii nil :i nil
                 :iii nil :iv nil :v nil nil nil
                 :iii nil :iv nil :v nil nil nil
                 :v :vi :v :iv :iii nil :i nil
                 :v :vi :v :iv :iii nil :i nil
                 :i nil :v- nil :i nil nil nil
                 :i nil :v- nil :i nil nil nil])

;(let [sequence (->> sample/twinkle-star
                    ;chain/generate-frequency-matrix
                    ;chain/generate
                    ;(take 128)
                    ;)]
  ;(player/play-fixed-length-notes piano (now) 200
                                  ;(degrees->pitches sequence :major :C4))
  ;)

;(stop)
