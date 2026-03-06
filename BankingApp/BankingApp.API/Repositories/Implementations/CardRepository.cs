using BankingApp.API.Data;
using BankingApp.API.Entities;
using BankingApp.API.Repositories.Interfaces;
using Microsoft.EntityFrameworkCore;

namespace BankingApp.API.Repositories.Implementations
{
    public class CardRepository : BaseRepository<Card>, ICardRepository
    {
        public CardRepository(AppDbContext context) : base(context) { }

        public async Task<List<Card>> GetByAccountIdAsync(int accountId)
            => await _context.Cards.Where(c => c.AccountId == accountId).ToListAsync();

        public async Task<Card?> GetByCardNumberAsync(string cardNumber)
            => await _context.Cards.FirstOrDefaultAsync(c => c.CardNumber == cardNumber);
    }
}
