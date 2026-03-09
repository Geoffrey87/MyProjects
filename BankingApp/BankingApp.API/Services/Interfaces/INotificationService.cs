using BankingApp.API.DTOs.RequestDtos;
using BankingApp.API.DTOs.ResponseDtos;

namespace BankingApp.API.Services.Interfaces
{
    public interface INotificationService
    {
        Task<List<NotificationResponseDto>> GetAllAsync();
        Task<NotificationResponseDto> GetByIdAsync(int id);
        Task<List<NotificationResponseDto>> GetByUserIdAsync(int userId);
        Task<List<NotificationResponseDto>> GetUnreadByUserIdAsync(int userId);
        Task<NotificationResponseDto> CreateAsync(NotificationRequestDto dto);
        Task<NotificationResponseDto> UpdateAsync(int id, NotificationRequestDto dto);
        Task MarkAsReadAsync(int id);
        Task DeleteAsync(int id);
    }
}