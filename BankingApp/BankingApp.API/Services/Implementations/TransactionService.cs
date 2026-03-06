using BankingApp.API.Entities;
using BankingApp.API.Enums;
using BankingApp.API.Repositories.Interfaces;
using BankingApp.API.Services.Interfaces;

namespace BankingApp.API.Services.Implementations
{
    public class TransactionService : BaseService<Transaction>, ITransactionService
    {
        private readonly ITransactionRepository _transactionRepository;
        private readonly IAccountRepository _accountRepository;

        public TransactionService(
            ITransactionRepository transactionRepository,
            IAccountRepository accountRepository) : base(transactionRepository)
        {
            _transactionRepository = transactionRepository;
            _accountRepository = accountRepository;
        }

        public async Task<List<Transaction>> GetByAccountIdAsync(int accountId)
            => await _transactionRepository.GetByAccountIdAsync(accountId);

        public async Task<List<Transaction>> GetByDateRangeAsync(int accountId, DateTime start, DateTime end)
            => await _transactionRepository.GetByDateRangeAsync(accountId, start, end);

        public async Task TransferAsync(int fromAccountId, int toAccountId, decimal amount)
        {
            var fromAccount = await _accountRepository.GetByIdAsync(fromAccountId)
                ?? throw new Exception("Source account not found");

            var toAccount = await _accountRepository.GetByIdAsync(toAccountId)
                ?? throw new Exception("Destination account not found");

            if (fromAccount.Balance < amount)
                throw new Exception("Insufficient funds");

            fromAccount.Balance -= amount;
            toAccount.Balance += amount;

            await _accountRepository.UpdateAsync(fromAccount);
            await _accountRepository.UpdateAsync(toAccount);

            await _transactionRepository.AddAsync(new Transaction
            {
                FromAccountId = fromAccountId,
                ToAccountId = toAccountId,
                Amount = amount,
                TransactionType = TransactionType.Transfer,
                Status = TransactionStatus.Completed
            });
        }
    }
}
