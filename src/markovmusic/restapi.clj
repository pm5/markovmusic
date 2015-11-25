(ns markovmusic.restapi
  (:require [compojure.route]
            [compojure.handler]
            [markovmusic.chain :as chain]
            [markovmusic.sample :as sample]
            [liberator.core :refer [defresource resource]]
            )
  (:use [ring.adapter.jetty :only (run-jetty)]
        [ring.middleware.params :only (wrap-params)]
        [ring.util.response]
        [compojure.core :only (GET POST DELETE ANY defroutes)]
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

(defresource musicbox
  :service-available? true
  :allowed-methods [:get :post :delete]
  :handle-ok (fn [context] (serialize ((music :more))))
  :post! (fn [context]
           (let [sequence (get-in context [:request :form-params "sequence"])]
             ((music :learn) (unserialize sequence))))
  :delete! (fn [context] ((music :forget)) "")
  :available-media-types ["text/plain"])

(defresource twinkle-star
  :service-available? true
  :allowed-methods [:get]
  :handle-ok (fn [_] (serialize (->> sample/twinkle-star
                                     chain/generate-frequency-matrix
                                     chain/generate
                                     (take 512)
                                     )))
  :available-media-types ["text/plain"])

(defresource two-tigers
  :allowed-methods [:get]
  :handle-ok (fn [_] (serialize (->> sample/two-tigers
                                     chain/generate-frequency-matrix
                                     chain/generate
                                     (take 512)
                                     )))
  :available-media-types ["text/plain"])

(defroutes app*
  (GET "/" request "Welcome! Try our v0 API at /0")
  (GET "/0/two-tigers/raw" [] (serialize sample/two-tigers))
  (ANY "/0/two-tigers" request two-tigers)
  (GET "/0/twinkle-star/raw" [] (serialize sample/twinkle-star))
  (ANY "/0/twinkle-star" request twinkle-star)
  (GET "/0/:musicbox/raw" [musicbox] (serialize memory))
  (ANY "/0/:musicbox" request musicbox)
  (compojure.route/not-found "Sorry, there's nothing here."))

(defn with-cors
  [handler]
  (fn [request]
    (let [response (handler request)]
      (ring.util.response/header response "Access-Control-Allow-Origin" "*"))))

(def app (with-cors (wrap-params (compojure.handler/api app*))))
