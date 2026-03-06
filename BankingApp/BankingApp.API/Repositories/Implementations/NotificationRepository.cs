using BankingApp.API.Data;
using BankingApp.API.Entities;
using BankingApp.API.Repositories.Interfaces;
using Microsoft.EntityFrameworkCore;

namespace BankingApp.API.Repositories.Implementations
{
    public class NotificationRepository : BaseRepository<Notification>, INotificationRepository
    {
        public NotificationRepository(AppDbContext context) : base(context) { }

        public async Task<List<Notification>> GetByUserIdAsync(int userId)
            => await _context.Notifications.Where(n => n.UserId == userId).ToListAsync();

        public async Task<List<Notification>> GetUnreadByUserIdAsync(int userId)
            => await _context.Notifications
                .Where(n => n.UserId == userId && !n.IsRead)
                .ToListAsync();
    }
}
