(ns markovmusic.core-test
  (:require [clojure.test :refer :all]
            [markovmusic.core :refer :all]))

(deftest test-play-reich-degrees
  (testing "FIXME, I fail."
    (play (overtone.live/now) [69 71 72 nil 71 nil 72 71 69 nil 71 nil] 200)))
