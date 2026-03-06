using BankingApp.API.Common;
using BankingApp.API.Enums;
using System.ComponentModel.DataAnnotations.Schema;

namespace BankingApp.API.Entities
{
    [Table("loans")]
    public class Loan : BaseEntity
    {
        public decimal Amount { get; set; }
        public decimal InterestRate { get; set; }
        public int TermMonths { get; set; }
        public decimal MonthlyPayment { get; set; }
        public decimal RemainingBalance { get; set; }
        public LoanStatus Status { get; set; } = LoanStatus.Pending;
        public DateTime StartDate { get; set; }
        public DateTime? EndDate { get; set; }

        // Foreign Keys
        public int UserId { get; set; }
        public int AccountId { get; set; }

        // Navigation properties
        public User User { get; set; } = null!;
        public Account Account { get; set; } = null!;
    }
}