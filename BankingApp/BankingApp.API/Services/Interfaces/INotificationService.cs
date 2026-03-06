using BankingApp.API.Entities;

namespace BankingApp.API.Services.Interfaces
{
    public interface INotificationService : IBaseService<Notification>
    {
        Task<List<Notification>> GetByUserIdAsync(int userId);
        Task<List<Notification>> GetUnreadByUserIdAsync(int userId);
    }
}