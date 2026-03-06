using BankingApp.API.Data;
using BankingApp.API.Entities;
using BankingApp.API.Repositories.Interfaces;
using Microsoft.EntityFrameworkCore;

namespace BankingApp.API.Repositories.Implementations
{
    public class AccountRepository : BaseRepository<Account>, IAccountRepository
    {
        public AccountRepository(AppDbContext context) : base(context) { }

        public async Task<List<Account>> GetByUserIdAsync(int userId)
            => await _context.Accounts.Where(a => a.UserId == userId).ToListAsync();

        public async Task<Account?> GetByIBANAsync(string iban)
            => await _context.Accounts.FirstOrDefaultAsync(a => a.IBAN == iban);

        public async Task<bool> ExistsByIBANAsync(string iban)
            => await _context.Accounts.AnyAsync(a => a.IBAN == iban);
    }
}
