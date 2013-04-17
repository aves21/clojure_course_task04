(ns vk.view.page
  (:require [me.raynes.laser :as l]
            [clojure.java.io :refer [file]]))

(def main-html
  (l/parse
   (slurp (clojure.java.io/resource "public/main.html"))))