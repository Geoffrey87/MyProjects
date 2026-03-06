using BankingApp.API.Data;
using BankingApp.API.Entities;
using BankingApp.API.Repositories.Interfaces;
using Microsoft.EntityFrameworkCore;

namespace BankingApp.API.Repositories.Implementations
{
    public class BeneficiaryRepository : BaseRepository<Beneficiary>, IBeneficiaryRepository
    {
        public BeneficiaryRepository(AppDbContext context) : base(context) { }

        public async Task<List<Beneficiary>> GetByUserIdAsync(int userId)
            => await _context.Beneficiaries.Where(b => b.UserId == userId).ToListAsync();

        public async Task<bool> ExistsByIBANAsync(int userId, string iban)
            => await _context.Beneficiaries.AnyAsync(b => b.UserId == userId && b.IBAN == iban);
    }
}