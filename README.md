# 🛰️ Moonrider Identity Reconciliation System

A Spring Boot-based web service that solves the **"Identity Reconciliation"** challenge for **Moonrider x Zamazon** by linking customer identities that use different emails and phone numbers for each order — inspired by the curious case of **Doc Chandrashekar** and his time-travel adventures.

---

## 🕵️ Mission Context

> Doc Chandrashekar, stranded in 2023 and working on his time machine, continues to shop from Zamazon.com using different emails and phone numbers on each purchase to stay hidden.
>
> Moonrider is integrating a backend system into Zamazon to **recognize and link different contact identities belonging to the same individual**.

Your mission (which I accepted ✅):

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

| Layer     | Tech                        |
|-----------|-----------------------------|
| Backend   | Java 22, Spring Boot        |
| Database  | MySQL                       |
| Testing   | Postman                     |
| Tooling   | IntelliJ IDEA, Git, Maven   |
| Version Control | Git + GitHub          |

---

## ⚙️ API Endpoint

### POST `/identify`

#### Request:
```json
{
  "email": "doc@brown.com",
  "phoneNumber": "9876543210"
}

### Response:
{
  "primaryContactId": 1,
  "emails": [
    "doc@brown.com",
    "time@flux.com"
  ],
  "phoneNumbers": [
    "9876543210",
    "1234567890"
  ],
  "secondaryContactIds": [
    2, 4
  ]
}

# Business Logic Rules

1. When no matching contact exists:
   - Create new PRIMARY contact

2. When matching email or phone found:
   - Create SECONDARY contact linked to primary

3. When email and phone match different primaries:
   - Merge under the older primary contact

4. System always returns:
   - Complete contact chain (primary + secondaries)