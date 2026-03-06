using BankingApp.API.Entities;
using BankingApp.API.Enums;
using BankingApp.API.Exceptions;
using BankingApp.API.Exceptions.Custom;
using BankingApp.API.Repositories.Interfaces;
using BankingApp.API.Services.Interfaces;

namespace BankingApp.API.Services.Implementations
{
    public class TransactionService : BaseService<Transaction>, ITransactionService
    {
        private readonly ITransactionRepository _transactionRepository;
        private readonly IAccountService _accountService;

        public TransactionService(
            ITransactionRepository transactionRepository,
            IAccountService accountService) : base(transactionRepository)
        {
            _transactionRepository = transactionRepository;
            _accountService = accountService;
        }

        public async Task<List<Transaction>> GetByAccountIdAsync(int accountId)
            => await _transactionRepository.GetByAccountIdAsync(accountId);

        public async Task<List<Transaction>> GetByDateRangeAsync(int accountId, DateTime start, DateTime end)
            => await _transactionRepository.GetByDateRangeAsync(accountId, start, end);

        public async Task TransferAsync(int fromAccountId, int toAccountId, decimal amount)
        {
            if (fromAccountId == toAccountId)
                throw new BadRequestException(ErrorMessages.InvalidTransfer);

            if (amount <= 0)
                throw new BadRequestException(ErrorMessages.InvalidAmount);

            await _accountService.WithdrawAsync(fromAccountId, amount);
            await _accountService.DepositAsync(toAccountId, amount);

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