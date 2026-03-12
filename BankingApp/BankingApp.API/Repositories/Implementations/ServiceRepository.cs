using BankingApp.API.Data;
using BankingApp.API.Entities;
using BankingApp.API.Repositories.Interfaces;
using Microsoft.EntityFrameworkCore;

namespace BankingApp.API.Repositories.Implementations
{
    public class ServiceRepository : BaseRepository<Service>, IServiceRepository
    {
        public ServiceRepository(AppDbContext context) : base(context) { }

        public async Task<List<Service>> GetByUserIdAsync(int userId)
            => await _context.Services
                .Where(s => s.UserId == userId)
                .ToListAsync();

        public async Task<List<Service>> GetByCategoryAsync(int userId, string category)
            => await _context.Services
                .Where(s => s.UserId == userId && s.Category == category)
                .ToListAsync();
    }
}