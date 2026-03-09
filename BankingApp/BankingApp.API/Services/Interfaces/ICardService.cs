using BankingApp.API.DTOs.RequestDtos;
using BankingApp.API.DTOs.ResponseDtos;

namespace BankingApp.API.Services.Interfaces
{
    public interface ICardService
    {
        Task<List<CardResponseDto>> GetAllAsync();
        Task<CardResponseDto> GetByIdAsync(int id);
        Task<List<CardResponseDto>> GetByAccountIdAsync(int accountId);
        Task<CardResponseDto> GetByCardNumberAsync(string cardNumber);
        Task<CardResponseDto> CreateAsync(CardRequestDto dto);
        Task<CardResponseDto> UpdateAsync(int id, CardRequestDto dto);
        Task DeleteAsync(int id);
        Task ToggleCardAsync(int cardId);
    }
}