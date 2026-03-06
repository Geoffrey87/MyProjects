using BankingApp.API.Common;
using System.ComponentModel.DataAnnotations.Schema;

namespace BankingApp.API.Entities
{
    [Table("beneficiaries")]
    public class Beneficiary : BaseEntity
    {
        public string Name { get; set; } = string.Empty;
        public string IBAN { get; set; } = string.Empty;
        public string Bank { get; set; } = string.Empty;

        // Foreign Key
        public int UserId { get; set; }

        // Navigation property
        public User User { get; set; } = null!;
    }
}