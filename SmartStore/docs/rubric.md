# SmartStore — Evaluation Rubric & Submission Checklist

## Evaluation Criteria (100 points)

### 1. OOP Implementation — 30 points
| Mark | Criteria |
|---|---|
| 25–30 | 3+ classes, all fields private, correct constructors, overloading used meaningfully |
| 18–24 | 3+ classes present but minor encapsulation issues (e.g. some public fields) |
| 10–17 | Classes exist but constructor or getter/setter logic is missing or incorrect |
| 0–9   | Single class, no OOP structure applied |

### 2. Functionality — 25 points
| Mark | Criteria                                       |
|---|------------------------------------------------|
| 22–25 | All 6 features work correctly during live demo |
| 16–21 | 4–5 features work; minor bugs in one area      |
| 8–15  | 2–3 features work; significant gaps            |
| 0–7   | Fewer than 2 features functional.              |

### 3. Code Quality & Readability — 20 points
| Mark | Criteria |
|---|---|
| 17–20 | Meaningful names, consistent indentation, comments on all key methods |
| 13–16 | Mostly clear; minor naming or formatting issues |
| 7–12  | Some meaningful names but inconsistent style throughout |
| 0–6   | Unclear names, no comments, hard to follow |

### 4. Exception Handling & Input Validation — 15 points
| Mark | Criteria |
|---|---|
| 13–15 | try-catch used correctly; all invalid inputs handled gracefully |
| 10–12 | try-catch present but one input path can crash the app |
| 5–9   | Minimal handling; multiple crash points |
| 0–4   | No exception handling; app crashes on bad input |

### 5. Presentation & Explanation — 10 points
| Mark | Criteria |
|---|---|
| 9–10 | Runs demo confidently; explains 3+ design decisions clearly |
| 6–8  | Demo runs; explains basic what the code does |
| 3–5  | Demo has issues; struggles to explain decisions |
| 0–2  | Unable to demo or explain the project |

---

## Pass Threshold: 65 / 100
Learners scoring 50–64 may submit one revised version within 5 working days.

---

## Submission Checklist

Complete every item below before submitting:

- [ ] Project compiles and runs without errors
- [ ] `Main.java` is the entry point
- [ ] At least 3 classes created (Product, Customer, Store or equivalent)
- [ ] All fields are `private` with getters and setters
- [ ] At least one class uses constructor overloading
- [ ] `ArrayList` is used to manage products
- [ ] User input is handled via `Scanner`
- [ ] At least one `try-catch` block is present
- [ ] Inventory loads from `data/inventory.txt` on startup
- [ ] Inventory saves to `data/inventory.txt` on exit or change
- [ ] Receipts are written to the `receipts/` folder
- [ ] `README.md` is complete (your name, GitHub link, how to run)
- [ ] Code is pushed to a **public** GitHub repository
- [ ] You can run a live demo in front of the class

---

## Submission Format

**GitHub link:** Submit your public repository URL via the class group chat or Replit invite link.

**Live demo:** Be ready to run your program, walk through a full purchase (browse → add to cart → checkout), and answer 2–3 questions about your code design.

**Date:** Week 4, Day 5 — during the final session.
