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
