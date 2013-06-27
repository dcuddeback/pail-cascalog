(defproject pail-cascalog "0.1.0-SNAPSHOT"
  :description "Utilties for working with Pail in Cascalog."
  :url "https://github.com/dcuddeback/pail-cascalog"
  :license {:name "MIT License"
            :url "http://opensource.org/licenses/MIT"}

  :min-lein-version "2.0.0"

  :dependencies [[org.clojure/clojure "1.5.1"]
                 [cascalog/cascalog-core "1.10.1" :exclusions [org.slf4j/slf4j-log4j12]]
                 [com.backtype/dfs-datastores "1.3.4"]
                 [com.backtype/dfs-datastores-cascading "1.3.4" :exclusions [cascading/cascading-core
                                                                             cascading/cascading-hadoop]]]

  :profiles {:1.3 {:dependencies [[org.clojure/clojure "1.3.0"]]}
             :1.4 {:dependencies [[org.clojure/clojure "1.4.0"]]}
             :1.5 {:dependencies [[org.clojure/clojure "1.5.1"]]}
             :1.6 {:dependencies [[org.clojure/clojure "1.6.0-master-SNAPSHOT"]]}

             :provided {:dependencies [[org.slf4j/slf4j-log4j12 "1.7.4"]]}

             :dev {:dependencies [[midje "1.5.1"]]
                   :plugins [[lein-midje "3.0.1"]]}

             :lint {:global-vars {*warn-on-reflection* true}}}

  :aliases {"lint" ["with-profile" "+lint" "midje"]}

  :repositories {"sonatype" {:url "http://oss.sonatype.org/content/repositories/releases"
                             :snapshots false
                             :releases {:checksum :fail :update :always}}
                 "sonatype-snapshots" {:url "http://oss.sonatype.org/content/repositories/snapshots"
                                       :snapshots true
                                       :releases {:checksum :fail :update :always}}}

  :deploy-repositories [["releases" {:url "https://clojars.org/repo" :username :gpg :password :gpg}]
                        ["snapshots" {:url "https://clojars.org/repo" :username :gpg :password :gpg}]])
