(ns vk.model.model
  (:use [korma db core]))



;(def default-conn {:classname "com.mysql.jdbc.Driver"
;                   :subprotocol "mysql"
;                   :user "root"
;                   :password "gfhjkm"
;                   :subname "//127.0.0.1:3306/vk?useUnicode=true&characterEncoding=utf8"
;                   :delimiters "`"})

(def env (into {} (System/getenv)))

(def dbhost (get env "OPENSHIFT_MYSQL_DB_HOST"))
(def dbport (get env "OPENSHIFT_MYSQL_DB_PORT"))

(def default-conn {:classname "com.mysql.jdbc.Driver"
                   :subprotocol "mysql"
                   :user "admintSHbyPm"
                   :password "Fpasl5U3Ealy"
                   :subname (str "//" dbhost ":" dbport "/vk?useUnicode=true&characterEncoding=utf8")
                   :delimiters "`"})


(defdb korma-db default-conn)

(defentity users)

(defn create-user [user]
  (insert users (values user)))

(defn get-user[id]
  (first (select users (where {:id id}))))

(defn update-user [user]
  (update users
    (set-fields user)
    (where {:id (:id user)})))

(defn delete-user [id]
  (delete users 
    (where {:id id})))

(defentity logins)

(defn create-login [login-entry]
  (insert logins (values login-entry)))

(defn update-login [login-entry]
  (update logins
    (set-fields login-entry)
    (where {:id (:id login-entry)})))

(defn login-free? [new-login]
  (empty? (select logins
            (where {:login new-login})))) 

(defn authentication? [login password]
   (not (empty? (select logins
                  (where {:login login :password password}))))) 

(defn get-user-id [login password]
  (:users_id (first (select logins
                      (where {:login login :password password})))))

(defn registrate-user [first_name last_name login password]
    (create-login {:login login :password password :users_id (:GENERATED_KEY (create-user {:first_name first_name :last_name last_name}))})) 


(defentity frends)

(defn- insert-frends [from_user_id to_user_id]
  (insert frends (values {:from_user_id from_user_id :to_user_id to_user_id})))

(defn get-friends-for-user[user_id]
   (select users  
     (where {:id [in (subselect frends 
                       (fields :to_user_id)
                       (where {:from_user_id user_id}))]})))

(defn get-not-friends-for-user[user_id]
   (select users  
     (where {:id [not-in (subselect frends 
                       (fields :to_user_id)
                       (where {:from_user_id user_id}))]})
     (where {:id [not= user_id]})))

(defn delete-friends [from_user_id to_user_id]
  (delete frends
    (where (or 
             (and (= :from_user_id from_user_id) (= :to_user_id to_user_id))
             (and (= :from_user_id to_user_id) (= :to_user_id from_user_id))))))

(defn add-friends [from_user_id to_user_id]
  (do (insert-frends from_user_id to_user_id)
      (insert-frends to_user_id from_user_id)))

(defn is-friends [from_user_id to_user_id]
  (not (empty? (select frends
                 (where {:from_user_id from_user_id :to_user_id to_user_id})))))