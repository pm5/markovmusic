(ns markovmusic.player
	(:use [overtone.live]))

(defn play
  "Plays a sequence of notes with given instrument and start time."
  [inst time notes sep]
  (let [note (first notes)]
    (when note
      (at time (inst note)))

    (let [next-time (+ time sep)]
      (apply-at next-time play [inst next-time (rest notes) sep]))))
