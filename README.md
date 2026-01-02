# 🏥 MediManage: Smart Medical Billing & Inventory System

**MediManage** is a comprehensive pharmaceutical management solution designed to digitize medical stores. It combines robust billing features with **AI-powered prescription intelligence** to optimize stock management, ensure safe dispensing, and improve patient service.

---

## 🚀 Core Features

### 🛒 Intelligent Billing & POS

* **Automated Invoicing:** GST-compliant digital & printed bills
* **Batch Tracking (FIFO):** Automatically sells from the oldest batch first
* **Expiry Alerts:** Real-time notifications for near-expiry medicines

---

## 🧠 Advanced AI Features

### 🩺 AI Prescription-Aware Medicine Recommendation

* **Doctor-Prescription Driven Suggestions** (not blind generic swaps)
* Analyzes:

  * Prescribed brand / salt
  * Dosage & frequency
  * Patient age group
  * Stock availability & expiry
* **Decision Logic:**

  * Dispense exact medicine if available
  * Suggest *clinically equivalent* alternative if unavailable
  * Block unsafe substitutions (antibiotics, steroids, scheduled drugs)

> ⚠️ Improves patient safety and pharmacy compliance

---

### 🧾 AI Prescription OCR & NLP

* Upload **handwritten or printed prescriptions**
* Automatically extracts:

  * Medicine name
  * Dosage
  * Frequency
* Auto-fills billing cart with pharmacist confirmation

**Tech:** Tesseract / EasyOCR + spaCy NLP

---

### 📈 Predictive Inventory Forecasting

* Forecasts weekly/monthly demand using:

  * Historical sales
  * Seasonal illness trends
  * Local demand spikes
* Generates **auto purchase recommendations**

---

### 🔍 Intelligent Medicine Search

* AI fuzzy search supports:

  * Misspellings
  * Short forms (`PCM`, `AZI`, `CET`)
  * Salt-based queries

Example:

```
Input: paracetmol
Output: Paracetamol 500mg | Calpol | Crocin
```

---

### 🚨 AI Risk & Compliance Alerts

* Detects:

  * High-risk drug combinations
  * Overselling controlled medicines
  * High-demand + near-expiry conflicts
* Smart dashboard alerts for pharmacists

---

## 🏗️ AI Architecture

```
JavaFX UI
   |
   |-- REST / ProcessBuilder
   |
Python AI Engine
   |-- Prescription NLP
   |-- Medicine Recommendation
   |-- Demand Forecasting
   |
MySQL / SQLite
```

---

## 🛠️ Technical Stack

| Layer       | Technology                   |
| ----------- | ---------------------------- |
| Language    | Java 17+                     |
| UI          | JavaFX / Scene Builder       |
| Database    | MySQL / SQLite               |
| AI Engine   | Python (spaCy, scikit-learn) |
| OCR         | Tesseract / EasyOCR          |
| Integration | REST / ProcessBuilder        |
| Build Tool  | Maven                        |

---

## 📁 Project Structure

```text
├── src/main/java
│   ├── billing
│   ├── inventory
│   ├── prescription
│   └── ai_integration
├── src/main/resources
│   ├── fxml
│   └── styles
├── ai_modules
│   ├── prescription_parser.py
│   ├── medicine_recommender.py
│   └── demand_forecasting.py
├── database
│   └── schema.sql
└── README.md
```

---

## 🌟 Why MediManage Stands Out

✅ Prescription-safe AI recommendations
✅ Real pharmacy workflow aligned
✅ AI + JavaFX full-stack project
✅ Strong real-world & interview-ready system

---

📌 *MediManage is designed for modern pharmacies that value safety, intelligence, and efficiency.*
