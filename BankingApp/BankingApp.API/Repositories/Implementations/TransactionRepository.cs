using BankingApp.API.Data;
using BankingApp.API.Entities;
using BankingApp.API.Repositories.Interfaces;
using Microsoft.EntityFrameworkCore;

namespace BankingApp.API.Repositories.Implementations
{
    public class TransactionRepository : BaseRepository<Transaction>, ITransactionRepository
    {
        public TransactionRepository(AppDbContext context) : base(context) { }

        public async Task<List<Transaction>> GetByAccountIdAsync(int accountId)
            => await _context.Transactions
                .Where(t => t.FromAccountId == accountId || t.ToAccountId == accountId)
                .ToListAsync();

        public async Task<List<Transaction>> GetByDateRangeAsync(int accountId, DateTime start, DateTime end)
            => await _context.Transactions
                .Where(t => (t.FromAccountId == accountId || t.ToAccountId == accountId)
                    && t.CreatedAt >= start && t.CreatedAt <= end)
                .ToListAsync();

        public async Task<Account?> GetByIBANAsync(string iban)
    => await _context.Accounts
        .FirstOrDefaultAsync(a => a.IBAN == iban);
    }
}
