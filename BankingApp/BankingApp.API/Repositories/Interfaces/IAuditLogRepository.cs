using BankingApp.API.Entities;

namespace BankingApp.API.Repositories.Interfaces
{
    public interface IAuditLogRepository : IBaseRepository<AuditLog>
    {
        Task<List<AuditLog>> GetByUserIdAsync(int userId);
    }
}
