using BankingApp.API.Enums;

namespace BankingApp.API.DTOs.ResponseDtos
{
    public class TransactionResponseDto
    {
        public int Id { get; set; }
        public int FromAccountId { get; set; }
        public int? ToAccountId { get; set; }
        public decimal Amount { get; set; }
        public string Currency { get; set; } = string.Empty;
        public string TransactionType { get; set; } = string.Empty;
        public string Status { get; set; } = string.Empty;
        public string Description { get; set; } = string.Empty;
        public DateTime CreatedAt { get; set; }
    }
}
