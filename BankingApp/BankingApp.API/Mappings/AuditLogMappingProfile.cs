using AutoMapper;
using BankingApp.API.DTOs.RequestDtos;
using BankingApp.API.DTOs.ResponseDtos;
using BankingApp.API.Entities;

namespace BankingApp.API.Mappings
{
    public class AuditLogMappingProfile : Profile
    {
        public AuditLogMappingProfile()
        {
            /// <summary>
            /// DtoToDomain: AuditLogRequestDto -> AuditLog
            /// </summary>
            CreateMap<AuditLogRequestDto, AuditLog>();

            /// <summary>
            /// DomainToDto: AuditLog -> AuditLogResponseDto
            /// </summary>
            CreateMap<AuditLog, AuditLogResponseDto>();
        }
    }
}