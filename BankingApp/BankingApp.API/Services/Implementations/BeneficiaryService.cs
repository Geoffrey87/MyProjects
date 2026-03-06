using BankingApp.API.Entities;
using BankingApp.API.Exceptions;
using BankingApp.API.Exceptions.Custom;
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
        {
            var beneficiaries = await _beneficiaryRepository.GetByUserIdAsync(userId);

            if (beneficiaries == null || !beneficiaries.Any())
                throw new NotFoundException(ErrorMessages.BeneficiaryNotFound);

            return beneficiaries;
        }

        public async Task<bool> ExistsByIBANAsync(int userId, string iban)
        {
            if (string.IsNullOrEmpty(iban))
                throw new BadRequestException(ErrorMessages.BeneficiaryNotFound);

            var exists = await _beneficiaryRepository.ExistsByIBANAsync(userId, iban);

            if (exists)
                throw new ConflictException(ErrorMessages.BeneficiaryAlreadyExists);

            return exists;
        }
    }
}