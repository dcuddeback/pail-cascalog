(ns pail-cascalog.core-test
  (:require [pail-cascalog.core :as pail])
  (:use midje.sweet)
  (:import (com.backtype.hadoop.pail PailStructure PailSpec PailPathLister)
           (com.backtype.cascading.tap PailTap$PailTapOptions)
           (org.apache.hadoop.fs Path)))


(defchecker instance-of
  [expected]
  (checker [actual]
    (instance? expected actual)))


(facts "tap-options"
  (let [spec (PailSpec.)]

    (facts "without options"
      (fact "creates a PailTapOptions with default properties"
        (pail/tap-options spec) => (instance-of PailTap$PailTapOptions)
        (.spec      (pail/tap-options spec)) => spec
        (.fieldName (pail/tap-options spec)) => "bytes"
        (.attrs     (pail/tap-options spec)) => nil?
        (.lister    (pail/tap-options spec)) => nil?))


    (facts "with options"
      (fact "creates a PailTapOptions with custom field name"
        (.fieldName (pail/tap-options spec :field-name "foo")) => "foo")

      (fact "creates a PailTapOptions with custom attributes"
        (seq (.attrs (pail/tap-options spec :attributes [["foo" "bar"] ["baz"]]))) => (just [["foo" "bar"] ["baz"]]))

      (fact "treats a list of strings as a single attributes"
        (seq (.attrs (pail/tap-options spec :attributes ["foo" "bar" "baz"]))) => (just [["foo" "bar" "baz"]]))

      (fact "creates a PailTapOptions with custom PailPathLister"
        (let [lister (proxy [PailPathLister] [])]
          (.lister (pail/tap-options spec :lister lister)) => lister)))))


(facts "tap"
  (let [spec (PailSpec.)
        options-foo (PailTap$PailTapOptions. spec "foo" nil nil)
        options-bar (PailTap$PailTapOptions. spec "bar" nil nil)]

    (fact "creates a PailTap with the given path"
      (.getPath (pail/tap options-foo "foo/bar")) => (Path. "foo/bar")
      (.getPath (pail/tap options-foo "baz")) => (Path. "baz"))

    (fact "creates a PailTap with the given PailTapOptions"
      (.. (pail/tap options-foo "foo/bar") getScheme getSpec) => spec
      (.. (pail/tap options-foo "foo/bar") getScheme getSourceFields (get 1)) => "foo"
      (.. (pail/tap options-bar "foo/bar") getScheme getSourceFields (get 1)) => "bar")))
