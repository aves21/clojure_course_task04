(ns vk.view.menu
  (:require [me.raynes.laser :as l]))



(l/defragment menu-item "<li></li>" [{:keys [link title]}]
  (l/element= :li) (l/content (l/node :a :attrs {:href link} :content title)))
  
  
(def menu-items [{:link "/main" :title "Моя страница"}
                 {:link "/allfriends" :title "Мои друзья"}
                 {:link "/addFriendsPage" :title "Добавить друзей"}
                 {:link "/exit" :title "Выход"}])  


(defn create-menu []
  (for [item menu-items]
     (menu-item item)))