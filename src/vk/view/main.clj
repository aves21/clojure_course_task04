(ns vk.view.main
    (:require [me.raynes.laser :as l]
              [clojure.java.io :refer [file]]
              [vk.view.page :as page]
              [vk.view.menu :as menu]))

(def main-form (l/select page/main-html (l/id= "mainFrag")))

(l/defragment main-form-frag main-form [{:keys [first_name last_name]}]
  (l/id= "userLabel") (l/content (if-not (and (nil? first_name) (nil? last_name)) (str first_name " " last_name) "")))


(defn show-main-page [data]
   (l/document page/main-html
              (l/id= "menu")
              (l/content (menu/create-menu)) 
              (l/id= "main")
              (l/content (main-form-frag data))))