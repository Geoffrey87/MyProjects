using System.ComponentModel.DataAnnotations;

namespace BankingApp.API.DTOs.RequestDtos
{
    public class AuditLogRequestDto
    {
        [Required(ErrorMessage = "User ID is required")]
        public int UserId { get; set; }

        [Required(ErrorMessage = "Action is required")]
        [MaxLength(100)]
        public string Action { get; set; } = string.Empty;

        [Required(ErrorMessage = "Entity is required")]
        [MaxLength(100)]
        public string Entity { get; set; } = string.Empty;

        [Required(ErrorMessage = "IP Address is required")]
        [MaxLength(45)]
        public string IpAddress { get; set; } = string.Empty;

        public string Details { get; set; } = string.Empty;
    }
}