(ns m-website.views
  (:require
   [re-frame.core :as re-frame]
   [m-website.subs :as subs]
   [stylefy.core :refer [use-style] :as stylefy]
   [clojure.core.reducers :refer [reduce]]
   ))


(defn styles [& stylecoll]
  (reduce merge {} stylecoll))

(def m-purple "#a23fb8")
(def section-style {:padding "30px 0px" :margin :auto})

;; home

(def full {:margin :auto :height "100%"})

(def wrap {:max-width "960px" :margin :auto})

(def centered {:position "relative"
               :left "50%"
               :top "50%"
               :transform "translate(-50%, -50%)"})

(def nopad {:margin "0" :padding "0"})

(def logo-style
  (styles centered {:height "200px"
                    :width "200px"
                    :background-image "url(\"images/log.svg\")"
                    :background-size "cover"
                    :background-position "50% 50%"}))

(def header-style
  (styles full {:background-image "url(\"images/linebg.svg\")"
                :background-size "cover"
                :position "relative"
                :max-height "800px"
                :min-height "500px"
                :height "700px"}))

(def tagline "A Minimal Lisp")

(defn header []
  [:div (use-style header-style)
   [:div (use-style logo-style)
   [:div (use-style {:font-weight 800
                     :font-size "1.2em"
                     :color "#6a6a6c"
                     :padding-top "110%"
                     :text-align :center}) tagline]]])

(def card-style {:padding "10px"})

(defn card [style & children]
    [:div (use-style (styles style card-style)) name
     (doall (for [item children] ^{:key item} [:div (use-style {:height "100%"}) item]))])

(defn heading [text] [:h1 (use-style {:margin 0}) text])

(def about-style (styles section-style
                        {:background-color m-purple
                         :color :white
                         ::stylefy/manual [[:.row {:margin-bottom "20px"}]]}))

(def math-example-str "# A function which ignores its second argument.
(def true (fn [x y] x))

# A function which ignores its first argument.
(def false (fn [x y] y))

# True if both arguments are true.
(def and
  (fn [x y]
    (x y false)))")

(def math-description
  "M leverages ideas from Alonzo Church's Lambda Calculus to reduce language
   complexity and provide an extremely pure language.")

(def abstract-example-str "# A person data type.
(defdata (person name age))

# Generates json functions at compile time.
(defjson person)

# Writes {\"name\":\"James\",\"age\":21}.
(write-line stdout
  (to-json (person \"James\" 21)))")

(def abstract-description
  "M's powerful macro system allows the definition of anything from
  control flow to type systems to generic DSLs.")

(def functional-example-str "# A simple echo program which logs its output.
(defn (echo log)
  (let-cont line (read-line stdin)
    (do {
      (run-async 
        (write-line stdout line)
        (write-line log line))
      (echo log)}))

(echo (file->ostream \"log.txt\"))")

(def functional-description
  "M isolates side effects using processes to allow for simple reasoning about
   programs for both you and the compiler.")

(def performance-example-str "# A factorial function parameterized over a numeric module.
(defn (factorial num x)
  (import num
    ((eq x zero) one
      (mul x (factorial num (sub x one))))))

# The factorial function specialized for 32 bit integers.
(def factorial-int32 (factorial int32))")

(def performance-description
  "M's simplicity means that the compiler and its optimizations are
  fast and don't degrade when abstractions are introduced.")

(defn about []
  [:div (use-style about-style)
   [:div.container-fluid  (use-style {:max-width "960px" :margin :auto})
    ;; -------------------------------------------------
    [:div.row (use-style {:width "100%" :margin :auto})
     [:div.col-xs-12.col-sm-5.col-md-6
      [card {}
       [heading "Mathematically Inspired"]
       [:p (use-style {:font-weight 600 :opacity 0.9}) math-description]]]
     [:div.col-xs-12.col-sm-7.col-md-6 (use-style {:margin :auto :display "flex"})
      [:pre.prettyprint.lang-lisp (use-style (styles nopad {:overflow-x :auto}))
        math-example-str]]]
    ;; -------------------------------------------------
    [:div.row (use-style {:width "100%" :margin :auto :flex-direction :row-reverse})
     [:div.col-xs-12.col-sm-5.col-md-6
      [card {}
       [heading "Structurally Abstract"]
       [:p (use-style {:font-weight 600 :opacity 0.9}) abstract-description]]]
     [:div.col-xs-12.col-sm-7.col-md-6 (use-style {:margin :auto :display "flex"})
      [:pre.prettyprint.lang-lisp (use-style (styles nopad {:overflow-x :auto}))
       abstract-example-str]]]
    ;; -------------------------------------------------
    [:div.row (use-style {:width "100%" :margin :auto})
     [:div.col-xs-12.col-sm-5.col-md-6
      [card {}
       [heading "Purely Functional"]
       [:p (use-style {:font-weight 600 :opacity 0.9}) functional-description]]]
     [:div.col-xs-12.col-sm-7.col-md-6 (use-style {:margin :auto :display "flex"})
      [:pre.prettyprint.lang-lisp (use-style (styles nopad {:overflow-x :auto}))
       functional-example-str]]]
    ;; -------------------------------------------------
    [:div.row (use-style {:width "100%" :margin :auto :flex-direction :row-reverse})
     [:div.col-xs-12.col-sm-5.col-md-6
      [card {}
       [heading "High Performance"]
       [:p (use-style {:font-weight 600 :opacity 0.9}) performance-description]]]
     [:div.col-xs-12.col-sm-7.col-md-6 (use-style {:margin :auto :display "flex"})
      [:pre.prettyprint.lang-lisp (use-style (styles nopad {:overflow-x :auto}))
       performance-example-str]]]
    ]])


(def backends-style
  (styles section-style
          {:background-color "rgba(162, 63, 184, 0.88)"
           :color :ghostwhite}))

(defn backend-icon [icon caption]
   [:div.col-xs-12.col-sm-4 (use-style {:font-size :80px :text-align :center})
    icon
    [:div (use-style {:font-size :18px
                      :font-weight 800
                      :text-align :center
                      :margin :auto}) caption]])

(defn backends []
  [:div (use-style backends-style)
   [:div.container-fluid
    [:div.row (use-style {:text-align :center})
     [:div.col-xs-12
      [heading "Cross Platform Support"]]]
    [:div.row (use-style {:padding "30px 0" :max-width :960px :margin :auto})
      [backend-icon [:i.fab.fa-java] "JVM"]
      [backend-icon [:i.fab.fa-js] "Javascript"]
      [backend-icon [:i.fas.fa-microchip] "Native"]]]])

(def editors-style (styles section-style
                           {:background-color "#3a3a3c"
                            :color :ghostwhite}))

(defn editor-icon [img caption repo]
  [:div (use-style {:text-align :center :width "100%"})
    [:div (use-style {:max-width :100px
                      :max-height :100px
                      :height :100px
                      :width "100%"
                      :margin :auto
                      :margin-bottom :10px
                      })
     [:img {:src img :height "100%"}]]
    [:a (use-style {:color :ghostwhite :text-decoration :none} {:href repo})
     [:div [:i.fab.fa-github] [:span (use-style {:margin-left :5px}) caption]]]])

(defn editors []
  [:div (use-style editors-style)
   [:div.container-fluid (use-style wrap)
    [:div (use-style {:text-align :center :margin-bottom :30px}) [heading "Editor Friendly"]]
    [:div.row
     [:div.col-xs-12.col-sm-3
      [card {}
       [editor-icon "images/intellij.png" "m-language/intellij-m" "https://github.com/m-language/intellij-m"]]]
     [:div.col-xs-12.col-sm-3
      [card {}
       [editor-icon "images/vscode.png" "m-language/vscode-m" "https://github.com/m-language/vscode-m"]]]
     [:div.col-xs-12.col-sm-3
      [card {}
       [editor-icon "images/vim.png" "m-language/vim-m" "https://github.com/m-language/vim-m"]]]
     [:div.col-xs-12.col-sm-3
      [card {}
       [editor-icon "images/sublime.png" "stuin/m-sublime" "https://github.com/stuin/M-Sublime"]]]
     ]]])

(def spec-style (styles section-style
                        {:background-color "#2a2a2c"
                         :color :ghostwhite}))

(def learn-style (styles section-style
                        {:background-color "#2980B9"
                         :color :ghostwhite}))

(defn spec []
  [:div (use-style spec-style)
   [:div.container-fluid (use-style wrap)
    [:div.row
     [:div.col-xs-12.col-sm-7
      [card {}
       [heading "Formally Specified"]
       [:p (use-style {:font-weight 600 :opacity 0.9})
        "M is developed according to a simple mathematical specification,
        meaning that there will be no surprises during program execution."]
       [:div
        [:a (use-style {:color :lightgrey :text-decoration :none}
                       {:href "https://m-language.github.io/m-spec/m.html"})
         "Read It Here"]]]]
     [:div.col-xs-12.col-sm-5 (use-style {:display "flex"})
      [card {:color "ghostwhite"
             :font-family "IBM Plex Mono"
             :min-width 0}
       [:div (use-style {:margin :auto :width "200px" :max-width "80vw"})
       [:img {:src "images/spec.png" :width "100%"}]]]]]
    ]])

(defn readthedocs []
  [:div (use-style learn-style)
   [:div.container-fluid (use-style wrap)
    [:div.row
     [:div.col-xs-12.col-sm-12
      [card {}
       [:h1 (use-style {:text-align :center :font-size "3.0em" :margin "15vh auto"})
        [:a (use-style {:color :lightgrey :text-decoration :none}
                       {:href "https://m-language.readthedocs.io/"})
         "Read The Docs"]]]]]]])

(defn home-panel []
  [:div
   [header]
   [about]
  ;  [backends]
   [editors]
;   [spec]
   [readthedocs]])

(def main-panel home-panel)
