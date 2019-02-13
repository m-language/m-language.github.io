(ns m-website.core
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [m-website.events :as events]
   [m-website.routes :as routes]
   [m-website.views :as views]
   [m-website.config :as config]
   [stylefy.core :as stylefy]
   ))


(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (routes/app-routes)
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (stylefy/init)
  (mount-root))
