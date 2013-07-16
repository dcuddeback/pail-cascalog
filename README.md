# pail-cascalog

[![Build Status](https://travis-ci.org/dcuddeback/pail-cascalog.png?branch=master)](https://travis-ci.org/dcuddeback/pail-cascalog)

Utilities for working with Pail in Cascalog.

## Usage

Add `pail-cascalog` to your project's dependencies. If you're using Leiningen, your `project.clj`
should look something like this:

~~~clojure
(defproject ...
  :dependencies [[pail-cascalog VERSION]])
~~~

Where `VERSION` is the latest version on [Clojars](https://clojars.org/pail-cascalog).


### Creating a Tap from a `PailStructure`

In order to create a Cascalog tap from a `PailStructure`, it is necessary to first create a
`PailSpec` from the `PailStructure`. [`clj-pail`](https://github.com/dcuddeback/clj-pail) provides a
method that does that for us. Once we have a `PailSpec`, `pail-cascalog` can be used to create a
tap:

~~~clojure
(require '[clj-pail.core :as pail]
(require '[pail-cascalog.core :as pail-cascalog])

; can be any PailStructure
(def structure (com.backtype.hadoop.pail.DefaultPailStructure.))

(def tap (-> structure
           (pail/spec)
           (pail-cascalog/tap-options :field-name "object")
           (pail-cascalog/tap "path/to/data")))
~~~

The tap can be customized by the options passed to `tap-options`. In the presence of a
vertically-partitioned `PailStructure`, a subset of data can be consumed by specifying paths with
the `:attributes` option:

~~~clojure
; read data only from "foo/bar" and "baz/qux" directories
(pail-cascalog/tap-options structure :attributes [["foo" "bar"] ["baz" "qux"]])
~~~

### Creating a Tap from an Existing Pail

Existing pails can be opened as Cascalog taps using `pail-cascalog.core/pail->tap`:

~~~clojure
(require '[clj-pail.core :as pail])
(require '[pail-cascalog.core :as pail-cascalog])

; open an existing pail
(def pail (pail/pail "path/to/data"))

; convert it to a Cascalog tap
(def tap (pail-cascalog/pail->tap pail))
~~~

The `pail->tap` function accepts the same options as `tap-options`:

~~~clojure
; customize the tap to have a custom field name ans read from two partitions: "foo" and "bar"
(def tap (pail-cascalog/pail->tap pail :field-name "object" :attributes [["foo"] ["bar"]]))
~~~

## License

Copyright © 2013 David Cuddeback

Distributed under the [MIT License](LICENSE).
