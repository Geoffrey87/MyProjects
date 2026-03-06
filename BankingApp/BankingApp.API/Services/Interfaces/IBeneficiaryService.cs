using BankingApp.API.Entities;

namespace BankingApp.API.Services.Interfaces
{
    public interface IBeneficiaryService : IBaseService<Beneficiary>
    {
        Task<List<Beneficiary>> GetByUserIdAsync(int userId);
        Task<bool> ExistsByIBANAsync(int userId, string iban);
    }
}
