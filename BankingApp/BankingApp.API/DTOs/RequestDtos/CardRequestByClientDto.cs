using BankingApp.API.Enums;
using System.ComponentModel.DataAnnotations;

namespace BankingApp.API.DTOs.RequestDtos
{
    public class CardRequestByClientDto
    {
        [Required(ErrorMessage = "Account ID is required")]
        public int AccountId { get; set; }

        [Required(ErrorMessage = "Card type is required")]
        public CardType CardType { get; set; }

        [Range(0.01, double.MaxValue, ErrorMessage = "Daily limit must be greater than zero")]
        public decimal DailyLimit { get; set; } = 1000;
    }
}