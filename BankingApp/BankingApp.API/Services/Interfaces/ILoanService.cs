using BankingApp.API.DTOs.RequestDtos;
using BankingApp.API.DTOs.ResponseDtos;

namespace BankingApp.API.Services.Interfaces
{
    public interface ILoanService
    {
        Task<List<LoanResponseDto>> GetAllAsync();
        Task<LoanResponseDto> GetByIdAsync(int id);
        Task<List<LoanResponseDto>> GetByUserIdAsync(int userId);
        Task<LoanResponseDto> CreateAsync(LoanRequestDto dto);
        Task<LoanResponseDto> UpdateAsync(int id, LoanRequestDto dto);
        Task DeleteAsync(int id);
        Task ApproveLoanAsync(int loanId);
        Task RejectLoanAsync(int loanId);
    }
}