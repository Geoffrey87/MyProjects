using BankingApp.API.Entities;
using BankingApp.API.Exceptions;
using BankingApp.API.Exceptions.Custom;
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

        public async Task DepositAsync(int accountId, decimal amount)
        {
            var account = await _accountRepository.GetByIdAsync(accountId)
                ?? throw new NotFoundException(ErrorMessages.AccountNotFound);

            if (amount <= 0)
                throw new BadRequestException(ErrorMessages.InvalidAmount);

            account.Balance += amount;
            await _accountRepository.UpdateAsync(account);
        }

        public async Task WithdrawAsync(int accountId, decimal amount)
        {
            var account = await _accountRepository.GetByIdAsync(accountId)
                ?? throw new NotFoundException(ErrorMessages.AccountNotFound);

            if (amount <= 0)
                throw new BadRequestException(ErrorMessages.InvalidAmount);

            if (account.Balance < amount)
                throw new BadRequestException(ErrorMessages.InsufficientFunds);

            account.Balance -= amount;
            await _accountRepository.UpdateAsync(account);
        }

        public async Task<decimal> GetBalanceAsync(int accountId)
        {
            var account = await _accountRepository.GetByIdAsync(accountId)
                ?? throw new NotFoundException(ErrorMessages.AccountNotFound);

            return account.Balance;
        }
    }
}