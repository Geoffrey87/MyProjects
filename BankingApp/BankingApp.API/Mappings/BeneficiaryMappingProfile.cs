using AutoMapper;
using BankingApp.API.DTOs.RequestDtos;
using BankingApp.API.DTOs.ResponseDtos;
using BankingApp.API.Entities;

namespace BankingApp.API.Mappings
{
    public class BeneficiaryMappingProfile : Profile
    {
        public BeneficiaryMappingProfile()
        {
            /// <summary>
            /// DtoToDomain: BeneficiaryRequestDto -> Beneficiary
            /// </summary>
            CreateMap<BeneficiaryRequestDto, Beneficiary>();

            /// <summary>
            /// DomainToDto: Beneficiary -> BeneficiaryResponseDto
            /// </summary>
            CreateMap<Beneficiary, BeneficiaryResponseDto>();
        }
    }
}