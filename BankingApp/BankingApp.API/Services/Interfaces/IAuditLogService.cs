using BankingApp.API.Entities;

namespace BankingApp.API.Services.Interfaces
{
    public interface IAuditLogService : IBaseService<AuditLog>
    {
        Task<List<AuditLog>> GetByUserIdAsync(int userId);
        Task LogAsync(int userId, string action, string entity, string ipAddress, string details);
    }
}