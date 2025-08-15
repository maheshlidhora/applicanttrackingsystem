# 📝 NRT-ATS (Applicant Tracking System)

**Developer:** Mahesh Prajapati  
**Organization:** NewRise Technosys  
**Tech Stack:** Spring Boot • Spring Security (JWT) • JPA/Hibernate • MySQL • Maven  

NRT-ATS is a **full-featured Applicant Tracking System** designed to streamline the recruitment process for HR managers, interviewers, and candidates.  
It offers **secure authentication**, **role-based access control**, **job postings**, **candidate applications**, **interview scheduling**, and **feedback management** — all in one platform.

---

## 📑 Table of Contents
1. [Features](#-features)
2. [Architecture](#-architecture)
3. [Modules](#-modules)
4. [Installation & Setup](#-installation--setup)


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



