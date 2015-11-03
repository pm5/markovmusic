(ns markovmusic.chain)

; frequency-matrix is a hash-map from degree to a frequency vector.
(defn generate-next-note
  "Generate next note with frequency matrix."
  [frequency-matrix note]
  (let [notes (frequency-matrix note)]
    (nth notes (int (rand (count notes))))
    ))

(defn generate-notes
  "Generate notes with frequency matrix."
  [frequency-matrix note]
  (let [next-note (generate-next-note frequency-matrix note)]
    (lazy-seq
      (cons next-note
          (generate-notes frequency-matrix next-note)))))


;(def reich-degrees [:vi :vii :i+ :_ :vii :_ :i+ :vii :vi :_ :vii :_])
;(generate-next-note reich-frequency-matrix :vi)
