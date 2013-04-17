(ns vk.controller.registration 
  (:require [vk.view.registration :as vr]
            [vk.model.model :as m]
            [vk.model.sha :as sha]
            [clojure.string :as string]
            [vk.model.sha :as sha]))

(defn show-registraion-page []
  (vr/show-registraion-page {}))


(defn registration [form-params]
  (let [firstName (:firstName form-params)
        lastName (:lastName form-params)
        login (:login form-params)
        password (:password form-params)
        confirmpassword (:confirmpassword form-params)]
      (cond
        (or (string/blank? firstName)
            (string/blank? lastName)
            (string/blank? login)
            (string/blank? password)
            (string/blank? confirmpassword)) (vr/show-registraion-page (assoc form-params :error "Не все поля заполнены"))
        (not= password confirmpassword) (vr/show-registraion-page (assoc form-params :error "Пароли не совпадают"))
        (not (m/login-free? login)) (vr/show-registraion-page (assoc form-params :error "Логин занят")) 
        :else (do (m/registrate-user firstName lastName login (sha/sha256 password))
                  (vr/show-registraion-page {:error "Вы успешно зарегестрированы"})))))