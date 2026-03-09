using BankingApp.API.DTOs.RequestDtos;
using BankingApp.API.DTOs.ResponseDtos;

namespace BankingApp.API.Services.Interfaces
{
    public interface IBeneficiaryService
    {
        Task<List<BeneficiaryResponseDto>> GetAllAsync();
        Task<BeneficiaryResponseDto> GetByIdAsync(int id);
        Task<List<BeneficiaryResponseDto>> GetByUserIdAsync(int userId);
        Task<BeneficiaryResponseDto> CreateAsync(BeneficiaryRequestDto dto);
        Task<BeneficiaryResponseDto> UpdateAsync(int id, BeneficiaryRequestDto dto);
        Task DeleteAsync(int id);
        Task<bool> ExistsByIBANAsync(int userId, string iban);
    }
}