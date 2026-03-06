using BankingApp.API.Common;
using System.ComponentModel.DataAnnotations.Schema;

namespace BankingApp.API.Entities
{
    [Table("loan_statuses")]
    public class LoanStatus : BaseEntity
    {
        public string Name { get; set; } = string.Empty;
        public string Description { get; set; } = string.Empty;

        // Navigation property
        public ICollection<Loan> Loans { get; set; } = new List<Loan>();
    }
}
