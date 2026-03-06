using BankingApp.API.Entities;

namespace BankingApp.API.Repositories.Interfaces
{
    public interface ICardRepository : IBaseRepository<Card>
    {
        Task<List<Card>> GetByAccountIdAsync(int accountId);
        Task<Card?> GetByCardNumberAsync(string cardNumber);
    }
}
