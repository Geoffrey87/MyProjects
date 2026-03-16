using BankingApp.API.Enums;
using System.ComponentModel.DataAnnotations;

namespace BankingApp.API.DTOs.RequestDtos
{
    public class TransactionRequestDto
    {
        [Required(ErrorMessage = "From account is required")]
        public int FromAccountId { get; set; }

        public int? ToAccountId { get; set; }

        public string? ToIBAN { get; set; }

        [Required(ErrorMessage = "Amount is required")]
        [Range(0.01, double.MaxValue, ErrorMessage = "Amount must be greater than zero")]
        public decimal Amount { get; set; }

        [Required(ErrorMessage = "Currency is required")]
        [MaxLength(3)]
        public string Currency { get; set; } = "EUR";

        [Required(ErrorMessage = "Transaction type is required")]
        public TransactionType TransactionType { get; set; }

        public string Description { get; set; } = string.Empty;
    }
}
