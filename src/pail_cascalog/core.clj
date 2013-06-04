(ns pail-cascalog.core
  "Utilties for working with Pail in Cascalog."
  (:import (com.backtype.hadoop.pail Pail PailSpec)
           (com.backtype.cascading.tap PailTap PailTap$PailTapOptions)))


(defn ^PailTap$PailTapOptions tap-options
  "Creates a `PailTapOptions` from a `PailSpec`. The `PailTapOptions` can then be used to create a
  `PailTap`. The `PailTap` will use the provided `PailSpec` to serialize, deserialize, and partition
  the data stored by the tap.

  When sourcing and sinking tuple, the tuple will have two fields. The first field is the path
  within the pail and is called `\"pail_root\"`. The second field will be named whatever string is
  given as the `:field-name` options, which defaults to `\"bytes\"`.

  The tap can be limited to a subset of the paths within the pail by giving a list of paths as the
  `:attributes` option. Each path must be a list of strings, each string corresponding to one level
  of the path. For example, to include data from the paths `\"foo/bar\"` and `\"baz\"`, the
  `:attributes` option would be `[[\"foo\" \"bar\"] [\"baz\"]]`. This option defaults to `nil`,
  which includes all paths within the pail.

  The last option is `:lister`, which attaches a `PailPathLister` to the `PailTap`."
  [spec & {:keys [field-name attributes lister]
           :or {field-name "bytes"}}]
  (PailTap$PailTapOptions.
           spec
           field-name
           (when attributes
             (if (every? string? attributes)
               (into-array [attributes])
               (into-array attributes)))
           lister))


(defn ^PailTap tap
  "Creates a `PailTap`. The tap will source and sink data from the path provided as the `path`
  parameter. The tap's behavior is specified by a `PailTapOptions` object, provided as the `options`
  parameter. The `options` parameter can be curried into a closure to create a factory-style
  function for creating taps with the same behavior at different paths."
  [options path]
  (PailTap. path options))
