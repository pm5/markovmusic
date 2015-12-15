(defproject markovmusic "0.1.0-SNAPSHOT"
  :description "Generate music with Markov chain."
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [overtone "0.9.1"]
                 [compojure "1.0.1"]
                 [ring "1.0.1"]
                 [liberator "0.14.0"]
                 [com.taoensso/carmine "2.12.1"]
                 [environ "1.0.0"]]
  :main ^:skip-aot markovmusic.core
  :target-path "target/%s"
  :plugins [[environ/environ.lein "0.3.1"]]
  :hooks [environ.leiningen.hooks]
  :profiles {:production {:env {:production true}}})
