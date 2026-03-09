using AutoMapper;
using BankingApp.API.DTOs.ResponseDtos;
using BankingApp.API.Entities;

namespace BankingApp.API.Mappings
{
    public class ServiceMappingProfile : Profile
    {
        public ServiceMappingProfile()
        {
            /// <summary>
            /// DomainToDto: Service -> ServiceResponseDto
            /// </summary>
            CreateMap<Service, ServiceResponseDto>();
        }
    }
}