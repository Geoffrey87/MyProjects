using AutoMapper;
using BankingApp.API.DTOs.RequestDtos;
using BankingApp.API.DTOs.ResponseDtos;
using BankingApp.API.Entities;

namespace BankingApp.API.Mappings
{
    public class NotificationMappingProfile : Profile
    {
        public NotificationMappingProfile()
        {
            /// <summary>
            /// DtoToDomain: NotificationRequestDto -> Notification
            /// </summary>
            CreateMap<NotificationRequestDto, Notification>();

            /// <summary>
            /// DomainToDto: Notification -> NotificationResponseDto
            /// </summary>
            CreateMap<Notification, NotificationResponseDto>()
                .ForMember(dest => dest.NotificationType, opt => opt.MapFrom(src => src.NotificationType.Name));
        }
    }
}