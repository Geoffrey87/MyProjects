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
        Task<CardResponseDto> CreateAutomaticDebitCardAsync(int accountId, string cardHolderName);
        Task<CardResponseDto> RequestCardAsync(CardRequestByClientDto dto);
        Task<CardResponseDto> ApproveCardAsync(int cardId);
        Task<CardResponseDto> RejectCardAsync(int cardId);
        Task<CardResponseDto> UpdateAsync(int id, CardRequestDto dto);
        Task DeleteAsync(int id);
        Task<CardResponseDto> ToggleCardAsync(int cardId);
    }
}