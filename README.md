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

Response:
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

Business Logic Summary
If no existing contact is found → create a new PRIMARY contact.

If a match is found on email or phone, and new info is introduced → create a SECONDARY contact linked to the oldest PRIMARY.

If both email and phone exist in different primaries, the later one becomes a SECONDARY and links to the older one — consolidation happens.

All linked contacts (primary + secondary) are returned in the response.