(ns markovmusic.restapi
  (:require [compojure.route]
            [compojure.handler]
            [markovmusic.chain :as chain]
            [markovmusic.sample :as sample]
            )
  (:use [ring.adapter.jetty :only (run-jetty)]
        [ring.middleware.params :only (wrap-params)]
        [ring.util.response]
        [compojure.core :only (GET POST DELETE defroutes)]
        ))

(def memory [])

(def music
  {:memory memory
   :more (defn more-music
           []
           (->> memory
                chain/generate-frequency-matrix
                chain/generate
                (take 512)
                ))
   :learn (defn learn-music
            [sequence]
            (def memory (concat memory sequence)))
   :forget (defn forget-music
             []
             (def memory []))
   })

(defn serialize
  [sequence]
  (clojure.string/join " " (map #(if (nil? %) "_" (name %)) sequence)))

(defn unserialize
  [text]
  (map #(keyword %) (clojure.string/split text #" ")))

(defroutes app*
  (GET "/" request "Welcome! Try our v0 API at /0")
  (GET "/0/two-tigers/raw" [] (serialize sample/two-tigers))
  (GET "/0/two-tigers" [] (serialize (->> sample/two-tigers
                                          chain/generate-frequency-matrix
                                          chain/generate
                                          (take 512)
                                          )))
  (GET "/0/twinkle-star/raw" [] (serialize sample/twinkle-star))
  (GET "/0/twinkle-star" [] (serialize (->> sample/twinkle-star
                                            chain/generate-frequency-matrix
                                            chain/generate
                                            (take 512)
                                            )))
  (GET "/0/:musicbox" [musicbox] (serialize ((music :more))))
  (GET "/0/:musicbox/raw" [musicbox] (serialize memory))
  (POST "/0/:musicbox" [musicbox sequence] ((music :learn) (unserialize sequence)) "")
  (DELETE "/0/:musicbox" [musicbox] ((music :forget)) "")
  (compojure.route/not-found "Sorry, there's nothing here."))

(defn with-cors
  [handler]
  (fn [request]
    (let [response (handler request)]
      (ring.util.response/header response "Access-Control-Allow-Origin" "*"))))

(def app (with-cors (wrap-params (compojure.handler/api app*))))
