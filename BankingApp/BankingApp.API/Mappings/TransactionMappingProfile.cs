using AutoMapper;
using BankingApp.API.DTOs.RequestDtos;
using BankingApp.API.DTOs.ResponseDtos;
using BankingApp.API.Entities;

namespace BankingApp.API.Mappings
{
    public class TransactionMappingProfile : Profile
    {
        public TransactionMappingProfile()
        {
            /// <summary>
            /// DtoToDomain: TransactionRequestDto -> Transaction
            /// </summary>
            CreateMap<TransactionRequestDto, Transaction>();

            /// <summary>
            /// DomainToDto: Transaction -> TransactionResponseDto
            /// </summary>
            CreateMap<Transaction, TransactionResponseDto>()
                .ForMember(dest => dest.TransactionType, opt => opt.MapFrom(src => src.TransactionType.ToString()))
                .ForMember(dest => dest.Status, opt => opt.MapFrom(src => src.Status.ToString()));
        }
    }
}