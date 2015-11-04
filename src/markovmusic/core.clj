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

(stop)

(player/play-fixed-length-notes piano (now) reich-pitches 200)
(player/play-fixed-length-notes piano (now) (degrees->pitches (take 16 (chain/generate-notes reich-frequency-matrix nil)) :diatonic :C4) 200)

(player/play piano (now)
             (take 16 (chain/generate-notes-duration reich-frequency-matrix nil 300))
             :minor :A3)
