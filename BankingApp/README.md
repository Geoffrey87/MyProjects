# BankingApp

**Technical Documentation** — Academic Portfolio Project — 2026

---

## Table of Contents

1. [Introduction](#1-introduction)
2. [System Architecture](#2-system-architecture)
3. [Database](#3-database)
4. [Backend (API)](#4-backend-api)
5. [Frontend (Angular)](#5-frontend-angular)
6. [Docker & Deployment](#6-docker--deployment)
7. [Security](#7-security)
8. [Local Installation Guide](#8-local-installation-guide)

---

## 1. Introduction

### 1.1 Project Description

BankingApp is a digital banking web application developed as a portfolio project. The application simulates the core functionalities of a modern banking system, enabling users to manage accounts, cards, transfers, loans, and notifications through an intuitive and responsive interface.

The project was built with a strong focus on software engineering best practices, separation of concerns, security, and user experience.

### 1.2 Objectives

- Build a full-stack application using .NET and Angular
- Implement authentication and authorization with JWT
- Apply architectural patterns such as Onion Architecture, Repository Pattern and Service Layer
- Follow SOLID principles throughout the codebase
- Containerize the application with Docker
- Deploy to a cloud platform (Fly.io)
- Ensure responsiveness across mobile and desktop devices

### 1.3 Technologies Used

| Layer | Technology | Version |
|---|---|---|
| Backend | .NET / ASP.NET Core | 10.0 |
| ORM | Entity Framework Core | 10.0 |
| Database | PostgreSQL | 17 |
| Frontend | Angular | 19 |
| CSS Framework | Tailwind CSS | 3.x |
| Containerization | Docker / Docker Compose | Latest |
| Deployment | Fly.io | — |
| Authentication | JWT (JSON Web Tokens) | — |
| API Documentation | Swagger / OpenAPI | — |

---

## 2. System Architecture

### 2.1 Overview

BankingApp follows the Onion Architecture pattern combined with a layered approach, ensuring a clean separation of concerns and high maintainability. The frontend Angular SPA communicates with the .NET REST API over HTTP, using JWT for authentication. The API persists data in a PostgreSQL database managed through Entity Framework Core.

### 2.2 Onion Architecture

The Onion Architecture organizes the codebase into concentric layers where dependencies always point inward — outer layers depend on inner layers, but never the reverse. This ensures that the core business logic is completely decoupled from infrastructure concerns such as databases or HTTP frameworks.

| Layer (outer to inner) | Responsibility |
|---|---|
| Infrastructure | Controllers, EF Core repositories, external services, configuration |
| Application | Service implementations, DTOs, AutoMapper profiles, use case orchestration |
| Domain | Entities, enums, repository interfaces, service interfaces |
| Core | Business rules, domain logic — no external dependencies |

The key benefit of this approach is that the Domain and Core layers have zero knowledge of how data is stored or how the API is exposed. They define contracts (interfaces) that outer layers implement, enabling easy testing and future replacement of infrastructure components.

### 2.3 Architectural Patterns

#### Repository Pattern

Each entity has its own repository interface (`IUserRepository`, `IAccountRepository`, etc.) defined in the Domain layer, with concrete implementations in the Infrastructure layer. This abstraction decouples business logic from data access technology and makes unit testing straightforward through mock implementations.

#### Service Layer

Services encapsulate all business logic and orchestrate operations across multiple repositories. Controllers are kept thin — they only delegate to services, parse HTTP input, and return HTTP responses. Business rules never leak into controllers.

#### DTO Pattern

Data Transfer Objects (DTOs) are used to separate the domain model from its external representation. `RequestDtos` define what the API accepts as input, and `ResponseDtos` define what it returns as output. This prevents accidental exposure of sensitive fields and decouples the API contract from internal entity changes.

#### Dependency Injection

All services and repositories are registered in the ASP.NET Core DI container with a Scoped lifetime, ensuring one instance per HTTP request. This promotes loose coupling and is the foundation for applying the Dependency Inversion Principle.

### 2.4 SOLID Principles

The codebase was designed following the five SOLID principles of object-oriented design.

#### S — Single Responsibility Principle

Each class has one and only one reason to change. `AuthService` is exclusively responsible for authentication logic (register, login, token generation). `UserService` handles user CRUD operations. `CardService` manages card lifecycle. None of these classes mix concerns — a service that validates passwords does not also send notifications.

#### O — Open/Closed Principle

Classes are open for extension but closed for modification. New repository implementations can be added without modifying existing service code. For instance, adding a new notification channel would require implementing the existing `INotificationService` interface rather than modifying existing notification logic.

#### L — Liskov Substitution Principle

Any concrete repository implementation can replace its interface without breaking the application. For example, `UserRepository` implements `IUserRepository` — if a different storage mechanism were used (e.g., a caching layer), it would implement the same interface and the service layer would work identically without any changes.

#### I — Interface Segregation Principle

Interfaces are kept focused and cohesive. `IUserRepository` only contains user-related data access methods. `ICardRepository` only contains card-related operations. No class is forced to implement methods it does not need. Each feature has its own dedicated service interface (`IAuthService`, `IAccountService`, `ICardService`, etc.).

#### D — Dependency Inversion Principle

High-level modules (services) do not depend on low-level modules (repositories). Both depend on abstractions (interfaces). For example, `TransactionService` depends on `ITransactionRepository` and `IAccountRepository` — not on the concrete EF Core implementations. This is enforced through constructor injection, making the dependency graph explicit and testable.

### 2.5 Folder Structure — Backend

```
BankingApp.API/
├── Controllers/       — HTTP endpoints (thin controllers)
├── Data/              — DbContext and DataSeeder
├── DTOs/              — Request and Response DTOs
├── Entities/          — Domain models
├── Enums/             — Enumerations
├── Exceptions/        — Custom exception classes
├── Migrations/        — EF Core migrations
├── Repositories/      — Interfaces and implementations
├── Services/          — Business logic layer
└── Program.cs         — Application entry point and configuration
```

### 2.6 Folder Structure — Frontend

```
BankingApp.Web/src/app/
├── core/              — Guards, interceptors, models
├── features/          — Feature modules
│   ├── auth/          — Login and registration
│   ├── dashboard/     — Main dashboard
│   ├── accounts/      — Account management
│   ├── cards/         — Card management
│   ├── loans/         — Loans
│   ├── transfer/      — Transfers
│   ├── notifications/ — Notifications
│   └── admin/         — Administration panel
└── shared/            — Shared components and pipes
```

---

## 3. Database

### 3.1 Main Entities

| Entity | Description |
|---|---|
| User | System user — personal data, email, password hash, role |
| Account | Bank account — IBAN, balance, type, associated user |
| Transaction | Financial transaction — amount, type, source/destination accounts |
| Card | Bank card — number, type, daily limit, status |
| Loan | Loan — amount, interest rate, status, term |
| Beneficiary | Saved beneficiary for quick transfers |
| Notification | System notification for a user |
| AuditLog | Audit trail of system actions |
| AccountType | Account types: Checking, Savings, Investment |
| LoanStatus | Loan states: Pending, Active, Completed, Rejected |
| NotificationType | Notification types: Transaction, Security, Loan, General |
| Service | Available services for payment |

### 3.2 Entity Relationships

- A User can have multiple Accounts (1:N)
- An Account belongs to an AccountType (N:1)
- A Transaction has an optional source account (FromAccount) and a destination account (ToAccount)
- A Card belongs to an Account (N:1)
- A Loan belongs to a User (N:1)
- A Notification belongs to a User (N:1)

### 3.3 Migrations

Migrations are managed by Entity Framework Core and applied automatically on application startup via `MigrateAsync()`, ensuring the database schema is always up to date without manual intervention.

```bash
dotnet ef migrations add MigrationName
dotnet ef database update
```

### 3.4 Data Seeder

On startup, the `DataSeeder` checks for the existence of base data and, if not present, automatically seeds account types, loan statuses, notification types, and an administrator user. The administrator credentials are configured exclusively via environment variables and are never stored in source code or version control.

---

## 4. Backend (API)

### 4.1 API Documentation — Swagger

The API is documented with Swagger (OpenAPI), accessible at `/swagger`. Swagger provides an interactive interface to explore and test all available endpoints directly from the browser, including authentication via Bearer token.

To authenticate in Swagger, click the **Authorize** button and enter the JWT token obtained from the login endpoint in the format: `Bearer <token>`.

### 4.2 Available Endpoints

#### Authentication

| Method | Endpoint | Description |
|---|---|---|
| POST | /api/auth/register | Register a new user |
| POST | /api/auth/login | Login and obtain JWT token |

#### Users

| Method | Endpoint | Description |
|---|---|---|
| GET | /api/users | List all users (Admin only) |
| GET | /api/users/{id} | Get user by ID |
| PUT | /api/users/{id} | Update user |
| DELETE | /api/users/{id} | Delete user |

#### Accounts

| Method | Endpoint | Description |
|---|---|---|
| GET | /api/accounts | List user accounts |
| GET | /api/accounts/{id} | Get account by ID |
| POST | /api/accounts | Create new account |
| DELETE | /api/accounts/{id} | Delete account |

#### Transactions

| Method | Endpoint | Description |
|---|---|---|
| GET | /api/transactions | List transactions |
| POST | /api/transactions/transfer | Transfer between accounts |
| POST | /api/transactions/transfer-iban | Transfer by IBAN |

#### Cards

| Method | Endpoint | Description |
|---|---|---|
| GET | /api/cards | List cards |
| POST | /api/cards/request | Request a new card |
| PUT | /api/cards/{id}/toggle | Activate/deactivate card |
| PUT | /api/cards/{id}/approve | Approve card (Admin only) |
| PUT | /api/cards/{id}/reject | Reject card (Admin only) |

#### Loans

| Method | Endpoint | Description |
|---|---|---|
| GET | /api/loans | List loans |
| POST | /api/loans | Apply for a loan |
| PUT | /api/loans/{id}/approve | Approve loan (Admin only) |
| PUT | /api/loans/{id}/reject | Reject loan (Admin only) |

### 4.3 JWT Authentication

The API uses JSON Web Tokens (JWT) for authentication. After a successful login, the client receives a signed token that must be included in the `Authorization` header of every subsequent request.

```
Authorization: Bearer <token>
```

The token contains the following claims: user ID, email, role, and full name. Token expiration is configurable via the `JwtSettings__ExpirationInHours` environment variable.

### 4.4 Role-Based Authorization

| Role | Permissions |
|---|---|
| Client | Access to own accounts, cards, transactions, loans, notifications, and transfers |
| Admin | Full access — user management, card and loan approvals, system-wide visibility |

### 4.5 Error Handling

The API includes a `GlobalExceptionHandler` that intercepts all unhandled exceptions and converts them into standardized HTTP responses. No internal error details are leaked to the client. Custom exception classes include `NotFoundException` (404), `ConflictException` (409), `BadRequestException` (400), and `UnauthorizedException` (401).

---

## 5. Frontend (Angular)

### 5.1 Main Components

| Component | Description |
|---|---|
| Layout | Base structure with sidebar navigation and main content area |
| Dashboard | Main panel with account summary and quick actions |
| Accounts | Account listing and detail view |
| Cards | Card management — activate, deactivate, request new card |
| Transfer | Transfer form — between accounts or by IBAN |
| Loans | Loan listing and application |
| Notifications | Notification center with mark-as-read functionality |
| Services | Available service payments |
| Admin/Users | User management (Admin only) |
| Admin/Loans | Loan approval management (Admin only) |
| Admin/Cards | Card approval management (Admin only) |

### 5.2 Services and API Communication

Each feature has its own Angular service that encapsulates HTTP calls to the API. An `AuthInterceptor` automatically attaches the JWT token to all outgoing requests. The `AuthService` manages the authentication state and the current user's data using Angular signals.

### 5.3 Route Guards

- `AuthGuard` — redirects unauthenticated users to the login page
- `AdminGuard` — restricts access to administration routes to Admin role only

### 5.4 Responsiveness

The application is fully responsive for mobile devices. On desktop, a full sidebar navigation is displayed with labels and icons. On screens narrower than 768px, the sidebar collapses to a compact icon-only version (56px wide), preserving full navigation access while maximizing the available content area.

---

## 6. Docker & Deployment

### 6.1 Containerization

The application is fully containerized with Docker, using a multi-stage build approach that produces lightweight, production-ready images.

#### Dockerfile — API (.NET)

The API Dockerfile uses 3 stages: the lightweight runtime base image (`aspnet:10.0`, ~200MB), the full SDK build image (~900MB) to compile and publish the application, and a final stage that combines only the runtime with the compiled output. The resulting image is approximately 90MB.

#### Dockerfile — Web (Angular + Nginx)

The frontend Dockerfile uses 2 stages: Node.js 22 to compile the Angular application in production mode, and Nginx Alpine to serve the static files. The Nginx configuration includes SPA routing support via `try_files`, ensuring that deep-linking and page refresh work correctly.

### 6.2 Docker Compose

The `docker-compose.yml` defines 3 services: `db` (PostgreSQL 17), `api` (.NET), and `web` (Angular/Nginx). The `api` service depends on `db` with a healthcheck to ensure the database is ready before the API starts. All sensitive values are read from a `.env` file that is never committed to version control.

### 6.3 Deployment on Fly.io

The application is deployed on the Fly.io platform using the `flyctl` CLI. The PostgreSQL database runs in a dedicated cluster in the Frankfurt region. The API and frontend are deployed as independent Fly.io apps.

| Service | URL |
|---|---|
| Frontend | https://bankingapp-web.fly.dev |
| Backend API | https://bankingapp-api.fly.dev |
| Swagger | https://bankingapp-api.fly.dev/swagger |

### 6.4 Environment Variables

All sensitive configuration is managed as secrets on Fly.io and is never committed to the repository. In local development, values are stored in the `.env` file or `appsettings.Development.json`, both of which are listed in `.gitignore`.

| Variable | Description |
|---|---|
| `ConnectionStrings__DefaultConnection` | PostgreSQL database connection string |
| `JwtSettings__Secret` | Secret key for JWT token signing |
| `JwtSettings__Issuer` | JWT token issuer |
| `JwtSettings__Audience` | JWT token audience |
| `JwtSettings__ExpirationInHours` | Token validity duration in hours |
| `Seeder__AdminPassword` | Initial administrator account password |

---

## 7. Security

### 7.1 Authentication

Authentication is based on JWT (JSON Web Tokens). Passwords are hashed with BCrypt before being stored in the database. Plain-text passwords are never stored or logged anywhere in the system.

### 7.2 Authorization

Authorization is role-based (Admin and Client). Protected endpoints use the `[Authorize]` attribute with role verification. The frontend enforces the same restrictions through `AuthGuard` and `AdminGuard`, controlling both route access and UI element visibility.

### 7.3 Administrator Account

The administrator account is seeded automatically on first startup. Its credentials are configured exclusively through environment variables (`Seeder__AdminPassword`) and are managed as secrets on the deployment platform. They are never hardcoded in source code, configuration files, or version control. Access to the administrator account should be restricted to authorized personnel only.

### 7.4 CORS

The API is configured with a strict CORS policy that only permits requests from known origins: localhost for local development and the Fly.io domain for production. All other origins are blocked at the API level.

### 7.5 Security Best Practices

- BCrypt password hashing — plain-text passwords are never stored
- JWT tokens with configurable expiration
- Sensitive credentials managed as platform secrets — never in source code
- CORS restricted to known origins
- DTOs prevent accidental exposure of sensitive entity fields
- `GlobalExceptionHandler` prevents internal error details from leaking to clients
- `.env` and `appsettings.Development.json` excluded from version control via `.gitignore`
- Role-based access control enforced at both API and frontend levels

---

## 8. Local Installation Guide

### 8.1 Prerequisites

- .NET SDK 10.0 or higher
- Node.js 22.x or higher
- Docker Desktop
- PostgreSQL 17 (or via Docker)
- Git

### 8.2 Clone the Repository

```bash
git clone https://github.com/Geoffrey87/MyProjects.git
cd MyProjects/BankingApp
```

### 8.3 Configure the Backend

Create the file `appsettings.Development.json` inside the `BankingApp.API` folder with your local database credentials and JWT settings. This file is excluded from version control and must be created manually on each development machine.

### 8.4 Run with Docker Compose

Create a `.env` file at the project root with the required environment variables, then run:

```bash
docker-compose up --build
```

The application will be available at `http://localhost:4200` and the API at `http://localhost:8080`. The Swagger documentation is accessible at `http://localhost:8080/swagger`.

### 8.5 Run in Development Mode

For development with hot reload, run the backend and frontend separately:

```bash
# Backend
cd BankingApp.API
dotnet run

# Frontend (in a separate terminal)
cd BankingApp.Web
ng serve
```

The Angular development server will be available at `http://localhost:4200` and will proxy API requests to `http://localhost:5168`.

### 8.6 Testing the API

To verify the API endpoints are working correctly after setup, open the Swagger UI at `/swagger`. All endpoints can be tested interactively — authenticate first using the `/api/auth/login` endpoint, copy the returned token, click the **Authorize** button in Swagger, and enter the token to access protected endpoints.

---

*BankingApp — Technical Documentation — 2026*
