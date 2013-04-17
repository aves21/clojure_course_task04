(ns vk.controller.login
    (:require [vk.view.login :as vl]
              [vk.model.model :as m]
              [vk.model.sha :as sha]
              [noir.session :as session]
              [ring.util.response :as resp]))

(defn show-login-page []
  (vl/show-login-page {}))

(defn authentication [login password]
  (if-not (m/authentication? login (sha/sha256 password))
    (vl/show-login-page {:error "Логин или пароль не верен"})
    (do (session/put! :user_id (m/get-user-id login (sha/sha256 password)))
        (resp/redirect "/main")) ))