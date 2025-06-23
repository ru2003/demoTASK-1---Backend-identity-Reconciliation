# Moonrider Identity Reconciliation System

A Spring Boot-based web service that solves the "Identity Reconciliation" challenge for Moonrider x Zamazon by linking customer identities that use different emails and phone numbers for each order - inspired by the curious case of Doc Chandrashekar and his time-travel adventures.

## Goal

1. Build an `/identify` endpoint to accept contact data
2. Detect overlaps across purchases using email/phone
3. Maintain a linked chain of contact records
4. Respond with a complete merged identity that includes:
    - Primary contact ID
    - All known emails
    - All known phone numbers
    - List of secondary contact IDs

## Tech Stack

- **Backend**: Java 22, Spring Boot
- **Database**: MySQL
- **Testing**: Postman
- **Tooling**: IntelliJ IDEA, Git, Maven
- **Version Control**: Git + GitHub

## API Endpoint

**POST /identify**

Request format:
```json
{
  "email": "doc@brown.com",
  "phoneNumber": "9876543210"
}
Business Logic
If no existing contact → create new PRIMARY contact

If match found on email/phone with new info → create SECONDARY contact linked to oldest PRIMARY

If both email and phone exist in different primaries → merge under older PRIMARY

Returns all linked contacts (primary + secondary)

How to Run
Prerequisites
Java 17+

MySQL

Maven

Setup
Clone repository:

bash
git clone https://github.com/<your-username>/identity-reconciliation-service.git
cd identity-reconciliation-service
Create database:

sql
CREATE DATABASE contact_db;
Configure application.properties:

text
spring.datasource.url=jdbc:mysql://localhost:3306/contact_db
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
Run application:

bash
./mvnw spring-boot:run
Testing Examples
New contact:

json
{
  "email": "doc@future.com",
  "phoneNumber": "9999999999"
}
Same email, new phone:

json
{
  "email": "doc@future.com",
  "phoneNumber": "1234567890"
}
Invalid case:

json
{
  "email": null,
  "phoneNumber": null
}
Developer
Ruchita Nandakishor Chaudhari
Final Year Information Technology Student
Backend Developer | Java + Spring Boot Enthusiast
Contact: ruchitanchaudhari145@email.com