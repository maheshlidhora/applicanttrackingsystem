# 📝 NRT-ATS (Applicant Tracking System)

**Developer:** Mahesh Prajapati  
**Organization:** NewRise Technosys  
**Tech Stack:** Spring Boot • Spring Security (JWT) • JPA/Hibernate • MySQL • Maven  

NRT-ATS is a **full-featured Applicant Tracking System** designed to streamline the recruitment process for HR managers, interviewers, and candidates.  
It offers **secure authentication**, **role-based access control**, **job postings**, **candidate applications**, **interview scheduling**, and **feedback management** — all in one platform.

---

## 📂 Project Structure

applicanttrackingsystem/
├── src/main/java/com/newrise/applicanttrackingsystem
│ ├── config/ # Security & Data Initializer
│ ├── controllers/ # REST API controllers
│ ├── entities/ # JPA entities
│ ├── repositories/ # Spring Data JPA repositories
│ ├── servicesimpl/ # Service layer implementations
│ ├── security/ # JWT & authentication classes
│ └── ApplicantTrackingSystemApplication.java
├── src/main/resources/
│ ├── application.properties
│ └── static/ & templates/ (if any)
├── uploads/resumes/ # Uploaded resumes
├── pom.xml # Maven dependencies
├── Applicant Tracking System (ATS).pdf # Documentation
├── Project Work Flow.pdf # Workflow diagram
└── README.md

## 📑 Table of Contents
1. [Features](#-features)
2. [Architecture](#-architecture)
3. [Modules](#-modules)
4. [Project Structure](#-project-structure)
5. [Installation & Setup](#-installation--setup)
6. [API Endpoints](#-api-endpoints)
7. [Workflow](#-workflow)
8. [Known Issues](#-known-issues)
9. [License](#-license)

---

## 🚀 Features

| Module                         | Description |
|--------------------------------|-------------|
| **User Management**            | Register, login, enable/disable users, role validation (`Admin`, `HR Manager`, `Interviewer`, `Candidate`) |
| **Authentication**             | Secure JWT-based login, token blacklisting, and expiration handling |
| **Role-Based Access Control**  | Restricts endpoints based on roles |
| **Job Management**             | Create, update, delete, and view job postings |
| **Application Tracking**       | Apply for jobs, track application status, view all candidates |
| **Interview Management**       | Schedule, update, and track interviews |
| **Feedback System**             | Provide ratings and comments on candidates |
| **OTP Verification**           | Email-based OTP for verification |
| **File Uploads**               | Store and manage resumes securely |
| **Pagination & Filtering**     | Fetch users and jobs efficiently |

---

## 🏗 Architecture

**Backend:**  
- **Spring Boot** REST API  
- **Spring Security + JWT** for authentication  
- **MySQL** database with **JPA/Hibernate**  
- **BCrypt** for password hashing  

**Security Flow:**
1. User logs in → JWT generated → Sent in `Authorization` header  
2. Each request passes through `JwtFilter` for validation  
3. Token expiration & blacklist check  
4. Role-based method access via `@PreAuthorize`  

---

## 📦 Modules

### 1. Authentication
- **JWTService** for token generation/validation
- **CustomUserDetailsService** for loading user details
- **TokensRepository** for blacklisting

### 2. User Management
- **UsersController** for CRUD operations on users
- Validates roles during registration
- Supports enable/disable users

### 3. Job Management
- **Jobs** entity and endpoints for HR managers

### 4. Applications
- **JobApplications** entity to link candidate → job
- Tracks application status

### 5. Interviews
- **Interview** entity for scheduling
- Assigns HR and interviewer

### 6. Feedback
- **Feedback** entity for ratings and comments

---

---

## ⚙ Installation & Setup

### Prerequisites
- **Java 17+**
- **Maven 3.8+**
- **MySQL** database

### Steps

1. **Clone the repo**
```bash
git clone https://github.com/maheshlidhora/applicanttrackingsystem.git
cd applicanttrackingsystem




---

This now contains **everything** — your full installation & setup instructions, complete API documentation, workflow, and known issues — in a clean, professional GitHub format.  

If you want, I can also **embed diagrams** from your *Project Work Flow.pdf* and *ATS.pdf* into this README so it’s more visually appealing on GitHub. That would make it stand out to recruiters.


