using BankingApp.API.Entities;

namespace BankingApp.API.Services.Interfaces
{
    public interface IAccountService : IBaseService<Account>
    {
        Task<List<Account>> GetByUserIdAsync(int userId);
        Task<Account?> GetByIBANAsync(string iban);
        Task<bool> ExistsByIBANAsync(string iban);
    }
}
