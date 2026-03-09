# BankingApp API Documentation

**Backend — ASP.NET Core Web API (.NET 10)**  
**March 2026**

---

## Table of Contents
1. [Project Overview](#1-project-overview)
2. [Architecture](#2-architecture)
3. [Domain Model](#3-domain-model)
4. [Authentication & Authorization](#4-authentication--authorization)
5. [API Endpoints](#5-api-endpoints)
6. [Error Handling](#6-error-handling)
7. [Data Seeding](#7-data-seeding)
8. [Business Logic](#8-business-logic)
9. [SOLID Principles Applied](#9-solid-principles-applied)
10. [Next Steps](#10-next-steps)

---

## 1. Project Overview

BankingApp is a RESTful API built with ASP.NET Core (.NET 10) that simulates a banking application. It was built as a portfolio project to demonstrate knowledge of enterprise-grade backend development with C#/.NET.

The application allows users to register, manage bank accounts, perform transactions, manage cards, request loans, and pay fictional services.

### 1.1 Technology Stack

| Technology | Details |
|---|---|
| Framework | ASP.NET Core Web API (.NET 10) |
| Language | C# |
| ORM | Entity Framework Core (InMemory) |
| Authentication | JWT Bearer Tokens |
| Password Hashing | BCrypt.Net |
| Object Mapping | AutoMapper 12 |
| API Documentation | Swagger / Swashbuckle |
| Version Control | Git / GitHub |

---

## 2. Architecture

The project follows a layered architecture pattern, separating concerns across distinct layers.

### 2.1 Project Structure

```
BankingApp.API/
├── Common/           — BaseEntity (shared base class)
├── Controllers/      — API endpoints
├── Data/             — AppDbContext + DataSeeder
├── DTOs/             — Request and Response DTOs
│   ├── RequestDtos/
│   └── ResponseDtos/
├── Entities/         — Domain entities
├── Enums/            — Enumerations
├── Exceptions/       — Custom exceptions + Global handler
│   ├── Custom/
│   └── Handlers/
├── Mappings/         — AutoMapper profiles
├── Repositories/     — Data access layer
│   ├── Interfaces/
│   └── Implementations/
└── Services/         — Business logic layer
    ├── Interfaces/
    └── Implementations/
```

### 2.2 Layer Responsibilities

| Layer | Responsibility |
|---|---|
| Controllers | Handle HTTP requests/responses. No business logic. |
| Services | Business logic. Maps between DTOs and Entities. |
| Repositories | Data access. Queries to the database via EF Core. |
| Entities | Database table mappings. |
| DTOs | Data transfer objects for request/response. |
| Mappings | AutoMapper profiles for Entity <-> DTO conversion. |
| Exceptions | Custom exceptions and global error handling. |

### 2.3 Comparison with Spring Boot

| Spring Boot (Java) | .NET (C#) |
|---|---|
| `@RestController` | `[ApiController]` |
| `@Service` | Service class with IService interface |
| `@Repository` / JpaRepository | Repository class with IRepository interface |
| `@Entity` / `@Table` | `[Table]` attribute on Entity class |
| `application.properties` | `appsettings.json` + `Program.cs` |
| `@Autowired` | Constructor injection |
| `@RestControllerAdvice` | `IExceptionFilter` / `GlobalExceptionHandler` |
| MapStruct | AutoMapper |
| Spring Security + JWT | ASP.NET Authentication + JwtBearer |
| `@SpringBootApplication` | `WebApplication.CreateBuilder(args)` |

---

## 3. Domain Model

### 3.1 BaseEntity

All entities inherit from `BaseEntity`, which provides common audit fields:

```csharp
public abstract class BaseEntity
{
    public int Id { get; set; }
    public DateTime CreatedAt { get; set; } = DateTime.UtcNow;
    public string? CreatedBy { get; set; }
    public DateTime? UpdatedAt { get; set; }
    public string? UpdatedBy { get; set; }
}
```

### 3.2 Entities

| Entity | Table | Description |
|---|---|---|
| User | users | Bank customer with authentication |
| Account | accounts | Bank account with balance |
| Transaction | transactions | Financial movements |
| Card | cards | Debit/Credit cards |
| Loan | loans | Bank loans |
| Beneficiary | beneficiaries | Saved transfer recipients |
| Notification | notifications | User alerts |
| AuditLog | audit_logs | Security audit trail |
| AccountType | account_types | Account type lookup table |
| LoanStatus | loan_statuses | Loan status lookup table |
| NotificationType | notification_types | Notification type lookup table |
| Service | services | Fictional payable services |

### 3.3 Enums

| Enum | Values |
|---|---|
| UserRole | Client, Admin, Manager |
| TransactionType | Deposit, Withdrawal, Transfer, Payment |
| TransactionStatus | Pending, Completed, Failed |
| CardType | Debit, Credit |

### 3.4 Entity Relationships

- **User** has many **Accounts** (1:N)
- **User** has many **Loans** (1:N)
- **User** has many **Beneficiaries** (1:N)
- **User** has many **Notifications** (1:N)
- **User** has many **AuditLogs** (1:N)
- **Account** has many **Transactions** as sender (1:N)
- **Account** has many **Transactions** as receiver (1:N)
- **Account** has many **Cards** (1:N)
- **Account** has many **Loans** (1:N)
- **Account** belongs to **AccountType** (N:1)
- **Loan** belongs to **LoanStatus** (N:1)
- **Notification** belongs to **NotificationType** (N:1)

---

## 4. Authentication & Authorization

The API uses JWT (JSON Web Token) Bearer authentication. Tokens are issued on login/register and must be included in the `Authorization` header of subsequent requests.

### 4.1 JWT Configuration

```json
{
  "JwtSettings": {
    "Secret": "BankingAppSuperSecretKey...",
    "Issuer": "BankingApp.API",
    "Audience": "BankingApp.Client",
    "ExpirationInHours": 24
  }
}
```

### 4.2 JWT Token Claims

| Claim | Value |
|---|---|
| NameIdentifier | User ID |
| Email | User email |
| Role | Client / Admin / Manager |
| Name | Full name |

### 4.3 Authorization Roles

| Role | Permissions |
|---|---|
| Client | View own data, perform transactions, manage beneficiaries |
| Admin | All Client permissions + manage users, approve loans, admin operations |
| Manager | To be defined in future iterations |

### 4.4 Default Admin Credentials

```
Email:    admin@bankingapp.com
Password: Admin123!
```

---

## 5. API Endpoints

### 5.1 Auth

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| POST | `/api/auth/register` | Public | Register new user |
| POST | `/api/auth/login` | Public | Login and get JWT token |

### 5.2 User

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| GET | `/api/user` | Admin | Get all users |
| GET | `/api/user/{id}` | Authenticated | Get user by ID |
| GET | `/api/user/email/{email}` | Admin | Get user by email |
| POST | `/api/user` | Admin | Create user |
| PUT | `/api/user/{id}` | Authenticated | Update user |
| DELETE | `/api/user/{id}` | Admin | Delete user |

### 5.3 Account

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| GET | `/api/account/user/{userId}` | Authenticated | Get accounts by user |
| GET | `/api/account/{id}` | Authenticated | Get account by ID |
| GET | `/api/account/{id}/balance` | Authenticated | Get account balance |
| POST | `/api/account` | Admin | Create account |
| POST | `/api/account/{id}/deposit` | Authenticated | Deposit to account |
| POST | `/api/account/{id}/withdraw` | Authenticated | Withdraw from account |
| PUT | `/api/account/{id}` | Admin | Update account |
| DELETE | `/api/account/{id}` | Admin | Delete account |

### 5.4 Transaction

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| GET | `/api/transaction/account/{accountId}` | Authenticated | Get by account |
| GET | `/api/transaction/{id}` | Authenticated | Get by ID |
| GET | `/api/transaction/account/{id}/range` | Authenticated | Get by date range |
| POST | `/api/transaction` | Authenticated | Create transaction |
| POST | `/api/transaction/transfer` | Authenticated | Transfer between accounts |
| DELETE | `/api/transaction/{id}` | Admin | Delete transaction |

### 5.5 Card

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| GET | `/api/card/account/{accountId}` | Authenticated | Get cards by account |
| GET | `/api/card/{id}` | Authenticated | Get card by ID |
| GET | `/api/card/number/{cardNumber}` | Admin | Get by card number |
| POST | `/api/card` | Admin | Create card |
| PATCH | `/api/card/{id}/toggle` | Authenticated | Activate/deactivate card |
| PUT | `/api/card/{id}` | Admin | Update card |
| DELETE | `/api/card/{id}` | Admin | Delete card |

### 5.6 Loan

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| GET | `/api/loan/user/{userId}` | Authenticated | Get loans by user |
| GET | `/api/loan/{id}` | Authenticated | Get loan by ID |
| POST | `/api/loan` | Admin | Create loan |
| PATCH | `/api/loan/{id}/approve` | Admin | Approve loan |
| PATCH | `/api/loan/{id}/reject` | Admin | Reject loan |
| PUT | `/api/loan/{id}` | Admin | Update loan |
| DELETE | `/api/loan/{id}` | Admin | Delete loan |

### 5.7 Beneficiary

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| GET | `/api/beneficiary/user/{userId}` | Authenticated | Get by user |
| GET | `/api/beneficiary/{id}` | Authenticated | Get by ID |
| POST | `/api/beneficiary` | Authenticated | Create beneficiary |
| PUT | `/api/beneficiary/{id}` | Authenticated | Update beneficiary |
| DELETE | `/api/beneficiary/{id}` | Authenticated | Delete beneficiary |

### 5.8 Service (Fictional Payments)

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| GET | `/api/service` | Authenticated | Get all services |
| GET | `/api/service/category/{category}` | Authenticated | Get by category |
| POST | `/api/service/{id}/pay` | Authenticated | Pay a service |

### 5.9 Notification

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| GET | `/api/notification/user/{userId}` | Authenticated | Get by user |
| GET | `/api/notification/user/{userId}/unread` | Authenticated | Get unread |
| GET | `/api/notification/{id}` | Authenticated | Get by ID |
| POST | `/api/notification` | Admin | Create notification |
| PATCH | `/api/notification/{id}/read` | Authenticated | Mark as read |
| PUT | `/api/notification/{id}` | Admin | Update notification |
| DELETE | `/api/notification/{id}` | Admin | Delete notification |

### 5.10 Audit Log

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| GET | `/api/auditlog/user/{userId}` | Admin | Get logs by user |
| GET | `/api/auditlog/{id}` | Admin | Get log by ID |
| DELETE | `/api/auditlog/{id}` | Admin | Delete log |

---

## 6. Error Handling

All errors are handled globally by the `GlobalExceptionHandler` which implements `IExceptionFilter`. Every error returns a standardized JSON response.

### 6.1 Error Response Format

```json
{
  "timestamp": "2026-03-09T12:00:00Z",
  "status": 404,
  "error": "NotFound",
  "message": "User not found"
}
```

### 6.2 Custom Exceptions

| Exception | HTTP Status | Use Case |
|---|---|---|
| NotFoundException | 404 Not Found | Resource not found |
| ConflictException | 409 Conflict | Duplicate resource |
| BadRequestException | 400 Bad Request | Invalid input |
| ForbiddenException | 403 Forbidden | Access denied |
| UnauthorizedException | 401 Unauthorized | Not authenticated |

### 6.3 Error Messages

All error messages are centralized in the `ErrorMessages` static class to ensure consistency (DRY principle):

```csharp
public static class ErrorMessages
{
    public const string UserNotFound = "User not found";
    public const string AccountNotFound = "Account not found";
    public const string InsufficientFunds = "Insufficient funds";
    // ...
}
```

---

## 7. Data Seeding

On application startup, the `DataSeeder` automatically populates the database with initial data.

| Data | Values |
|---|---|
| AccountTypes | Checking, Savings, Investment |
| LoanStatuses | Pending, Active, Completed, Rejected |
| NotificationTypes | Transaction, Security, Loan, General |
| Admin User | admin@bankingapp.com / Admin123! |
| Services | Netflix, Spotify, NOS Internet, EDP, EPAL, Renda |

---

## 8. Business Logic

### 8.1 User Registration Flow

1. Validates email and NIF uniqueness
2. Hashes password with BCrypt
3. Creates User entity
4. Automatically creates a Checking account
5. Credits 10,000 EUR welcome bonus
6. Creates a Deposit transaction for audit trail
7. Returns JWT token

### 8.2 Transfer Flow

1. Validates `fromAccountId != toAccountId`
2. Validates `amount > 0`
3. Calls `AccountService.WithdrawAsync` (debit source)
4. Calls `AccountService.DepositAsync` (credit destination)
5. Creates Transaction record

### 8.3 Loan Approval Flow

1. Admin calls `PATCH /api/loan/{id}/approve`
2. Validates loan is in Pending status
3. Changes status to Active
4. Sets start and end dates
5. Credits loan amount to account
6. Creates Deposit transaction for audit trail

### 8.4 Service Payment Flow

1. User selects a service
2. For fixed amount services (Netflix, Spotify): uses predefined amount
3. For variable amount services (EDP, Water): user provides amount
4. Debits account balance
5. Creates Payment transaction for audit trail

### 8.5 Monthly Payment Calculation

Loans use the standard amortization formula:

```
MonthlyPayment = P * (r * (1 + r)^n) / ((1 + r)^n - 1)
```

Where `P` = principal amount, `r` = monthly interest rate, `n` = term in months.

---

## 9. SOLID Principles Applied

### 9.1 Single Responsibility Principle (SRP)
- Controllers only handle HTTP requests/responses
- Services only contain business logic
- Repositories only handle data access
- Mappers only handle object conversion
- `ErrorMessages` class only contains error message constants

### 9.2 Open/Closed Principle (OCP)
- `AccountType`, `LoanStatus`, `NotificationType` are database entities (not enums)
- New types can be added without modifying code
- `TransactionType` and `CardType` remain enums as they are stable

### 9.3 Dependency Inversion Principle (DIP)
- All dependencies are injected via interfaces (`IUserService`, `IUserRepository`, etc.)
- Controllers depend on `IService` interfaces, not concrete implementations
- Services depend on `IRepository` interfaces, not concrete implementations

---


