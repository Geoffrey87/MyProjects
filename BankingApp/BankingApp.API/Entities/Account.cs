using BankingApp.API.Common;
using System.ComponentModel.DataAnnotations.Schema;

namespace BankingApp.API.Entities
{
    [Table("accounts")]
    public class Account : BaseEntity
    {
        public string AccountNumber { get; set; } = string.Empty;
        public string IBAN { get; set; } = string.Empty;
        public decimal Balance { get; set; } = 0;
        public string Currency { get; set; } = "EUR";
        public int AccountTypeId { get; set; }
        public AccountType AccountType { get; set; } = null!;
        public bool IsActive { get; set; } = true;

        // Foreign Key
        public int UserId { get; set; }

        // Navigation properties
        public User User { get; set; } = null!;
        public ICollection<Transaction> Transactions { get; set; } = new List<Transaction>();
        public ICollection<Card> Cards { get; set; } = new List<Card>();
        public ICollection<Loan> Loans { get; set; } = new List<Loan>();
    }
}
