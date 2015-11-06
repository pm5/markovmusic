(ns markovmusic.chain)

; frequency-matrix is a hash-map from degree to a frequency vector.
(defn generate-next
  [frequency-matrix current]
  (let [nexts (frequency-matrix current)]
    (nth nexts (int (rand (count nexts))))
    ))

(defn generate
  "Generate an infinite sequence with frequency matrix."
  [frequency-matrix current]
  (let [next-value (generate-next frequency-matrix current)]
    (lazy-seq
      (cons next-value
          (generate frequency-matrix next-value)))))

(def duration-vector [100 200 200 200])

(defn generate-next-duration
  [frequency-matrix current]
  {:value (generate-next frequency-matrix (current :value)) :duration (duration-vector (int (rand (count duration-vector))))})

(defn generate-duration
  "Generate an infinite sequence of values with duration."
  [frequency-matrix current]
  (let [next-value (generate-next-duration frequency-matrix current)]
    (lazy-seq
      (cons current
            (generate-duration frequency-matrix next-value)))))

;(def reich-degrees [:vi :vii :i+ :_ :vii :_ :i+ :vii :vi :_ :vii :_])
;(generate-next reich-frequency-matrix :vi)
