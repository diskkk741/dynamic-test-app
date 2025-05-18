# 📱 Dynamic Test App (Student Edition)

A full-stack adaptive test-taking system developed as a final-year project. This mobile app allows students to solve dynamically generated tests based on question difficulty, analyze their performance, and sync results when online. The backend is powered by Flask and supports MySQL as the main database, while the app also works fully offline using local SQLite.

---

## 📁 Project Structure

```
dynamic-test-app/
├── app/               # Android Studio project (Kotlin)
├── backend/           # Flask backend (app.py, config.py)
├── db/                # Offline JSON + MySQL schema
├── .gitignore
└── README.md
```

---

## ✨ Features

### ✅ Mobile App (Kotlin)
- Adaptive test generation by difficulty
- Offline test solving using local SQLite
- Instant feedback after each question
- Test history tracking
- Sync results to backend when online
- Internet/server availability check

### 🖥 Backend (Python + Flask)
- RESTful API with custom user ID support
- Basic POST/GET endpoints
- MySQL database integration
- Offline result sync endpoint

---

## 🧪 Technologies Used

- **Frontend:** Kotlin, Android Studio, SQLite
- **Backend:** Python 3, Flask, MySQLdb
- **Offline Storage:** `assets/questions.json` → local SQLite

---

## 📱 How to Run Android App

1. Open the project in **Android Studio**
2. Build & run on device/emulator
3. Offline questions are loaded from `assets/questions.json` on first launch
4. Results are stored locally and synced when network is detected

---

## 🔌 How to Run Flask Backend

> Python 3.10+ is recommended

```bash
cd backend/
python -m venv venv
venv\Scripts\activate   # or source venv/bin/activate
pip install -r requirements.txt
python app.py
```

Backend runs at: `http://localhost:5000`

---

## 🗃 Database Structure

- `test_results`: stores overall test score and timestamp
- `test_analytics`: stores performance per difficulty level
- SQLite DB (offline) mirrors this schema
- SQL dump and JSON data are in `db/` folder

---

## 🔄 Offline Sync Flow

1. User completes test while offline
2. Results saved to local SQLite DB
3. App detects connection + server
4. Sends results via `/api/sync-results`
5. Local record is deleted

---

## 🔐 API Endpoints (Summary)

| Method | Endpoint                     | Description                    |
|--------|------------------------------|--------------------------------|
| GET    | /api/questions               | Fetch questions                |
| POST   | /api/test-result             | Submit a test result           |
| GET    | /api/test-results/<user_id> | Get user’s test history        |
| POST   | /api/test-analytics          | Submit analytics               |
| POST   | /api/sync-results            | Sync offline results           |
| GET    | /ping                        | Ping server                    |

---

## 👨‍💻 Developer

**Alperen Özkılıç**  
Final Year Software Engineering Student  
GitHub: [github.com/diskkk741](https://github.com/diskkk741)

---

## 📜 License

This project is for educational/demo purposes only.
