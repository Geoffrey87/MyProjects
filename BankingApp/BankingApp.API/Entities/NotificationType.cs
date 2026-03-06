using BankingApp.API.Common;
using System.ComponentModel.DataAnnotations.Schema;

namespace BankingApp.API.Entities
{
    [Table("notification_types")]
    public class NotificationType : BaseEntity
    {
        public string Name { get; set; } = string.Empty;
        public string Description { get; set; } = string.Empty;

        // Navigation property
        public ICollection<Notification> Notifications { get; set; } = new List<Notification>();
    }
}
