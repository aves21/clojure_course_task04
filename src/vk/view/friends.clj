(ns vk.view.friends
  (:require [me.raynes.laser :as l]
            [clojure.java.io :refer [file]]
            [vk.view.page :as page]
            [vk.view.menu :as menu]))


(def friend-form (l/select page/main-html (l/id= "friendsFrag")))

(def friend-item (l/select page/main-html (l/id= "friendItem")))

(l/defragment friend-item-frag friend-item [{:keys [first_name last_name id]}]
  (l/id= "friendName") (l/content (if-not (and (nil? first_name) (nil? last_name)) (str first_name " " last_name) ""))
  (l/id= "showFriend") (l/attr :href (str "/main/" id))
  (l/id= "deleteFriend") (l/attr :href (str "/deletefriend/" id)))

(l/defragment friends-all-items-frag friend-form [friends]
   (l/id= "friendsMain") (l/content (for [friend friends]
                                      (friend-item-frag friend)))) 

(defn show-all-friends-page [friends]
   (l/document page/main-html
              (l/id= "menu")
              (l/content (menu/create-menu))
              (l/id= "main")
              (l/content (friends-all-items-frag friends))))

(def add-friend-form (l/select page/main-html (l/id= "addFriendsFrag")))

(def add-friend-item (l/select page/main-html (l/id= "addFriendItem")))

(l/defragment add-friend-item-frag add-friend-item [{:keys [first_name last_name id]}]
  (l/id= "friendName") (l/content (if-not (and (nil? first_name) (nil? last_name)) (str first_name " " last_name) ""))
  (l/id= "addFriend") (l/attr :href (str "/addFriends/" id)))

(l/defragment add-friends-all-items-frag add-friend-form [friends]
   (l/id= "addFriendsMain") (l/content (for [friend friends]
                                         (add-friend-item-frag friend)))) 

(defn show-add-friends-page [friends]
   (l/document page/main-html
              (l/id= "menu")
              (l/content (menu/create-menu))
              (l/id= "main")
              (l/content (add-friends-all-items-frag friends))))