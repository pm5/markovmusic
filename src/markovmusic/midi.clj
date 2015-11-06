(ns markovmusic.midi
  (:import (java.io File)
           (javax.sound.midi MidiSystem Sequence MidiMessage MidiEvent ShortMessage Track)))

(def note-on  0x90)
(def note-off 0x80)

(defn read-file
  ([file-name] (read-file file-name 0))
  ([file-name track]
   (let [sequence (MidiSystem/getSequence (File. file-name))
         track    (-> sequence .getTracks (aget track))]
     (->> (range (.size track))
          (map #(-> {:event (.get track %) :message (-> track (.get %) .getMessage)}))
          (filter #(instance? ShortMessage (% :message)))
          (map #(assoc % :command (.getCommand (% :message))))
          (filter #(or (= note-on (% :command)) (= note-off (% :command))))
          (map #(-> {
                     :freq (.getData1 (% :message))
                     :tick (.getTick (% :event))
                     :velocity (.getData2 (% :message))
                     :command (% :command)
                     }))
          (reduce (fn [[current-event notes] event]
                    (if (= note-on (event :command))
                      [event notes]
                      [nil (if (not= nil current-event) (conj notes (assoc current-event :duration (- (event :tick) (current-event :tick)))) notes)]))
                  [nil []])
          last))))

;(read-file "resources/WTCBkI/Fugue1.mid" 1)
