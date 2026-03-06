using BankingApp.API.Entities;

namespace BankingApp.API.Services.Interfaces
{
    public interface IAccountService : IBaseService<Account>
    {
        Task<List<Account>> GetByUserIdAsync(int userId);
        Task<Account?> GetByIBANAsync(string iban);
        Task<bool> ExistsByIBANAsync(string iban);

        Task DepositAsync(int accountId, decimal amount);
        Task WithdrawAsync(int accountId, decimal amount);
        Task<decimal> GetBalanceAsync(int accountId);
    }
}
