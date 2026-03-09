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
    public class AuditLogService : IAuditLogService
    {
        private readonly IAuditLogRepository _auditLogRepository;
        private readonly IMapper _mapper;

        public AuditLogService(IAuditLogRepository auditLogRepository, IMapper mapper)
        {
            _auditLogRepository = auditLogRepository;
            _mapper = mapper;
        }

        public async Task<List<AuditLogResponseDto>> GetAllAsync()
        {
            var logs = await _auditLogRepository.GetAllAsync();
            return _mapper.Map<List<AuditLogResponseDto>>(logs);
        }

        public async Task<AuditLogResponseDto> GetByIdAsync(int id)
        {
            var log = await _auditLogRepository.GetByIdAsync(id)
                ?? throw new NotFoundException(ErrorMessages.AuditLogNotFound);
            return _mapper.Map<AuditLogResponseDto>(log);
        }

        public async Task<List<AuditLogResponseDto>> GetByUserIdAsync(int userId)
        {
            var logs = await _auditLogRepository.GetByUserIdAsync(userId);
            if (logs == null || !logs.Any())
                throw new NotFoundException(ErrorMessages.AuditLogNotFound);
            return _mapper.Map<List<AuditLogResponseDto>>(logs);
        }

        public async Task LogAsync(int userId, string action, string entity, string ipAddress, string details)
        {
            if (string.IsNullOrEmpty(action) || string.IsNullOrEmpty(entity))
                throw new BadRequestException(ErrorMessages.InvalidAuditLog);

            await _auditLogRepository.AddAsync(new AuditLog
            {
                UserId = userId,
                Action = action,
                Entity = entity,
                IpAddress = ipAddress,
                Details = details
            });
        }

        public async Task DeleteAsync(int id)
        {
            var log = await _auditLogRepository.GetByIdAsync(id)
                ?? throw new NotFoundException(ErrorMessages.AuditLogNotFound);
            await _auditLogRepository.DeleteAsync(log.Id);
        }
    }
}