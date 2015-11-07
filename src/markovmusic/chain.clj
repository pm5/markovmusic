(ns markovmusic.chain)

; frequency-matrix is a hash-map from degree to a frequency vector.
(defn generate-next
  [frequency-matrix current]
  (let [nexts (frequency-matrix current)]
    (nth nexts (int (rand (count nexts))))
    ))

(defn generate
  "Generate an infinite sequence with discrete Markov chain"
  [frequency-matrix current]
  (let [next-value (generate-next frequency-matrix current)]
    (lazy-seq
      (cons current
          (generate frequency-matrix next-value)))))

(defn exponetial-rand
  "Generate a random value between 0 and infinite by exponential distrubtion of given rate."
  [rate]
  (/ (Math/log (- 1 (rand))) (- rate)))

(defn generate-next-ct
  [frequency-matrix holding-rates current]
  (let [next-value    (generate-next frequency-matrix (current :value))
        next-duration (exponetial-rand (holding-rates next-value))]
    {:value next-value :duration next-duration}))

(defn generate-ct
  "Generate an infinite sequence of values and duration with CTMC."
  ([frequency-matrix holding-rates current]
   (let [next-value (generate-next-ct frequency-matrix holding-rates current)]
     (lazy-seq
       (cons current
             (generate-ct frequency-matrix holding-rates next-value)))))

  ([frequency-matrix current]
   (generate-ct frequency-matrix
                (zipmap (keys frequency-matrix) (map (fn [x] 0.005) (range (count frequency-matrix))))
                current))
  )

(defn generate-frequency-matrix
  []
  )
