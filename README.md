# 💸 PennyWise – Personal Finance Companion App

PennyWise is a modern Android application designed to help users track daily financial activities, monitor spending habits, and build better saving behavior through a clean and intuitive mobile experience.

---

## 🚀 Features (Current)

### 🔐 Authentication

* Google Sign-In using Firebase Authentication
* Secure login flow
* Automatic session handling
* Auto-login if user already signed in
* Auto logout after 7 days of inactivity

### 🎬 Splash Screen

* 2-second splash screen on app launch
* Smart navigation based on login state

### 🎨 UI/UX

* Modern fintech-inspired design
* Gradient-based authentication screen
* Clean typography and layout
* Responsive and minimal interface

---

## 🧱 Tech Stack

* **Language -** Kotlin
* **UI -** XML (ConstraintLayout)
* **Architecture -** MVVM (planned)
* **Authentication -** Firebase Auth (Google Sign-In)
---

## 📱 App Flow

```
App Launch
   ↓
Splash Screen (2s)
   ↓
Check login session
   ↓
Auth Screen (if not logged in)
   ↓
Google Sign-In
   ↓
Dashboard (MainActivity)
```

---

## 🛠️ Setup Instructions

### 1️⃣ Clone the Repository

```bash
git clone https://github.com/PriyanshuYadav08/Personal_Finance_App
cd Personal_Finance_App
```

---

### 2️⃣ Open in Android Studio

* Open Android Studio
* Click **Open Project**
* Select the cloned folder

---

### 3️⃣ Firebase Setup

#### 🔹 Step 1: Create Firebase Project

* Go to https://console.firebase.google.com/
* Create a new project

#### 🔹 Step 2: Add Android App

* Add package name: `com.example.personal_finance_app`
* Download `google-services.json`

#### 🔹 Step 3: Add `google-services.json`

Place it inside:

```
/app/google-services.json
```

---

### 4️⃣ Enable Google Sign-In

* Go to Firebase Console → Authentication → Sign-in Method
* Enable **Google Sign-In**

---

### 5️⃣ Add SHA-1 Key

Run this command in terminal:

```bash
./gradlew signingReport
```

Copy SHA-1 and paste it in Firebase Console.

---

### 6️⃣ Add Dependencies

In `app/build.gradle`:

```gradle
implementation 'com.google.android.gms:play-services-auth:20.7.0'
implementation 'com.google.firebase:firebase-auth:22.3.0'
```

---

### 7️⃣ Sync Project

Click:

```
Sync Now
```

---

## 🔐 Authentication Logic

* Google Sign-In via Firebase
* Stores login timestamp using SharedPreferences
* Valid session = 7 days
* After 7 days → auto logout

---

## 📌 Planned Features (Next Steps)

### 📊 Dashboard

* Current balance
* Total income & expenses
* Savings progress indicator
* Visual charts (Pie / Bar)

### 💳 Transaction Management

* Add, edit, delete transactions
* Category-based tracking
* Search & filter functionality

### 🎯 Savings Goal Feature

* Monthly savings goal
* Progress tracking
* Visual progress indicator

### 📈 Insights Screen

* Spending trends
* Category breakdown
* Weekly/monthly comparisons

### 🗄️ Data Handling

* Room Database (local storage)
* Offline-first architecture

---

## ✨ Future Enhancements

* Dark mode
* Notifications/reminders
* Data export
* Biometric authentication
* Cloud sync (optional)

---

## 📂 Project Structure (Planned)

```
com.example.personal_finance_app
│
├── ui/
├── viewmodel/
├── repository/
├── data/
│   ├── db/
│   ├── model/
│
└── utils/
```
---

## 👨‍💻 Author

**PRIYANSHU YADAV a.k.a. panther_yankee**

---

## ⭐️ If you like this project

Give it a star ⭐ on GitHub