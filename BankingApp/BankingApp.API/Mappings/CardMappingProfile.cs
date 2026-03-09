using AutoMapper;
using BankingApp.API.DTOs.RequestDtos;
using BankingApp.API.DTOs.ResponseDtos;
using BankingApp.API.Entities;

namespace BankingApp.API.Mappings
{
    public class CardMappingProfile : Profile
    {
        public CardMappingProfile()
        {
            /// <summary>
            /// DtoToDomain: CardRequestDto -> Card
            /// </summary>
            CreateMap<CardRequestDto, Card>();

            /// <summary>
            /// DomainToDto: Card -> CardResponseDto
            /// </summary>
            CreateMap<Card, CardResponseDto>()
                .ForMember(dest => dest.CardType, opt => opt.MapFrom(src => src.CardType.ToString()));
        }
    }
}
