using BankingApp.API.Enums;
using System.ComponentModel.DataAnnotations;

namespace BankingApp.API.DTOs.RequestDtos
{
    public class CardRequestDto
    {
        [Required(ErrorMessage = "Account ID is required")]
        public int AccountId { get; set; }

        [Required(ErrorMessage = "Card holder name is required")]
        [MaxLength(100)]
        public string CardHolderName { get; set; } = string.Empty;

        [Required(ErrorMessage = "Card type is required")]
        public CardType CardType { get; set; }

        [Required(ErrorMessage = "Expiry date is required")]
        public DateOnly ExpiryDate { get; set; }

        [Range(0.01, double.MaxValue, ErrorMessage = "Daily limit must be greater than zero")]
        public decimal DailyLimit { get; set; } = 1000;
    }
}
