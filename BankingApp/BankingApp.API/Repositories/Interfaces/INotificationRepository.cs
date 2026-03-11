using BankingApp.API.Entities;

namespace BankingApp.API.Repositories.Interfaces
{
    public interface INotificationRepository : IBaseRepository<Notification>
    {
        Task<List<Notification>> GetByUserIdAsync(int userId);
        Task<List<Notification>> GetUnreadByUserIdAsync(int userId);
        Task<NotificationType?> GetTypeByNameAsync(string name);
        Task<List<int>> GetAdminUserIdsAsync();
    }
}
