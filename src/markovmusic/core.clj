(ns markovmusic.core
	(:require [markovmusic.player :as player]
						[markovmusic.chain :as chain]
            [markovmusic.ctmc :as ctmc])
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

(player/play-fixed-length-notes piano (now) reich-pitches 200)
(player/play-fixed-length-notes piano (now) (degrees->pitches (take 16 (chain/generate-notes reich-frequency-matrix nil)) :diatonic :C4) 200)

(player/play piano (now)
             (take 64 (chain/generate-notes-duration reich-frequency-matrix {:degree nil :duration 0}))
             :minor :A3)
