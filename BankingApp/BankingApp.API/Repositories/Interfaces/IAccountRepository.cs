using BankingApp.API.Entities;

namespace BankingApp.API.Repositories.Interfaces
{
    public interface IAccountRepository : IBaseRepository<Account>
    {
        Task<List<Account>> GetByUserIdAsync(int userId);
        Task<Account?> GetByIBANAsync(string iban);
        Task<bool> ExistsByIBANAsync(string iban);
    }
}
