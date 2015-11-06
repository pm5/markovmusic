(ns markovmusic.core
	(:require [markovmusic.player :as player]
						[markovmusic.chain :as chain]
            [markovmusic.ctmc :as ctmc]
            [markovmusic.midi :as midi])
	(:use [overtone.live]
				[overtone.inst.piano]
        [overtone.inst.drum]))

(def reich-degrees [:vi :vii :i+ :_ :vii :_ :i+ :vii :vi :_ :vii :_])
(def reich-pitches (degrees->pitches reich-degrees :diatonic :C4))
(def reich-frequency-matrix { nil [:vi], :vi [:vii :_], :vii [:i+ :_ :vi :_], :i+ [:_ :vii], :_ [:vii :i+ :vii nil] })

(def happy-degrees [:i :i :ii :i :iv :iii :i :i :ii :i :v :iv :i :i :i+ :vi :iv :iii :vii :vii :vi :iv :v :iv])
(def happy-pitches (degrees->pitches happy-degrees :diatonic :C4))
(player/play-fixed-length-notes piano (now) happy-pitches 200)

(stop)

;(player/play-fixed-length-notes piano (now) reich-pitches 200)
(let [sequence (take 32 (chain/generate reich-frequency-matrix nil))]
  (player/play-fixed-length-notes piano (now) (degrees->pitches sequence :diatonic :C4) 200)
  ;(player/play-fixed-length-notes piano (+ (now) 1600) (degrees->pitches sequence :diatonic :G3) 200)
  )

(let [sequence (take 32 (chain/generate-duration reich-frequency-matrix {:value nil :duration 0}))]
  (player/play piano (now) sequence :minor :A3)
  )

(midi/read-file "resources/WTCBkI/Fugue1.mid" 1)
