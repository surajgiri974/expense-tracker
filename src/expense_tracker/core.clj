(ns expense-tracker.core
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io])
  (:import (java.time LocalDateTime ZoneId)
          (java.time.format DateTimeFormatter)))

(def file-name "finance-data.edn")
(def finances (atom {:income [] :expenses []}))

(defn save-data []
  (spit file-name (pr-str @finances)))

(defn load-data []
  (when (.exists (io/file file-name))
    (reset! finances (edn/read-string (slurp file-name)))
    )
)

(defn current-timestamp []
  (.format (LocalDateTime/now (ZoneId/of "Asia/Kolkata"))
           (DateTimeFormatter/ofPattern "yyyy-MM-dd HH:mm:ss")))

(defn add-income []
  (println "Enter Income Source (e.g., Salary, Diwali Bonus etc.): ") (flush)
  (let [source (read-line)]
    (println "Enter Amount: ") (flush)
    (let [amount (Double/parseDouble (read-line))
          entry {:id (inc (count (:income @finances)))
                 :source source
                 :amount amount
                 :timestamp (current-timestamp)}]
      (swap! finances update :income conj entry)
      (save-data)
      (println "Income Added !!")
      )
    )
  )

(defn add-expense []
  (println "\nEnter Expense Category (e.g., Food, Travel, Rent etc.): ") (flush)
  (let [category (read-line)]
    (println "Enter Amount: ") (flush)
    (let [amount (Double/parseDouble (read-line))
          entry {:id (inc (count (:expenses @finances)))
                 :category category
                 :amount amount
                 :timestamp (current-timestamp)}]
      (swap! finances update :expenses conj entry)
      (save-data)
      (println "Expenses Added !!")
      )
    )
  )

(defn balance-sheet []
  (let [incomes (:income @finances)
        expenses (:expenses @finances)
        all-entries (->> (concat
                           (map #(assoc % :type "Cr") incomes)
                           (map #(assoc % :type "Dr") expenses))
                         (sort-by :id))
        total-income (reduce + (map :amount incomes))
        total-expense (reduce + (map :amount expenses))
        balance (- total-income total-expense)]

    ;; Header
    (println "-------------------------------------------- BALANCE SHEET ------------------------------------------")
    (println (format "%-5s %-20s %-8s %-10s %-10s %-20s %-12s"
                     "ID" "Description" "Type" "Cr" "Dr" "DateTime" "Balance"))
    (println "--------------------------------------------------------------------------------------------------")

    ;; Entries
    (if (empty? all-entries)
      (println "No Records found")
      (loop [entries all-entries
             running-balance 0.0]
        (when (seq entries)
          (let [entry (first entries)
                amount (:amount entry)
                new-balance (if (= (:type entry) "Cr")
                              (+ running-balance amount)
                              (- running-balance amount))
                cr-amt (if (= (:type entry) "Cr") (format "₹%.2f" (double amount)) "₹0")
                dr-amt (if (= (:type entry) "Dr") (format "₹%.2f" (double amount)) "₹0")]
            (println (format "%-5d %-20s %-8s %-10s %-10s %-20s ₹%-10.2f"
                             (:id entry)
                             (or (:source entry) (:category entry))
                             (:type entry)
                             cr-amt
                             dr-amt
                             (:timestamp entry)
                             (double new-balance)))
            (recur (rest entries) new-balance)))))

    ;; Totals
    (println "--------------------------------------------------------------------------------------------------")
    (println (format "%-35s %-10s %-10s %-20s ₹%-10.2f"
                     "TOTAL"
                     (format "₹%.2f" (double total-income))
                     (format "₹%.2f" (double total-expense))
                     ""
                     (double balance)))
    (println "==================================================================================================\n")))



(defn menu []
  (println "--------- Welcome to Expense Tracker -------")
  (println "1: Add Income")
  (println "2: Add Expense")
  (println "3: Balance Sheet")
  (println "4: Exit")
  (println "Enter Choice: ")
  (let [choice (read-line)]
    (case choice
      "1" (add-income)
      "2" (add-expense)
      "3" (balance-sheet)
      "4" (do (println "BYE !!") (System/exit 0))
       (println "Invalid Choice")
      )
    )
  (recur)
)

(defn -main []
  (load-data)
  (menu)
  )


