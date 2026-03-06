namespace BankingApp.API.DTOs.ResponseDtos
{
    public class BeneficiaryResponseDto
    {
        public int Id { get; set; }
        public int UserId { get; set; }
        public string Name { get; set; } = string.Empty;
        public string IBAN { get; set; } = string.Empty;
        public string Bank { get; set; } = string.Empty;
        public DateTime CreatedAt { get; set; }
    }
}
