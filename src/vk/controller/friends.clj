(ns vk.controller.friends
   (:require [vk.view.friends :as vf]
             [vk.model.model :as m]
             [noir.session :as session]))


(defn show-all-friends-page []
  (vf/show-all-friends-page (m/get-friends-for-user (session/get :user_id))))

(defn delete-friend [id]
  (do (m/delete-friends id (session/get :user_id))
      (vf/show-all-friends-page (m/get-friends-for-user (session/get :user_id)))))

(defn show-add-friends-page []
  (vf/show-add-friends-page (m/get-not-friends-for-user (session/get :user_id))))

(defn add-friend [id]
  (do (m/add-friends id (session/get :user_id))
      (vf/show-add-friends-page (m/get-not-friends-for-user (session/get :user_id)))))