(ns markovmusic.core
	(:require [markovmusic.player :as player]
						[markovmusic.chain :as chain]
            [markovmusic.ctmc :as ctmc]
            [markovmusic.midi :as midi])
	(:use [overtone.live]
				[overtone.inst.piano]
        [overtone.inst.drum]))

; Reich degrees

(def reich-degrees [:vi :vii :i+ :_ :vii :_ :i+ :vii :vi :_ :vii :_])

(let [sequence reich-degrees]
  (player/play-fixed-length-notes piano (now) (degrees->pitches sequence :diatonic :C4) 200)
  )

; Two tigers

(def two-tigers [:i nil :ii nil :iii nil :i nil
                 :i nil :ii nil :iii nil :i nil
                 :iii nil :iv nil :v nil nil nil
                 :iii nil :iv nil :v nil nil nil
                 :v :vi :v :iv :iii nil :i nil
                 :v :vi :v :iv :iii nil :i nil
                 :i nil :v- nil :i nil nil nil
                 :i nil :v- nil :i nil nil nil])

(let [sequence two-tigers]
  (player/play-fixed-length-notes piano (now) (degrees->pitches sequence :major :C4) 200)
  )

; CTMC

(let [sequence (cycle (take 32 (chain/generate-ct reich-frequency-matrix {:value nil :duration 0})))]
  (player/play piano (now) sequence :major :A3)
  )

(player/play-duration-notes piano (now) (midi/read-file "resources/WTCBkI/Fugue1.mid" 1))

(let [sequence (map #(assoc % :freq (% :value))
                    (take 32
                          (chain/generate-ct
                            (chain/generate-frequency-matrix
                              (map #(% :freq) (midi/read-file "resources/WTCBkI/Fugue1.mid" 1)))
                            {:value nil :duration 0})))]
  (player/play piano (now) sequence))

(stop)
