using BankingApp.API.Entities;
using BankingApp.API.Exceptions;
using BankingApp.API.Exceptions.Custom;
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
        {
            var notifications = await _notificationRepository.GetByUserIdAsync(userId);

            if (notifications == null || !notifications.Any())
                throw new NotFoundException(ErrorMessages.NotificationNotFound);

            return notifications;
        }

        public async Task<List<Notification>> GetUnreadByUserIdAsync(int userId)
        {
            var notifications = await _notificationRepository.GetUnreadByUserIdAsync(userId);

            if (notifications == null || !notifications.Any())
                throw new NotFoundException(ErrorMessages.NotificationNotFound);

            return notifications;
        }
    }
}