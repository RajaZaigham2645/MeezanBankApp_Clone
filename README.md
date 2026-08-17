# 🏦 Meezan Bank App Clone

![Kotlin](https://img.shields.io/badge/Kotlin-1.9.0-purple.svg)
![Android](https://img.shields.io/badge/Android-API%2024%2B-green.svg)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-UI-blue.svg)
![Material 3](https://img.shields.io/badge/Material%203-Design-orange.svg)

A modern **Meezan Bank mobile application clone** built using **Kotlin** and **Jetpack Compose**.  
The application provides a modern banking experience with a premium **glass-morphism UI**, smooth navigation, money transfer features, beneficiary management, debit card management, transactions, payments, and other banking services.

> ⚠️ **Disclaimer:** This project is an educational/demo application and is not affiliated with, sponsored by, or officially connected to Meezan Bank.

---

## 📱 About The Project

**Meezan Bank App Clone** is an Android banking application developed to demonstrate modern mobile banking UI/UX and Android development practices.

The application focuses on creating a professional banking experience with:

- Modern glass-morphism UI
- Gradient backgrounds
- Smooth navigation
- Banking dashboard
- Money transfer functionality
- Beneficiary management
- Debit card interface
- Transaction history
- QR payment interface
- Bill payments
- Zakat and Sadqat services
- Additional banking services

The project is designed primarily as a **UI/UX and Android development project**.

---

## ✨ Features

### 🏠 Dashboard

- Personalized welcome screen
- Account balance display
- Show/Hide balance
- Quick action buttons
- Banking statistics
- Modern glass-morphism cards
- Gradient-based interface

### 💸 Money Transfer

- Send money
- Transfer money to existing beneficiaries
- Send money to a new beneficiary
- Enter account details
- Beneficiary management
- Transfer workflow

### 👤 Beneficiary Management

- Add new beneficiary
- Enter beneficiary account details
- Manage beneficiaries
- Select beneficiary for money transfer

### 💳 Debit Card

- Digital debit card interface
- Card information screen
- Card management interface
- Modern card design

### 📊 Transactions

- Recent transactions
- Transaction history
- Credit transactions
- Debit transactions
- Transaction grouping
- Today's transactions
- Previous transactions
- Bank statement interface

### 💰 Payments

- Bill payment interface
- Quick payments
- Utility payment interface
- Payment service screens

### 📷 QR Payments

- QR scanning interface
- QR-based payment workflow
- Camera-based scanning support

### 🕌 Islamic Services

- Zakat
- Sadqat
- Islamic banking focused services

### 🧭 Navigation

- Bottom navigation bar
- Offers
- Contact
- FAQ
- Qibla
- Products
- Floating location button
- Smooth screen navigation

---

## 🎨 UI/UX

The application uses a modern **glass-morphism design system**.

### Design Features

| Feature | Description |
|---|---|
| 🪟 Glass-morphism | Transparent cards with borders and layered surfaces |
| 🌈 Gradients | Purple and dark gradient backgrounds |
| 🔘 Rounded Cards | Modern rounded UI components |
| ✨ Glow Effects | Ambient background glow effects |
| 🎬 Animations | Smooth transitions and interaction feedback |
| 📱 Responsive UI | Designed for different Android screen sizes |
| 🎨 Material 3 | Modern Android design components |


### Color Palette


Primary     → #9B4DFF
Secondary   → #C24DFF
Background  → Dark Purple / Black
Cards       → Transparent / Dark Purple
Text        → White
Sub Text    → Translucent White

## 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| **Kotlin** | Main programming language |
| **Jetpack Compose** | UI development |
| **Material 3** | Modern UI components |
| **AndroidX** | Android libraries |
| **CameraX** | Camera functionality |
| **ML Kit** | QR code scanning |
| **Gradle Kotlin DSL** | Build system |
| **Android SDK** | Android application development |

---

## 📂 Project Structure

```text
MeezanBankApp_Clone/
│
├── app/
│   │
│   ├── src/
│   │   │
│   │   └── main/
│   │       │
│   │       ├── java/
│   │       │   └── com/example/meezan_bank_app/
│   │       │       │
│   │       │       ├── MainActivity.kt
│   │       │       ├── AddBenAccountDetailsActivity.kt
│   │       │       ├── AddBeneficiary.kt
│   │       │       ├── DebitcardActivity.kt
│   │       │       ├── EnterAccountDetailsActivity.kt
│   │       │       ├── SendMoneyActivity.kt
│   │       │       ├── SendToNewBen.kt
│   │       │       │
│   │       │       └── ui/
│   │       │           └── theme/
│   │       │
│   │       ├── res/
│   │       │   ├── drawable/
│   │       │   ├── mipmap/
│   │       │   └── values/
│   │       │
│   │       └── AndroidManifest.xml
│   │
│   └── build.gradle.kts
│
├── gradle/
│
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── gradlew
├── gradlew.bat
├── .gitignore
└── README.md



