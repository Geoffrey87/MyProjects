namespace BankingApp.API.DTOs.ResponseDtos
{
    public class CardResponseDto
    {
        public int Id { get; set; }
        public string CardNumber { get; set; } = string.Empty;
        public string CardHolderName { get; set; } = string.Empty;
        public string CardType { get; set; } = string.Empty;
        public DateOnly ExpiryDate { get; set; }
        public bool IsActive { get; set; }
        public decimal DailyLimit { get; set; }
        public string Status { get; set; } = string.Empty;
        public DateTime CreatedAt { get; set; }
    }
}