# Payments Alert

Payments Alert is a web application that allows users to manage their recurring payments through an intuitive calendar-based interface. Built with Spring Boot on the backend and React on the frontend, it supports user authentication, CRUD operations for payments, and automatic scheduling based on recurrence (monthly, quarterly, yearly).

the project is available here:
http://paymentalertv-env.eba-ypp8k93w.eu-west-3.elasticbeanstalk.com/
---

## Tech Stack

### Backend:
- Java 17
- Spring Boot
- Spring Security (JWT)
- JPA (Hibernate)
- PostgreSQL

### Frontend:
- React
- Axios
- React Calendar

### Other:
- Docker & Docker Compose
- Nginx (for production reverse proxy)

---

## Project Structure

### Backend (`/backend`)
- **Controllers**: Handle API endpoints (e.g., `/users`, `/payments`).
- **Services**: Contain business logic (e.g., scheduling recurring payments, validating users).
- **Entities**: JPA models for `User` and `Payment`.
- **DTOs**: Used for data transfer between client and server.
- **Mappers**: Convert between DTOs and entities.
- **Repositories**: Interfaces extending `JpaRepository` for DB access.
- **Security Config**: JWT filter, CORS settings, and password encoding.
- **Util**: Helper classes like `JwtUtil` for token management.

### Frontend (`/frontend`)
- **Pages**: Components for views like calendar, login, register.
- **Components**: Reusable UI elements (forms, lists, etc).
- **Services/API**: Functions using Axios to communicate with the backend.
- **State Management**: Handled using React hooks and props.
- **Routing**: React Router for page navigation.

---

## Features

- User registration and login
- Token-based authentication
- Create, update, delete payments
- Mark payments as paid/unpaid
- View payments per user and per selected date
- Calculate monthly and yearly total amounts
- Support for recurring payments (monthly, quarterly, yearly)

---

## Docker Setup

### Prerequisites:
- Docker
- Docker Compose

### Launching the App:

```bash
docker-compose up --build
```

The setup includes:
- `backend` – Spring Boot application
- `frontend` – React application served via Nginx
- `db` – PostgreSQL database

---


## 🗂️ Environment Variables

**Backend (`application.properties` or `application.yml`):**
- `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`
- JWT secret can be stored securely with environment config or AWS Secrets Manager.

**Frontend (`.env`):**
```
REACT_APP_API_BASE_URL=/api
```

---

## Recurrence Logic

When creating a payment with a recurrence (monthly, quarterly, yearly), the backend automatically generates future payment entries based on the selected recurrence.

Example:
- A monthly payment starting on April 1st will generate 12 entries (April to March).

---

## Testing

You can use Postman or the frontend UI to test:

- POST `/login` – Login and retrieve token
- POST `/users` – Create new user
- GET `/users/me` – Get authenticated user
- POST `/payments` – Create payment
- GET `/payments/user/{id}/date?date=yyyy-mm-dd` – Fetch daily payments
- PATCH `/payments/{id}/status` – Mark payment as paid/unpaid

---


## Authentication Flow

1. **Login**  
   The user logs in by sending their email and password to the `/login` endpoint.

2. **Token Generation**  
   If the credentials are valid, the backend generates a **JWT (JSON Web Token)** that:
    - Contains the username as the subject.
    - Has a 30-minute expiration time.
    - Is signed with a secret key to prevent tampering.

3. **Token Usage**  
   The frontend stores the token (usually in `localStorage`) and attaches it to every request via the `Authorization` header

4. **Token Validation**  
   A custom filter (`JwtAuthenticationFilter`) intercepts all incoming requests:
- It checks for the token in the header.
- Validates the token’s signature and expiration.
- If valid, sets the authenticated user in the Spring Security context.

5. **Access Control**  
   In this setup, all endpoints are currently accessible to all users (`.anyRequest().permitAll()`), but it’s easy to adjust this to restrict access based on authentication or roles.

---

## 🔐 Password Encryption

Passwords are never stored in plain text. Instead, they are hashed using **BCrypt**, a strong one-way hashing algorithm. During login:
- The entered password is encoded and compared with the stored hashed version.
- If they match, the user is authenticated.

---

## 🌐 CORS Configuration

To allow communication between frontend (e.g., `localhost:3000`) and backend (Elastic Beanstalk), CORS is configured to:
- Allow multiple origins
- Accept all standard HTTP methods
- Include `Authorization` headers
- Allow credentials (cookies, auth headers)

---

## 🧱 Security Technologies Used

- **JWT (JSON Web Tokens)** – Stateless authentication
- **BCrypt** – Password hashing
- **Spring Security** – Authentication filters, security context, CSRF config
- **CORS Config** – Allow secure cross-origin requests

---

## 💡 Notes

- JWTs are stateless — no session is stored on the server.
- CSRF is disabled as it’s unnecessary with token-based authentication.
- CORS must be carefully configured to avoid security issues or blocked requests.

---

