using BankingApp.API.Entities;
using BankingApp.API.Repositories.Interfaces;
using BankingApp.API.Services.Interfaces;

namespace BankingApp.API.Services.Implementations
{
    public class BeneficiaryService : BaseService<Beneficiary>, IBeneficiaryService
    {
        private readonly IBeneficiaryRepository _beneficiaryRepository;

        public BeneficiaryService(IBeneficiaryRepository beneficiaryRepository) : base(beneficiaryRepository)
        {
            _beneficiaryRepository = beneficiaryRepository;
        }

        public async Task<List<Beneficiary>> GetByUserIdAsync(int userId)
            => await _beneficiaryRepository.GetByUserIdAsync(userId);

        public async Task<bool> ExistsByIBANAsync(int userId, string iban)
            => await _beneficiaryRepository.ExistsByIBANAsync(userId, iban);
    }
}
