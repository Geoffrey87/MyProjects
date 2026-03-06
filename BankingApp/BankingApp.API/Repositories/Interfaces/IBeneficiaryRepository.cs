using BankingApp.API.Entities;

namespace BankingApp.API.Repositories.Interfaces
{
    public interface IBeneficiaryRepository : IBaseRepository<Beneficiary>
    {
        Task<List<Beneficiary>> GetByUserIdAsync(int userId);
        Task<bool> ExistsByIBANAsync(int userId, string iban);
    }
}
