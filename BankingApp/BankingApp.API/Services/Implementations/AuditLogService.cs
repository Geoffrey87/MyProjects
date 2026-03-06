using BankingApp.API.Entities;
using BankingApp.API.Repositories.Interfaces;
using BankingApp.API.Services.Interfaces;

namespace BankingApp.API.Services.Implementations
{
    public class AuditLogService : BaseService<AuditLog>, IAuditLogService
    {
        private readonly IAuditLogRepository _auditLogRepository;

        public AuditLogService(IAuditLogRepository auditLogRepository) : base(auditLogRepository)
        {
            _auditLogRepository = auditLogRepository;
        }

        public async Task<List<AuditLog>> GetByUserIdAsync(int userId)
            => await _auditLogRepository.GetByUserIdAsync(userId);

        public async Task LogAsync(int userId, string action, string entity, string ipAddress, string details)
            => await _auditLogRepository.AddAsync(new AuditLog
            {
                UserId = userId,
                Action = action,
                Entity = entity,
                IpAddress = ipAddress,
                Details = details
            });
    }
}
