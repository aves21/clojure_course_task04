(ns vk.view.registration
  (:require [me.raynes.laser :as l]
            [clojure.java.io :refer [file]]
            [vk.view.page :as page]))



(def registraion-form (l/select page/main-html (l/id= "registrationFrag")))

(l/defragment registraion-form-frag registraion-form [{:keys [firstName lastName login password confirmpassword error]}]
  (l/id= "errorLabel") (l/content (if-not (nil? error) error ""))
  (l/id= "firstName") (l/attr :value (if-not (nil? firstName) firstName ""))
  (l/id= "lastName") (l/attr :value (if-not (nil? lastName) lastName ""))
  (l/id= "login") (l/attr :value (if-not (nil? login) login ""))
  (l/id= "password") (l/attr :value (if-not (nil? password) password ""))
  (l/id= "confirmpassword") (l/attr :value (if-not (nil? confirmpassword) confirmpassword "")))


(defn show-registraion-page [data]
   (l/document page/main-html
              (l/id= "main")
              (l/content (registraion-form-frag data))))

