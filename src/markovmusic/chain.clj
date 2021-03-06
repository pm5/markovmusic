(ns markovmusic.chain)

; frequency-matrix is a hash-map from degree to a frequency vector.
(defn generate-next
  [frequency-matrix current]
  (let [nexts (frequency-matrix current)]
    (nth nexts (int (rand (count nexts))))
    ))

(defn generate
  "Generate an infinite sequence with discrete Markov chain"
  ([frequency-matrix] (generate frequency-matrix nil))
  ([frequency-matrix current]
   (let [next-value (generate-next frequency-matrix current)]
     (lazy-seq
       (cons current
             (generate frequency-matrix next-value)))))
  )

(defn exponetial-rand
  "Generate a random value between 0 and infinite by exponential distrubtion of given rate."
  [rate]
  (/ (Math/log (- 1 (rand))) (- rate)))

(defn generate-next-ctmc
  [frequency-matrix holding-rates current]
  (let [next-value    (generate-next frequency-matrix (current :value))
        next-duration (exponetial-rand (holding-rates next-value))]
    {:value next-value :duration next-duration}))

(defn generate-ctmc
  "Generate an infinite sequence of values and duration with CTMC."
  ([frequency-matrix] (generate-ctmc frequency-matrix {:value nil :duration 0}))
  ([frequency-matrix current]
   (generate-ctmc frequency-matrix
                (zipmap (keys frequency-matrix)
                        (map (fn [x] 0.005) (range (count frequency-matrix))))
                current))
  ([frequency-matrix holding-rates current]
   (let [next-value (generate-next-ctmc frequency-matrix holding-rates current)]
     (lazy-seq
       (cons current
             (generate-ctmc frequency-matrix holding-rates next-value)))))
  )

(defn generate-frequency-matrix
  [sequence]
  (->> sequence
       (reduce (fn [[prev matrix] current]
                 [current (assoc matrix prev (vec (conj (matrix prev) current)))])
               [nil {}])
       last))

;(generate-frequency-matrix [:a :b :c :d :a :c :a])

