using System.ComponentModel.DataAnnotations;

namespace BankingApp.API.DTOs.RequestDtos
{
    public class BeneficiaryRequestDto
    {
        [Required(ErrorMessage = "User ID is required")]
        public int UserId { get; set; }

        [Required(ErrorMessage = "Name is required")]
        [MaxLength(100)]
        public string Name { get; set; } = string.Empty;

        [Required(ErrorMessage = "IBAN is required")]
        [MaxLength(34)]
        public string IBAN { get; set; } = string.Empty;

        [Required(ErrorMessage = "Bank is required")]
        [MaxLength(100)]
        public string Bank { get; set; } = string.Empty;
    }
}