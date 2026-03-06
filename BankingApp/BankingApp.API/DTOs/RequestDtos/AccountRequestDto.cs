using System.ComponentModel.DataAnnotations;

namespace BankingApp.API.DTOs.RequestDtos
{
    public class AccountRequestDto
    {
        [Required(ErrorMessage = "User ID is required")]
        public int UserId { get; set; }

        [Required(ErrorMessage = "Account type is required")]
        public int AccountTypeId { get; set; }

        [Required(ErrorMessage = "Currency is required")]
        [MaxLength(3, ErrorMessage = "Currency must be 3 characters")]
        public string Currency { get; set; } = "EUR";
    }
}