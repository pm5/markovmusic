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
(def reich-pitches (degrees->pitches reich-degrees :diatonic :C4))
(def reich-frequency-matrix (chain/generate-frequency-matrix reich-degrees))

(let [sequence (cycle (take 32 (chain/generate reich-frequency-matrix nil)))]
  (player/play-fixed-length-notes piano (now) (degrees->pitches sequence :diatonic :C4) 200)
  (player/play-fixed-length-notes piano (+ 1600 (now)) (degrees->pitches sequence :diatonic :G3) 400)
  )

(let [sequence (cycle (take 32 (chain/generate-ct reich-frequency-matrix {:value nil :duration 0})))]
  (player/play piano (now) sequence :major :A3)
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


(let [sequence (take 64 (chain/generate (chain/generate-frequency-matrix two-tigers) nil))]
  (player/play-fixed-length-notes piano (now) (degrees->pitches sequence :major :C4) 200)
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
