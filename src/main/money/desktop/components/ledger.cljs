(ns money.desktop.components.ledger
  (:require [reagent-mui.components :as mui]))

(defn ledger [text]
  [mui/table-container {:component "div"}
   [mui/table {:sx #js {:min-width 650}
               :size "small"
               :aria-label "a dense table"}
    [mui/table-head
     [mui/table-row
      [mui/table-cell "Date"]
      [mui/table-cell "Description"]
      [mui/table-cell "Transfer"]
      [mui/table-cell {:align "right"} "Debit"]
      [mui/table-cell {:align "right"} "Credit"]
      [mui/table-cell {:align "right"} "Balance"]]]
    [mui/table-body
     [mui/table-row {:key 0}
                     ; :sx #js {"&:last-child td, &:last-child th" #js {:border 0}}}
      [mui/table-cell "12/31/20"]
      [mui/table-cell "Salary"]
      [mui/table-cell "Income:Salary"]
      [mui/table-cell {:align "right"} "5000.00"]
      [mui/table-cell {:align "right"}]
      [mui/table-cell {:align "right"} "5000.00"]]
     [mui/table-row {:key 1}
                     ; :sx #js {"&:last-child td, &:last-child th" #js {:border 0}}}
      [mui/table-cell "01/03/21"]
      [mui/table-cell "Grocerie shopping"]
      [mui/table-cell "Expenses:Groceries"]
      [mui/table-cell {:align "right"}]
      [mui/table-cell {:align "right"} "21.28"]
      [mui/table-cell {:align "right"} "4978.72"]]
     [mui/table-row {:key 2}
                     ; :sx #js {"&:last-child td, &:last-child th" #js {:border 0}}}
      [mui/table-cell "01/13/21"]
      [mui/table-cell "Cinema"]
      [mui/table-cell "Expenses:Entertainment"]
      [mui/table-cell {:align "right"}]
      [mui/table-cell {:align "right"} "32.00"]
      [mui/table-cell {:align "right"} "4946.72"]]
     ]]])
