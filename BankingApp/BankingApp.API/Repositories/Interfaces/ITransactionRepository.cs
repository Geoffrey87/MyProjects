using BankingApp.API.Entities;

namespace BankingApp.API.Repositories.Interfaces
{
    public interface ITransactionRepository : IBaseRepository<Transaction>
    {
        Task<List<Transaction>> GetByAccountIdAsync(int accountId);
        Task<List<Transaction>> GetByDateRangeAsync(int accountId, DateTime start, DateTime end);
    }
}
