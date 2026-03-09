using AutoMapper;
using BankingApp.API.DTOs.RequestDtos;
using BankingApp.API.DTOs.ResponseDtos;
using BankingApp.API.DTOs.User;
using BankingApp.API.Entities;

namespace BankingApp.API.Mappings
{
    public class UserMappingProfile : Profile
    {
        public UserMappingProfile()
        {
            /// <summary>
            /// DtoToDomain: UserRequestDto -> User
            /// </summary>
            CreateMap<UserRequestDto, User>()
                .ForMember(dest => dest.PasswordHash, opt => opt.Ignore());

            /// <summary>
            /// DomainToDto: User -> UserResponseDto
            /// </summary>
            CreateMap<User, UserResponseDto>()
                .ForMember(dest => dest.Role, opt => opt.MapFrom(src => src.Role.ToString()));
        }
    }
}