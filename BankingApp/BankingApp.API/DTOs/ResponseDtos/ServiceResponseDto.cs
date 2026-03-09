namespace BankingApp.API.DTOs.ResponseDtos
{
    public class ServiceResponseDto
    {
        public int Id { get; set; }
        public string Name { get; set; } = string.Empty;
        public string Description { get; set; } = string.Empty;
        public decimal Amount { get; set; }
        public bool IsFixedAmount { get; set; }
        public string Category { get; set; } = string.Empty;
    }
}