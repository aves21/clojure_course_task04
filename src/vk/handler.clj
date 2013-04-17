(ns vk.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [vk.controller.login :as cl] 
            [vk.controller.registration :as cr]
            [vk.controller.main :as cm]
            [vk.controller.friends :as cf]
            [vk.model.model :as model]
            [ring.util.response :as resp]
            [noir.util.middleware :as noir]
            [noir.session :as session]))


(defmacro if-user-authorized [& body]
  `(if-not (nil? (session/get :user_id)) ~@body (resp/redirect "/login")))

(defmacro if-user-not-authorized [& body]
  `(if (nil? (session/get :user_id)) ~@body (resp/redirect "/main")))

(defmacro if-friends [from_user_id to_user_id & body]
  `(if (model/is-friends ~from_user_id ~to_user_id) ~@body (resp/redirect "/main")))


(defn pint [s-int]
  (Integer/parseInt s-int))


(defn modify-keys [mp] 
  (zipmap (map keyword (keys mp)) (vals mp)))

(defroutes app-routes
  
  (GET "/" [] (resp/redirect "/login"))
  
  ;; Show articles list
  (GET "/login" [] (if-user-not-authorized (cl/show-login-page)))
  
  (POST "/login" [login password] (if-user-not-authorized (cl/authentication login password))) 
  
  (GET "/registration" [] (if-user-not-authorized (cr/show-registraion-page))) 

  (POST "/registration" {form-params :form-params} (if-user-not-authorized (cr/registration (modify-keys form-params))))
  
  (GET "/main" [] (if-user-authorized (cm/show-main-page (session/get :user_id))))
  
  (GET "/exit" [] (if-user-authorized (cm/exit)))
  
  (GET "/main/:id" [id] (if-user-authorized (if-friends (session/get :user_id) (pint id) (cm/show-main-page (pint id)))))
  
  (GET "/allfriends" [] (if-user-authorized (cf/show-all-friends-page)))
  
  (GET "/deletefriend/:id" [id] (if-user-authorized (if-friends (session/get :user_id) (pint id) (cf/delete-friend (pint id)))))
  
  (GET "/addFriendsPage" [] (if-user-authorized (cf/show-add-friends-page)))
  
  (GET "/addFriends/:id" [id] (if-user-authorized (cf/add-friend (pint id))))
  
  (route/resources "/") 
  (route/not-found "Not Found"))

(def app
  (->
   [(handler/site app-routes)]
   noir/app-handler
   noir/war-handler
   ))



(comment
  ;; Function for inspecting java objects
  (use 'clojure.pprint)
  (defn show-methods [obj]
    (-> obj .getClass .getMethods vec pprint))
  
  )


