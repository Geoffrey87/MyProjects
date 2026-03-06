using BankingApp.API.Entities;
using BankingApp.API.Repositories.Interfaces;
using BankingApp.API.Services.Interfaces;

namespace BankingApp.API.Services.Implementations
{
    public class NotificationService : BaseService<Notification>, INotificationService
    {
        private readonly INotificationRepository _notificationRepository;

        public NotificationService(INotificationRepository notificationRepository) : base(notificationRepository)
        {
            _notificationRepository = notificationRepository;
        }

        public async Task<List<Notification>> GetByUserIdAsync(int userId)
            => await _notificationRepository.GetByUserIdAsync(userId);

        public async Task<List<Notification>> GetUnreadByUserIdAsync(int userId)
            => await _notificationRepository.GetUnreadByUserIdAsync(userId);
    }
}