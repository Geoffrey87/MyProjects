using AutoMapper;
using BankingApp.API.DTOs.RequestDtos;
using BankingApp.API.DTOs.ResponseDtos;
using BankingApp.API.Entities;

namespace BankingApp.API.Mappings
{
    public class AccountMappingProfile : Profile
    {
        public AccountMappingProfile()
        {
            /// <summary>
            /// DtoToDomain: AccountRequestDto -> Account
            /// </summary>
            CreateMap<AccountRequestDto, Account>();

            /// <summary>
            /// DomainToDto: Account -> AccountResponseDto
            /// </summary>
            CreateMap<Account, AccountResponseDto>()
                .ForMember(dest => dest.AccountType, opt => opt.MapFrom(src => src.AccountType.Name));
        }
    }
}