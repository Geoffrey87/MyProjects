using BankingApp.API.DTOs.RequestDtos;
using BankingApp.API.DTOs.ResponseDtos;

namespace BankingApp.API.Services.Interfaces
{
    public interface IAuditLogService
    {
        Task<List<AuditLogResponseDto>> GetAllAsync();
        Task<AuditLogResponseDto> GetByIdAsync(int id);
        Task<List<AuditLogResponseDto>> GetByUserIdAsync(int userId);
        Task LogAsync(int userId, string action, string entity, string ipAddress, string details);
        Task DeleteAsync(int id);
    }
}