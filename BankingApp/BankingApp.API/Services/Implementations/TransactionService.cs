using AutoMapper;
using BankingApp.API.DTOs.RequestDtos;
using BankingApp.API.DTOs.ResponseDtos;
using BankingApp.API.Entities;
using BankingApp.API.Enums;
using BankingApp.API.Exceptions;
using BankingApp.API.Exceptions.Custom;
using BankingApp.API.Repositories.Interfaces;
using BankingApp.API.Services.Interfaces;

namespace BankingApp.API.Services.Implementations
{
    public class TransactionService : ITransactionService
    {
        private readonly ITransactionRepository _transactionRepository;
        private readonly IAccountService _accountService;
        private readonly IAccountRepository _accountRepository;
        private readonly INotificationRepository _notificationRepository;
        private readonly IAuditLogService _auditLogService;
        private readonly IMapper _mapper;

        public TransactionService(
            ITransactionRepository transactionRepository,
            IAccountService accountService,
            IAccountRepository accountRepository,
            INotificationRepository notificationRepository,
            IAuditLogService auditLogService,
        IMapper mapper)
        {
            _transactionRepository = transactionRepository;
            _accountService = accountService;
            _accountRepository = accountRepository;
            _notificationRepository = notificationRepository;
            _auditLogService = auditLogService;
            _mapper = mapper;
        }

        public async Task<List<TransactionResponseDto>> GetAllAsync()
        {
            var transactions = await _transactionRepository.GetAllAsync();
            return _mapper.Map<List<TransactionResponseDto>>(transactions);
        }

        public async Task<TransactionResponseDto> GetByIdAsync(int id)
        {
            var transaction = await _transactionRepository.GetByIdAsync(id)
                ?? throw new NotFoundException(ErrorMessages.TransactionNotFound);
            return _mapper.Map<TransactionResponseDto>(transaction);
        }

        public async Task<List<TransactionResponseDto>> GetByAccountIdAsync(int accountId)
        {
            var transactions = await _transactionRepository.GetByAccountIdAsync(accountId);
            return _mapper.Map<List<TransactionResponseDto>>(transactions);
        }

        public async Task<List<TransactionResponseDto>> GetByDateRangeAsync(int accountId, DateTime start, DateTime end)
        {
            var transactions = await _transactionRepository.GetByDateRangeAsync(accountId, start, end);
            return _mapper.Map<List<TransactionResponseDto>>(transactions);
        }

        public async Task<TransactionResponseDto> CreateAsync(TransactionRequestDto dto)
        {
            var transaction = _mapper.Map<Transaction>(dto);
            await _transactionRepository.AddAsync(transaction);
            return _mapper.Map<TransactionResponseDto>(transaction);
        }

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
                Currency = "EUR",
                TransactionType = TransactionType.Transfer,
                Status = TransactionStatus.Completed
            });

            // Notifica o utilizador que enviou
            var fromAccount = await _accountRepository.GetByIdAsync(fromAccountId);
            var notificationType = await _notificationRepository.GetTypeByNameAsync("Transaction");

            if (fromAccount != null && notificationType != null)
            {
                await _notificationRepository.AddAsync(new Notification
                {
                    UserId = fromAccount.UserId,
                    Title = "Transfer Completed",
                    Message = $"Transfer of {amount}€ completed successfully.",
                    NotificationTypeId = notificationType.Id,
                    IsRead = false
                });
            }

            // Notifica o utilizador que recebeu
            var toAccount = await _accountRepository.GetByIdAsync(toAccountId);

            if (toAccount != null && notificationType != null)
            {
                await _notificationRepository.AddAsync(new Notification
                {
                    UserId = toAccount.UserId,
                    Title = "Transfer Received",
                    Message = $"You received a transfer of {amount}€.",
                    NotificationTypeId = notificationType.Id,
                    IsRead = false
                });
            }
            // Regista log da transferência
            if (fromAccount != null)
            {
                await _auditLogService.LogAsync(
                    fromAccount.UserId,
                    "Transfer",
                    "Transaction",
                    "N/A",
                    $"Transferred {amount}€ from account {fromAccountId} to account {toAccountId}"
                );
            }
        }

        public async Task DeleteAsync(int id)
        {
            var transaction = await _transactionRepository.GetByIdAsync(id)
                ?? throw new NotFoundException(ErrorMessages.TransactionNotFound);
            await _transactionRepository.DeleteAsync(transaction.Id);
        }
        public async Task TransferByIBANAsync(int fromAccountId, string toIBAN, decimal amount, string description = "")
        {
            var normalizedIBAN = toIBAN.Replace(" ", "").ToUpper();

            var toAccount = await _accountRepository.GetByIBANAsync(normalizedIBAN)
                ?? throw new NotFoundException(ErrorMessages.AccountNotFound);

            if (fromAccountId == toAccount.Id)
                throw new BadRequestException(ErrorMessages.InvalidTransfer);

            await TransferAsync(fromAccountId, toAccount.Id, amount);
        }
    }
}