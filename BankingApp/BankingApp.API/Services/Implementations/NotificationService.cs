using AutoMapper;
using BankingApp.API.DTOs.RequestDtos;
using BankingApp.API.DTOs.ResponseDtos;
using BankingApp.API.Entities;
using BankingApp.API.Exceptions;
using BankingApp.API.Exceptions.Custom;
using BankingApp.API.Repositories.Interfaces;
using BankingApp.API.Services.Interfaces;

namespace BankingApp.API.Services.Implementations
{
    public class NotificationService : INotificationService
    {
        private readonly INotificationRepository _notificationRepository;
        private readonly IMapper _mapper;

        public NotificationService(INotificationRepository notificationRepository, IMapper mapper)
        {
            _notificationRepository = notificationRepository;
            _mapper = mapper;
        }

        public async Task<List<NotificationResponseDto>> GetAllAsync()
        {
            var notifications = await _notificationRepository.GetAllAsync();
            return _mapper.Map<List<NotificationResponseDto>>(notifications);
        }

        public async Task<NotificationResponseDto> GetByIdAsync(int id)
        {
            var notification = await _notificationRepository.GetByIdAsync(id)
                ?? throw new NotFoundException(ErrorMessages.NotificationNotFound);
            return _mapper.Map<NotificationResponseDto>(notification);
        }

        public async Task<List<NotificationResponseDto>> GetByUserIdAsync(int userId)
        {
            var notifications = await _notificationRepository.GetByUserIdAsync(userId);
            if (notifications == null || !notifications.Any())
                throw new NotFoundException(ErrorMessages.NotificationNotFound);
            return _mapper.Map<List<NotificationResponseDto>>(notifications);
        }

        public async Task<List<NotificationResponseDto>> GetUnreadByUserIdAsync(int userId)
        {
            var notifications = await _notificationRepository.GetUnreadByUserIdAsync(userId);
            if (notifications == null || !notifications.Any())
                throw new NotFoundException(ErrorMessages.NotificationNotFound);
            return _mapper.Map<List<NotificationResponseDto>>(notifications);
        }

        public async Task<NotificationResponseDto> CreateAsync(NotificationRequestDto dto)
        {
            var notification = _mapper.Map<Notification>(dto);
            await _notificationRepository.AddAsync(notification);
            return _mapper.Map<NotificationResponseDto>(notification);
        }

        public async Task<NotificationResponseDto> UpdateAsync(int id, NotificationRequestDto dto)
        {
            var notification = await _notificationRepository.GetByIdAsync(id)
                ?? throw new NotFoundException(ErrorMessages.NotificationNotFound);
            _mapper.Map(dto, notification);
            await _notificationRepository.UpdateAsync(notification);
            return _mapper.Map<NotificationResponseDto>(notification);
        }

        public async Task MarkAsReadAsync(int id)
        {
            var notification = await _notificationRepository.GetByIdAsync(id)
                ?? throw new NotFoundException(ErrorMessages.NotificationNotFound);
            notification.IsRead = true;
            await _notificationRepository.UpdateAsync(notification);
        }

        public async Task DeleteAsync(int id)
        {
            var notification = await _notificationRepository.GetByIdAsync(id)
                ?? throw new NotFoundException(ErrorMessages.NotificationNotFound);
            await _notificationRepository.DeleteAsync(notification.Id);
        }
    }
}