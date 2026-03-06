using BankingApp.API.Common;
using System.ComponentModel.DataAnnotations.Schema;

namespace BankingApp.API.Entities
{
    [Table("notifications")]
    public class Notification : BaseEntity
    {
        public string Title { get; set; } = string.Empty;
        public string Message { get; set; } = string.Empty;
        public int NotificationTypeId { get; set; }
        public NotificationType NotificationType { get; set; } = null!;

        public bool IsRead { get; set; } = false;

        // Foreign Key
        public int UserId { get; set; }

        // Navigation property
        public User User { get; set; } = null!;
    }
}
