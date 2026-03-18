using BankingApp.API.Common;
using BankingApp.API.Enums;
using System.ComponentModel.DataAnnotations.Schema;

namespace BankingApp.API.Entities
{
    [Table("transactions")]
    public class Transaction : BaseEntity
    {
        public decimal Amount { get; set; }
        public string Currency { get; set; } = "EUR";
        public string Description { get; set; } = string.Empty;
        public TransactionType TransactionType { get; set; }
        public TransactionStatus Status { get; set; } = TransactionStatus.Pending;

        // Foreign Keys
        public int? FromAccountId { get; set; }
        public int? ToAccountId { get; set; } // Nullable for non-transfer transactions

        // Navigation properties
        public Account FromAccount { get; set; } = null!;
        public Account? ToAccount { get; set; } // Nullable for non-transfer transactions
    }
}