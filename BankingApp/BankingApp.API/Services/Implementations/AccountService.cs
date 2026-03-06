using BankingApp.API.Entities;
using BankingApp.API.Repositories.Interfaces;
using BankingApp.API.Services.Interfaces;

namespace BankingApp.API.Services.Implementations
{
    public class AccountService : BaseService<Account>, IAccountService
    {
        private readonly IAccountRepository _accountRepository;

        public AccountService(IAccountRepository accountRepository) : base(accountRepository)
        {
            _accountRepository = accountRepository;
        }

        public async Task<List<Account>> GetByUserIdAsync(int userId)
            => await _accountRepository.GetByUserIdAsync(userId);

        public async Task<Account?> GetByIBANAsync(string iban)
            => await _accountRepository.GetByIBANAsync(iban);

        public async Task<bool> ExistsByIBANAsync(string iban)
            => await _accountRepository.ExistsByIBANAsync(iban);
    }
}
