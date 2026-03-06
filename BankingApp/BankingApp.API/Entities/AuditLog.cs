using BankingApp.API.Common;
using System.ComponentModel.DataAnnotations.Schema;

namespace BankingApp.API.Entities
{
    [Table("audit_logs")]
    public class AuditLog : BaseEntity
    {
        public string Action { get; set; } = string.Empty;
        public string Entity { get; set; } = string.Empty;
        public string IpAddress { get; set; } = string.Empty;
        public string Details { get; set; } = string.Empty;

        // Foreign Key
        public int UserId { get; set; }

        // Navigation property
        public User User { get; set; } = null!;
    }
}
