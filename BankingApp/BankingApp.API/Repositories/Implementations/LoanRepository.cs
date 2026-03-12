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
            => await _context.Loans
                .Include(l => l.LoanStatus)
                .Where(l => l.UserId == userId)
                .ToListAsync();

        public async Task<List<Loan>> GetAllAsync()
            => await _context.Loans
                .Include(l => l.LoanStatus)
                .ToListAsync();

        public async Task<Loan?> GetByIdAsync(int id)
            => await _context.Loans
                .Include(l => l.LoanStatus)
                .FirstOrDefaultAsync(l => l.Id == id);

        public async Task<LoanStatus?> GetStatusByNameAsync(string name)
            => await _context.LoanStatuses.FirstOrDefaultAsync(l => l.Name == name);
    }
}