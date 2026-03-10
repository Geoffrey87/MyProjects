using BankingApp.API.DTOs.RequestDtos;
using BankingApp.API.DTOs.ResponseDtos;
using BankingApp.API.DTOs.User;

namespace BankingApp.API.Services.Interfaces
{
    public interface IUserService
    {
        Task<List<UserResponseDto>> GetAllAsync();
        Task<UserResponseDto> GetByIdAsync(int id);
        Task<UserResponseDto> GetByEmailAsync(string email);
        Task<UserResponseDto> CreateAsync(UserRequestDto dto);
        Task<UserResponseDto> UpdateAsync(int id, UserRequestDto dto);
        Task DeleteAsync(int id);
        Task<bool> ExistsByEmailAsync(string email);
    }
}