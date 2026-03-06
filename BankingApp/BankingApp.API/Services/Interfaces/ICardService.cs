using BankingApp.API.Entities;

namespace BankingApp.API.Services.Interfaces
{
    public interface ICardService : IBaseService<Card>
    {
        Task<List<Card>> GetByAccountIdAsync(int accountId);
        Task<Card?> GetByCardNumberAsync(string cardNumber);
    }
}