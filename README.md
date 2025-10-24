# Expense Tracker

A simple Clojure-based CLI expense tracker to manage personal finances.

````markdown
# Expense Tracker (Clojure)

A simple Clojure-based CLI expense tracker to manage personal finances.  

---

## Features

- Add **income** and **expense** entries with descriptions and amounts.  
- View a **detailed balance sheet** showing:  
  - Transaction ID  
  - Description  
  - Type (Credit / Debit)  
  - Cr / Dr Amount  
  - Date and Time  
  - Running Balance  
- Stores data in memory using Clojure **atoms**.  
- Lightweight, terminal-based, and perfect for learning Clojure data structures and I/O.  

---

## Installation

1. **Clone the repository**

```bash
git clone https://github.com/yourusername/expense-tracker-clojure.git
cd expense-tracker-clojure
````

2. **Run the project using Leiningen**

```bash
lein run
```

> Make sure you have [Clojure](https://clojure.org/guides/getting_started) and [Leiningen](https://leiningen.org/) installed.

---

## Usage

1. Launch the program with:

```bash
  lein run
```

2. The menu options:

```
1: Add Income
2: Add Expense
3: Balance Sheet
4: Exit
```

3. Enter your choice, provide the requested information, and view your **running balance sheet**.

---

## Example Balance Sheet

```
------------------------------------ BALANCE SHEET ------------------------------------
ID    Description         Type     Cr         Dr         DateTime             Balance
--------------------------------------------------------------------------------------
1     Salary              Cr       ₹50000.00             2025-10-23 19:47:15 ₹50000.00
2     Rent                Dr                   ₹12000.00 2025-10-23 19:55:03 ₹38000.00
3     Freelance           Cr       ₹15000.00             2025-10-23 20:05:12 ₹53000.00
--------------------------------------------------------------------------------------
TOTAL                              ₹65000.00   ₹12000.00                     ₹53000.00
======================================================================================
```

---

## Data Storage

* Uses **Clojure atoms** for in-memory storage.
* Optional EDN file persistence can be added to save/load data between sessions.

---

## License

Copyright © 2025 Suraj Giri

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
https://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
