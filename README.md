# 🛰️ Moonrider Identity Reconciliation System

A Spring Boot-based web service that solves the **"Identity Reconciliation"** challenge for **Moonrider x Zamazon** by linking customer identities that use different emails and phone numbers for each order — inspired by the curious case of **Doc Chandrashekar** and his time-travel adventures.

### 🎯 Goal

1. Build an `/identify` endpoint to accept contact data.
2. Detect overlaps across purchases using email/phone.
3. Maintain a **linked chain** of contact records.
4. Respond with a complete, **merged identity** that includes:
    - `primaryContactId`
    - All known `emails`
    - All known `phoneNumbers`
    - List of `secondaryContactIds`

---

## 🛠️ Tech Stack Used

| Layer           | Tech                        |
|-----------------|-----------------------------|
| Backend         | Java 22, Spring Boot        |
| Database        | MySQL                       |
| Testing         | Postman                     |
| Tooling         | IntelliJ IDEA, Git, Maven   |
| Version Control | Git + GitHub                |

---

## ⚙️ API Endpoint

### 🔹 POST `/identify`

#### 📥 Request:

```json
{
  "email": "doc@brown.com",
  "phoneNumber": "9876543210"
}

📌 Business Logic Summary
If no existing contact is found → create a new PRIMARY contact.

If a match is found on email or phone, and new info is introduced → create a SECONDARY contact linked to the oldest PRIMARY.

If both email and phone exist in different primaries, the later one becomes a SECONDARY and links to the older one — consolidation happens.

All linked contacts (primary + secondary) are returned in the response.

✅ How to Run the Project Locally
1️⃣ Prerequisites
Java 17+ (Java 22 preferred)

MySQL installed locally

IntelliJ IDEA or VS Code (optional)

Maven

2️⃣ Clone This Repo
Edit
git clone https://github.com/<your-username>/identity-reconciliation-service.git
cd identity-reconciliation-service