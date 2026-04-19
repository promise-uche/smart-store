# SmartStore 🛒
**Java Capstone Project — ICT Training Academy | Week 4**

A menu-driven console application simulating an online store, built using core Java concepts covered across the 4-week module.

---

## Features

| Feature | Description |
|---|---|
| Browse products | View full catalog with stock levels and pricing |
| Search | Find products by name or category |
| Shopping cart | Add, update, and remove items; see running total |
| Checkout | Enter customer details, confirm order, generate receipt |
| File persistence | Inventory auto-saves on every change; reloads on startup |
| Admin panel | Add/remove products, update stock, view order history |
| Input validation | All inputs validated — app never crashes on bad input |
| VAT calculation | 7.5% VAT applied automatically at checkout |

---

## Project Structure

```
SmartStore/
├── src/
│   └── com/smartstore/
│       ├── Main.java                   ← Entry point
│       ├── models/
│       │   ├── Product.java            ← Product entity (2 constructors, encapsulation)
│       │   ├── Customer.java           ← Customer entity (validation in setters)
│       │   ├── CartItem.java           ← Cart line item (product + quantity)
│       │   └── Order.java              ← Completed order with receipt generation
│       ├── services/
│       │   └── StoreService.java       ← All business logic (ArrayList, checkout flow)
│       └── utils/
│           ├── ConsoleUI.java          ← All I/O and input validation (try-catch)
│           └── FileManager.java        ← File read/write (BufferedReader/Writer)
├── data/
│   └── inventory.txt                  ← Product catalog (pipe-delimited, persisted)
├── receipts/
│   └── ORD-XXXX.txt                   ← Generated per order at checkout
├── tests/
│   └── ProductTest.java               ← Manual unit tests for Product class
└── docs/
    └── rubric.md                       ← Evaluation rubric and submission checklist
```

---

## How to Run

### Option 1 — Replit (recommended for class)
1. Fork the starter Replit project shared by your instructor
2. Click **Run** — no setup needed

### Option 2 — Local (IntelliJ / VS Code)
```bash
# Compile from the src/ directory
javac -d out src/com/smartstore/**/*.java src/com/smartstore/*.java

# Run from project root
java -cp out com.smartstore.Main
```

### Option 3 — Command line (bare javac)
```bash
cd SmartStore
javac src/com/smartstore/models/*.java src/com/smartstore/utils/*.java src/com/smartstore/services/*.java src/com/smartstore/Main.java -d out
java -cp out com.smartstore.Main
```

---

## Running Tests
```bash
# Compile ProductTest (after compiling main src)
javac -cp out tests/ProductTest.java -d out

# Run
java -cp out com.smartstore.tests.ProductTest
```

---

## OOP Concepts Demonstrated

| Concept | Where |
|---|---|
| **Classes & Objects** | Product, Customer, CartItem, Order, StoreService |
| **Encapsulation** | All fields private; accessed via getters/setters |
| **Constructor overloading** | Product (2 constructors), Customer (2 constructors) |
| **ArrayList** | `catalog`, `cart`, `orders` in StoreService |
| **File I/O** | FileManager — BufferedReader/Writer with try-with-resources |
| **Exception handling** | ConsoleUI input loops, StoreService stock validation |
| **try-catch** | FileManager, ConsoleUI.getIntInput(), Customer.setEmail() |
| **Static methods** | FileManager.readLines(), FileManager.writeLines() |

---

## Evaluation Rubric Summary

| Criterion | Weight | Your Target |
|---|---|---|
| OOP implementation | 30 pts | All 4+ classes, constructors, encapsulation |
| Functionality | 25 pts | All 6 features working |
| Code quality | 20 pts | Clear naming, comments, structure |
| Exception handling | 15 pts | No crashes on bad input |
| Presentation | 10 pts | Explain 3 design decisions verbally |
| **TOTAL** | **100 pts** | **Pass: 65/100** |

---

## Author

**Name:** Promise Onyemaechi
**Date:** April 20, 2026
**GitHub:** https://github.com/promise-uche/smart-store.git
