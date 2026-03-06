using BankingApp.API.Common;
using BankingApp.API.Enums;
using System.ComponentModel.DataAnnotations.Schema;

namespace BankingApp.API.Entities
{
    [Table("notifications")]
    public class Notification : BaseEntity
    {
        public string Title { get; set; } = string.Empty;
        public string Message { get; set; } = string.Empty;
        public NotificationType Type { get; set; }
        public bool IsRead { get; set; } = false;

        // Foreign Key
        public int UserId { get; set; }

        // Navigation property
        public User User { get; set; } = null!;
    }
}
