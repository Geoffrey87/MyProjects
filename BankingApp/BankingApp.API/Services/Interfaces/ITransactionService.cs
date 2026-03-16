using BankingApp.API.DTOs.RequestDtos;
using BankingApp.API.DTOs.ResponseDtos;

namespace BankingApp.API.Services.Interfaces
{
    public interface ITransactionService
    {
        Task<List<TransactionResponseDto>> GetAllAsync();
        Task<TransactionResponseDto> GetByIdAsync(int id);
        Task<List<TransactionResponseDto>> GetByAccountIdAsync(int accountId);
        Task<List<TransactionResponseDto>> GetByDateRangeAsync(int accountId, DateTime start, DateTime end);
        Task<TransactionResponseDto> CreateAsync(TransactionRequestDto dto);
        Task TransferAsync(int fromAccountId, int toAccountId, decimal amount);
        Task DeleteAsync(int id);
        Task TransferByIBANAsync(int fromAccountId, string toIBAN, decimal amount, string description = "");
    }
}