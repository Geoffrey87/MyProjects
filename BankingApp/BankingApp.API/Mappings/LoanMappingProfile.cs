using AutoMapper;
using BankingApp.API.DTOs.RequestDtos;
using BankingApp.API.DTOs.ResponseDtos;
using BankingApp.API.Entities;

namespace BankingApp.API.Mappings
{
    public class LoanMappingProfile : Profile
    {
        public LoanMappingProfile()
        {
            /// <summary>
            /// DtoToDomain: LoanRequestDto -> Loan
            /// </summary>
            CreateMap<LoanRequestDto, Loan>();

            /// <summary>
            /// DomainToDto: Loan -> LoanResponseDto
            /// </summary>
            CreateMap<Loan, LoanResponseDto>()
                .ForMember(dest => dest.LoanStatus, opt => opt.MapFrom(src => src.LoanStatus.Name));
        }
    }
}