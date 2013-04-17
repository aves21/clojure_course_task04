(ns vk.controller.main
  (:require [vk.view.main :as vm]
            [vk.model.model :as m]
            [noir.session :as session]
            [ring.util.response :as resp]))

(defn show-main-page [user_id]
  (let [user ( m/get-user user_id)]
    (vm/show-main-page user)))


(defn exit []
  (do (session/remove! :user_id)
      (resp/redirect "/login")))