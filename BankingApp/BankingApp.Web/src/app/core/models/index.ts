// ─── Enums ────────────────────────────────────────────────────────────────────

export enum UserRole {
  Client = 'Client',
  Admin = 'Admin',
  Manager = 'Manager',
}

export enum TransactionType {
  Deposit = 'Deposit',
  Withdrawal = 'Withdrawal',
  Transfer = 'Transfer',
  Payment = 'Payment',
}

export enum TransactionStatus {
  Pending = 'Pending',
  Completed = 'Completed',
  Failed = 'Failed',
}

export enum CardType {
  Debit = 'Debit',
  Credit = 'Credit',
}

// ─── User ─────────────────────────────────────────────────────────────────────

export interface User {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  phoneNumber: string;
  nif: string;
  address: string;
  dateOfBirth: string;
  role: UserRole;
  isActive: boolean;
  createdAt: string;
}

export interface RegisterRequest {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  phoneNumber: string;
  nif: string;
  address: string;
  dateOfBirth: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface AuthResponse {
  token: string;
  email: string;
  fullName: string;
  role: string;
  expiresAt: string;
}

export interface UpdateUserRequest {
  firstName?: string;
  lastName?: string;
  phoneNumber?: string;
  address?: string;
}

// ─── Account ──────────────────────────────────────────────────────────────────

export interface Account {
  id: number;
  userId: number;
  accountNumber: string;
  iban: string;
  balance: number;
  currency: string;
  accountType: string;
  isActive: boolean;
  createdAt: string;
}

export interface DepositWithdrawRequest {
  amount: number;
  description?: string;
}

// ─── Transaction ──────────────────────────────────────────────────────────────

export interface Transaction {
  id: number;
  fromAccountId: number;
  toAccountId: number;
  amount: number;
  currency: string;
  transactionType: TransactionType;
  status: TransactionStatus;
  description: string;
  createdAt: string;
}

export interface TransferRequest {
  fromAccountId: number;
  toAccountId: number;
  amount: number;
  description?: string;
}

// ─── Card ─────────────────────────────────────────────────────────────────────

export interface Card {
  id: number;
  accountId: number;
  cardNumber: string;
  cardHolderName: string;
  cardType: CardType;
  expiryDate: string;
  isActive: boolean;
  dailyLimit: number;
  createdAt: string;
}

// ─── Loan ─────────────────────────────────────────────────────────────────────

export interface Loan {
  id: number;
  userId: number;
  accountId: number;
  amount: number;
  interestRate: number;
  termMonths: number;
  monthlyPayment: number;
  remainingBalance: number;
  status: string;
  startDate: string | null;
  endDate: string | null;
  createdAt: string;
}

export interface CreateLoanRequest {
  userId: number;
  accountId: number;
  amount: number;
  interestRate: number;
  termMonths: number;
}

// ─── Beneficiary ──────────────────────────────────────────────────────────────

export interface Beneficiary {
  id: number;
  userId: number;
  name: string;
  iban: string;
  bank: string;
  createdAt: string;
}

// ─── Notification ─────────────────────────────────────────────────────────────

export interface Notification {
  id: number;
  userId: number;
  title: string;
  message: string;
  type: string;
  isRead: boolean;
  createdAt: string;
}

// ─── Service (Payments) ───────────────────────────────────────────────────────

export interface BankingService {
  id: number;
  name: string;
  category: string;
  fixedAmount: number | null;
  description: string;
}

export interface PayServiceRequest {
  accountId: number;
  amount?: number;
}

// ─── Audit Log ────────────────────────────────────────────────────────────────

export interface AuditLog {
  id: number;
  userId: number;
  action: string;
  entity: string;
  ipAddress: string;
  details: string;
  createdAt: string;
}

// ─── API Error ────────────────────────────────────────────────────────────────

export interface ApiError {
  timestamp: string;
  status: number;
  error: string;
  message: string;
}
