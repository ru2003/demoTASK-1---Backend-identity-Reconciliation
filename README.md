# 🛰️ Moonrider Identity Reconciliation System
A Spring Boot-based web service that solves the **"Identity Reconciliation"** challenge for **Moonrider x Zamazon** by linking customer identities that use different emails and phone numbers for each order — inspired by the curious case of **Doc Chandrashekar** and his time-travel adventures.

Goal:
1. Build an `/identify` endpoint to accept contact data.
2. Detect overlaps across purchases using email/phone.
3. Maintain a **linked chain** of contact records.
4. Respond with a complete, **merged identity** that includes:
    - `primaryContactId`
    - All known `emails`
    - All known `phoneNumbers`
    - List of `secondaryContactIds`

## 🛠️ Tech Stack Used

| Layer     | Tech                        |
|-----------|-----------------------------|
| Backend   | Java 22, Spring Boot        |
| Database  | MySQL                       |
| Testing   | Postman                     |
| Tooling   | IntelliJ IDEA, Git, Maven   |
| Version Control | Git + GitHub          |


## ⚙️ API Endpoint

### POST `/identify`

#### Request:
```json
{
  "email": "doc@brown.com",
  "phoneNumber": "9876543210"
}


##📌 Business Logic Summary
If no existing contact is found → create a new PRIMARY contact.

If a match is found on email or phone, and new info is introduced → create a SECONDARY contact linked to the oldest PRIMARY.

If both email and phone exist in different primaries, the later one becomes a SECONDARY and links to the older one — consolidation happens.

All linked contacts (primary + secondary) are returned in the response.

##✅ How to Run the Project Locally
##1️⃣ Prerequisites
Java 17+ (Java 22 preferred)

MySQL installed locally

IntelliJ IDEA or VS Code (optional)

Maven


##2️⃣ Clone This Repo
git clone https://github.com/<your-username>/identity-reconciliation-service.git
cd identity-reconciliation-service

##3️⃣ Create MySQL Database
CREATE DATABASE contact_db;


##4️⃣ Update application.properties
In src/main/resources/application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/contact_db
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

##5️⃣ Run the App
Using terminal:
./mvnw spring-boot:run

Or from IntelliJ:
Open DemoApplication.java
Click ##▶️ Run


🧪 Postman Testing Guide
Test Case 1: New contact
{
  "email": "doc@future.com",
  "phoneNumber": "9999999999"
}
##➡️ Creates a new PRIMARY.

Test Case 2: Same email, new phone
{
  "email": "doc@future.com",
  "phoneNumber": "1234567890"
}
##➡️ Adds SECONDARY with new phone, linked to original contact.

Test Case 3: Same phone, different email
{
  "email": "flux@capacitor.com",
  "phoneNumber": "1234567890"
}
##➡️ Adds SECONDARY again if email wasn't known.


Test Case 4: Email + Phone that exist in different primaries
##➡️ Merge both under the older PRIMARY, adjust linkedId and linkPrecedence.

##❌ Invalid Case

{
  "email": null,
  "phoneNumber": null
}


##👩##‍💻 Developed by :
Ruchita Nandakishor Chaudhari
##🎓 Final Year Information Technology Student
##🚀 Backend Developer | Java + Spring Boot Enthusiast
##📫 Contact: ruchitanchaudhari145@email.com
##🔗 LinkedIn Profie : https://www.linkedin.com/in/ruchita-n-chaudhari-77a931253/