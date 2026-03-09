using BankingApp.API.DTOs.RequestDtos;
using BankingApp.API.DTOs.ResponseDtos;

namespace BankingApp.API.Services.Interfaces
{
    public interface IAccountService
    {
        Task<List<AccountResponseDto>> GetAllAsync();
        Task<AccountResponseDto> GetByIdAsync(int id);
        Task<List<AccountResponseDto>> GetByUserIdAsync(int userId);
        Task<AccountResponseDto> GetByIBANAsync(string iban);
        Task<AccountResponseDto> CreateAsync(AccountRequestDto dto);
        Task<AccountResponseDto> UpdateAsync(int id, AccountRequestDto dto);
        Task DeleteAsync(int id);
        Task DepositAsync(int accountId, decimal amount);
        Task WithdrawAsync(int accountId, decimal amount);
        Task<decimal> GetBalanceAsync(int accountId);
        Task<bool> ExistsByIBANAsync(string iban);
    }
}