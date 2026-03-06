using System.ComponentModel.DataAnnotations.Schema;
using BankingApp.API.Enums;
using BankingApp.API.Common;
using System.Security.Principal;

namespace BankingApp.API.Entities
{
    [Table("users")]
    public class User : BaseEntity
    {
        public string FirstName { get; set; } = string.Empty;
        public string LastName { get; set; } = string.Empty;
        public string Email { get; set; } = string.Empty;
        public string PasswordHash { get; set; } = string.Empty;
        public string PhoneNumber { get; set; } = string.Empty;
        public string NIF { get; set; } = string.Empty;
        public string Address { get; set; } = string.Empty;
        public DateOnly DateOfBirth { get; set; }
        public UserRole Role { get; set; } = UserRole.Client;
        public bool IsActive { get; set; } = true;

        // Navigation properties
        public ICollection<Account> Accounts { get; set; } = new List<Account>();
        public ICollection<Loan> Loans { get; set; } = new List<Loan>();
        public ICollection<Beneficiary> Beneficiaries { get; set; } = new List<Beneficiary>();
        public ICollection<Notification> Notifications { get; set; } = new List<Notification>();
        public ICollection<AuditLog> AuditLogs { get; set; } = new List<AuditLog>();
    }
}