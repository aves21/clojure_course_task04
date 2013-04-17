(ns vk.view.login  
  (:require [me.raynes.laser :as l]
            [clojure.java.io :refer [file]]
            [vk.view.page :as page]))




(def login-form (l/select page/main-html (l/id= "loginFrag")))

(l/defragment login-form-frag login-form [{:keys [error]}]
  (l/id= "errorLabel") (l/content (if-not (nil? error) error "")))


(defn show-login-page [error]
   (l/document page/main-html
              (l/id= "main")
              (l/content (login-form-frag error))))