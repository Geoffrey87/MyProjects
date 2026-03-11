using BankingApp.API.Data;
using BankingApp.API.Entities;
using BankingApp.API.Enums;
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

    public async Task<NotificationType?> GetTypeByNameAsync(string name)
    => await _context.NotificationTypes.FirstOrDefaultAsync(n => n.Name == name);

        public async Task<List<int>> GetAdminUserIdsAsync()
            => await _context.Users
                .Where(u => u.Role == UserRole.Admin)
                .Select(u => u.Id)
                .ToListAsync();
    }
}