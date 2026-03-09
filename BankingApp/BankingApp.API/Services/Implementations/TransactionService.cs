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
        private readonly IMapper _mapper;

        public TransactionService(
            ITransactionRepository transactionRepository,
            IAccountService accountService,
            IMapper mapper)
        {
            _transactionRepository = transactionRepository;
            _accountService = accountService;
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
                TransactionType = TransactionType.Transfer,
                Status = TransactionStatus.Completed
            });
        }

        public async Task DeleteAsync(int id)
        {
            var transaction = await _transactionRepository.GetByIdAsync(id)
                ?? throw new NotFoundException(ErrorMessages.TransactionNotFound);
            await _transactionRepository.DeleteAsync(transaction.Id);
        }
    }
}