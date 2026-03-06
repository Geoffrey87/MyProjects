using System.ComponentModel.DataAnnotations;

namespace BankingApp.API.DTOs.RequestDtos
{
    public class LoanRequestDto
    {
        [Required(ErrorMessage = "User ID is required")]
        public int UserId { get; set; }

        [Required(ErrorMessage = "Account ID is required")]
        public int AccountId { get; set; }

        [Required(ErrorMessage = "Amount is required")]
        [Range(0.01, double.MaxValue, ErrorMessage = "Amount must be greater than zero")]
        public decimal Amount { get; set; }

        [Required(ErrorMessage = "Interest rate is required")]
        [Range(0.01, 100, ErrorMessage = "Interest rate must be between 0 and 100")]
        public decimal InterestRate { get; set; }

        [Required(ErrorMessage = "Term in months is required")]
        [Range(1, 360, ErrorMessage = "Term must be between 1 and 360 months")]
        public int TermMonths { get; set; }

        [Required(ErrorMessage = "Start date is required")]
        public DateTime StartDate { get; set; }
    }
}