using AutoMapper;
using BankingApp.API.DTOs.RequestDtos;
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

            ////// <summary>
            /// DtoToDomain
            /// </summary>
            CreateMap<ServiceRequestDto, Service>()
                .ForMember(dest => dest.UserId, opt => opt.Ignore())
                .ForMember(dest => dest.User, opt => opt.Ignore());
        }
    }
}