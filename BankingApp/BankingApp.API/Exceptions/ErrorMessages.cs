namespace BankingApp.API.Exceptions
{
    public static class ErrorMessages
    {
        // User
        public const string UserNotFound = "User not found";
        public const string UserEmailAlreadyExists = "Email already exists";
        public const string UserNIFAlreadyExists = "NIF already exists";
        public const string InvalidCredentials = "Invalid credentials";

        // Account
        public const string AccountNotFound = "Account not found";
        public const string AccountIBANAlreadyExists = "IBAN already exists";
        public const string InsufficientFunds = "Insufficient funds";
        public const string InvalidAmount = "Amount must be greater than zero";

        // Card
        public const string CardNotFound = "Card not found";
        public const string CardNumberAlreadyExists = "Card number already exists";

        // Loan
        public const string LoanNotFound = "Loan not found";

        // Beneficiary
        public const string BeneficiaryNotFound = "Beneficiary not found";
        public const string BeneficiaryAlreadyExists = "Beneficiary already exists";

        // Notification
        public const string NotificationNotFound = "Notification not found";

        // Transaction
        public const string TransactionNotFound = "Transaction not found";
        public const string InvalidTransfer = "Invalid transfer operation";

        // AuditLog
        public const string AuditLogNotFound = "Audit log not found";
        public const string InvalidAuditLog = "Action and entity are required";

        // Service
        public const string ServiceNotFound = "Service not found";

        // Card
        public const string CardNotPending = "Card is not in pending status.";
        public const string CardNotActive = "Cannot toggle a pending or rejected card.";
    }
}
