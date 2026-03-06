using BankingApp.API.Entities;

namespace BankingApp.API.Services.Interfaces
{
    public interface ITransactionService : IBaseService<Transaction>
    {
        Task<List<Transaction>> GetByAccountIdAsync(int accountId);
        Task<List<Transaction>> GetByDateRangeAsync(int accountId, DateTime start, DateTime end);
        Task TransferAsync(int fromAccountId, int toAccountId, decimal amount);
    }
}
