using AutoMapper;
using BankingApp.API.DTOs.ResponseDtos;
using BankingApp.API.Entities;
using BankingApp.API.Enums;
using BankingApp.API.Exceptions;
using BankingApp.API.Exceptions.Custom;
using BankingApp.API.Repositories.Interfaces;
using BankingApp.API.Services.Interfaces;

namespace BankingApp.API.Services.Implementations
{
    public class ServiceService : IServiceService
    {
        private readonly IServiceRepository _serviceRepository;
        private readonly IAccountRepository _accountRepository;
        private readonly ITransactionRepository _transactionRepository;
        private readonly IMapper _mapper;

        public ServiceService(
            IServiceRepository serviceRepository,
            IAccountRepository accountRepository,
            ITransactionRepository transactionRepository,
            IMapper mapper)
        {
            _serviceRepository = serviceRepository;
            _accountRepository = accountRepository;
            _transactionRepository = transactionRepository;
            _mapper = mapper;
        }

        public async Task<List<ServiceResponseDto>> GetAllAsync()
        {
            var services = await _serviceRepository.GetAllAsync();
            return _mapper.Map<List<ServiceResponseDto>>(services);
        }

        public async Task<List<ServiceResponseDto>> GetByCategoryAsync(string category)
        {
            var services = await _serviceRepository.GetByCategoryAsync(category);
            return _mapper.Map<List<ServiceResponseDto>>(services);
        }

        public async Task PayServiceAsync(int serviceId, int accountId, decimal? customAmount)
        {
            var service = await _serviceRepository.GetByIdAsync(serviceId)
                ?? throw new NotFoundException(ErrorMessages.ServiceNotFound);

            var account = await _accountRepository.GetByIdAsync(accountId)
                ?? throw new NotFoundException(ErrorMessages.AccountNotFound);

            var amount = service.IsFixedAmount ? service.Amount : customAmount
                ?? throw new BadRequestException(ErrorMessages.InvalidAmount);

            if (amount <= 0)
                throw new BadRequestException(ErrorMessages.InvalidAmount);

            if (account.Balance < amount)
                throw new BadRequestException(ErrorMessages.InsufficientFunds);

            account.Balance -= amount;
            await _accountRepository.UpdateAsync(account);

            await _transactionRepository.AddAsync(new Transaction
            {
                FromAccountId = accountId,
                Amount = amount,
                Currency = account.Currency,
                TransactionType = TransactionType.Payment,
                Status = TransactionStatus.Completed,
                Description = $"Payment: {service.Name}"
            });
        }
    }
}