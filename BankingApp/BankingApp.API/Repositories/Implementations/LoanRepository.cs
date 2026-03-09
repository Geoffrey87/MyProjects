using BankingApp.API.Data;
using BankingApp.API.Entities;
using BankingApp.API.Repositories.Interfaces;
using Microsoft.EntityFrameworkCore;

namespace BankingApp.API.Repositories.Implementations
{
    public class LoanRepository : BaseRepository<Loan>, ILoanRepository
    {
        public LoanRepository(AppDbContext context) : base(context) { }

        public async Task<List<Loan>> GetByUserIdAsync(int userId)
            => await _context.Loans.Where(l => l.UserId == userId).ToListAsync();

        public async Task<LoanStatus?> GetStatusByNameAsync(string name)
    => await _context.LoanStatuses.FirstOrDefaultAsync(l => l.Name == name);
    }
}
