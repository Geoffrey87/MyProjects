using AutoMapper;
using BankingApp.API.DTOs.RequestDtos;
using BankingApp.API.DTOs.ResponseDtos;
using BankingApp.API.Entities;
using BankingApp.API.Exceptions;
using BankingApp.API.Exceptions.Custom;
using BankingApp.API.Repositories.Interfaces;
using BankingApp.API.Services.Interfaces;

namespace BankingApp.API.Services.Implementations
{
    public class AccountService : IAccountService
    {
        private readonly IAccountRepository _accountRepository;
        private readonly IMapper _mapper;
        private readonly ICardService _cardService;   
        private readonly IUserRepository _userRepository;

        public AccountService(
         IAccountRepository accountRepository,
         ICardService cardService,
         IUserRepository userRepository,
         IMapper mapper)
        {
            _accountRepository = accountRepository;
            _cardService = cardService;
            _userRepository = userRepository;
            _mapper = mapper;
        }

        public async Task<AccountResponseDto> CreateAsync(AccountRequestDto dto)
        {
            await ExistsByIBANAsync(dto.Currency);
            var account = _mapper.Map<Account>(dto);
            await _accountRepository.AddAsync(account);

            // Buscar o utilizador para o nome no cartão
            var user = await _userRepository.GetByIdAsync(dto.UserId)
                ?? throw new NotFoundException(ErrorMessages.UserNotFound);

            // Gerar cartão de débito automaticamente
            await _cardService.CreateAutomaticDebitCardAsync(
                account.Id,
                $"{user.FirstName} {user.LastName}"
            );

            return _mapper.Map<AccountResponseDto>(account);
        }

        public async Task<List<AccountResponseDto>> GetAllAsync()
        {
            var accounts = await _accountRepository.GetAllAsync();
            return _mapper.Map<List<AccountResponseDto>>(accounts);
        }

        public async Task<AccountResponseDto> GetByIdAsync(int id)
        {
            var account = await _accountRepository.GetByIdAsync(id)
                ?? throw new NotFoundException(ErrorMessages.AccountNotFound);
            return _mapper.Map<AccountResponseDto>(account);
        }

        public async Task<List<AccountResponseDto>> GetByUserIdAsync(int userId)
        {
            var accounts = await _accountRepository.GetByUserIdAsync(userId);
            return _mapper.Map<List<AccountResponseDto>>(accounts);
        }

        public async Task<AccountResponseDto> GetByIBANAsync(string iban)
        {
            var account = await _accountRepository.GetByIBANAsync(iban)
                ?? throw new NotFoundException(ErrorMessages.AccountNotFound);
            return _mapper.Map<AccountResponseDto>(account);
        }


        public async Task<AccountResponseDto> UpdateAsync(int id, AccountRequestDto dto)
        {
            var account = await _accountRepository.GetByIdAsync(id)
                ?? throw new NotFoundException(ErrorMessages.AccountNotFound);
            _mapper.Map(dto, account);
            await _accountRepository.UpdateAsync(account);
            return _mapper.Map<AccountResponseDto>(account);
        }

        public async Task DeleteAsync(int id)
        {
            var account = await _accountRepository.GetByIdAsync(id)
                ?? throw new NotFoundException(ErrorMessages.AccountNotFound);
            await _accountRepository.DeleteAsync(account.Id);
        }

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

        public async Task<bool> ExistsByIBANAsync(string iban)
        {
            if (await _accountRepository.ExistsByIBANAsync(iban))
                throw new ConflictException(ErrorMessages.AccountIBANAlreadyExists);
            return false;
        }
    }
}