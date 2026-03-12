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
    public class CardService : ICardService
    {
        private readonly ICardRepository _cardRepository;
        private readonly IAccountRepository _accountRepository;
        private readonly IMapper _mapper;

        public CardService(ICardRepository cardRepository, IAccountRepository accountRepository, IMapper mapper)
        {
            _cardRepository = cardRepository;
            _accountRepository = accountRepository;
            _mapper = mapper;
        }

        public async Task<List<CardResponseDto>> GetAllAsync()
        {
            var cards = await _cardRepository.GetAllAsync();
            return _mapper.Map<List<CardResponseDto>>(cards);
        }

        public async Task<CardResponseDto> GetByIdAsync(int id)
        {
            var card = await _cardRepository.GetByIdAsync(id)
                ?? throw new NotFoundException(ErrorMessages.CardNotFound);
            return _mapper.Map<CardResponseDto>(card);
        }

        public async Task<List<CardResponseDto>> GetByAccountIdAsync(int accountId)
        {
            var cards = await _cardRepository.GetByAccountIdAsync(accountId);
            return _mapper.Map<List<CardResponseDto>>(cards);
        }

        public async Task<CardResponseDto> GetByCardNumberAsync(string cardNumber)
        {
            if (string.IsNullOrEmpty(cardNumber))
                throw new BadRequestException(ErrorMessages.CardNotFound);

            var card = await _cardRepository.GetByCardNumberAsync(cardNumber)
                ?? throw new NotFoundException(ErrorMessages.CardNotFound);
            return _mapper.Map<CardResponseDto>(card);
        }

        public async Task<CardResponseDto> CreateAsync(CardRequestDto dto)
        {
            var card = _mapper.Map<Card>(dto);
            card.CardNumber = GenerateCardNumber();
            card.Status = CardStatus.Active;
            await _cardRepository.AddAsync(card);
            return _mapper.Map<CardResponseDto>(card);
        }

        // Chamado automaticamente ao criar conta
        public async Task<CardResponseDto> CreateAutomaticDebitCardAsync(int accountId, string cardHolderName)
        {
            var card = new Card
            {
                AccountId = accountId,
                CardHolderName = cardHolderName,
                CardType = CardType.Debit,
                CardNumber = GenerateCardNumber(),
                ExpiryDate = DateOnly.FromDateTime(DateTime.UtcNow.AddYears(4)),
                DailyLimit = 1000,
                IsActive = true,
                Status = CardStatus.Active
            };

            await _cardRepository.AddAsync(card);
            return _mapper.Map<CardResponseDto>(card);
        }

        // Cliente pede novo cartão — fica Pending até Admin aprovar
        public async Task<CardResponseDto> RequestCardAsync(CardRequestByClientDto dto)
        {
            var account = await _accountRepository.GetByIdAsync(dto.AccountId)
                ?? throw new NotFoundException(ErrorMessages.AccountNotFound);

            var card = new Card
            {
                AccountId = dto.AccountId,
                CardHolderName = account.User != null
                    ? $"{account.User.FirstName} {account.User.LastName}"
                    : "Account Holder",
                CardType = dto.CardType,
                CardNumber = GenerateCardNumber(),
                ExpiryDate = DateOnly.FromDateTime(DateTime.UtcNow.AddYears(4)),
                DailyLimit = dto.DailyLimit,
                IsActive = false,
                Status = CardStatus.Pending
            };

            await _cardRepository.AddAsync(card);
            return _mapper.Map<CardResponseDto>(card);
        }

        // Admin aprova pedido de cartão
        public async Task<CardResponseDto> ApproveCardAsync(int cardId)
        {
            var card = await _cardRepository.GetByIdAsync(cardId)
                ?? throw new NotFoundException(ErrorMessages.CardNotFound);

            if (card.Status != CardStatus.Pending)
                throw new BadRequestException(ErrorMessages.CardNotPending);

            card.Status = CardStatus.Active;
            card.IsActive = true;
            await _cardRepository.UpdateAsync(card);
            return _mapper.Map<CardResponseDto>(card);
        }

        // Admin rejeita pedido de cartão
        public async Task<CardResponseDto> RejectCardAsync(int cardId)
        {
            var card = await _cardRepository.GetByIdAsync(cardId)
                ?? throw new NotFoundException(ErrorMessages.CardNotFound);

            if (card.Status != CardStatus.Pending)
                throw new BadRequestException(ErrorMessages.CardNotPending);

            card.Status = CardStatus.Rejected;
            card.IsActive = false;
            await _cardRepository.UpdateAsync(card);
            return _mapper.Map<CardResponseDto>(card);
        }

        public async Task<CardResponseDto> UpdateAsync(int id, CardRequestDto dto)
        {
            var card = await _cardRepository.GetByIdAsync(id)
                ?? throw new NotFoundException(ErrorMessages.CardNotFound);
            _mapper.Map(dto, card);
            await _cardRepository.UpdateAsync(card);
            return _mapper.Map<CardResponseDto>(card);
        }

        public async Task DeleteAsync(int id)
        {
            var card = await _cardRepository.GetByIdAsync(id)
                ?? throw new NotFoundException(ErrorMessages.CardNotFound);
            await _cardRepository.DeleteAsync(card.Id);
        }

        public async Task<CardResponseDto> ToggleCardAsync(int cardId)
        {
            var card = await _cardRepository.GetByIdAsync(cardId)
                ?? throw new NotFoundException(ErrorMessages.CardNotFound);

            if (card.Status == CardStatus.Pending || card.Status == CardStatus.Rejected)
                throw new BadRequestException(ErrorMessages.CardNotActive);

            card.IsActive = !card.IsActive;
            card.Status = card.IsActive ? CardStatus.Active : CardStatus.Inactive;
            await _cardRepository.UpdateAsync(card);
            return _mapper.Map<CardResponseDto>(card);
        }

        // Gera número de cartão único
        private static string GenerateCardNumber()
        {
            var rng = new Random();
            return string.Join("-", Enumerable.Range(0, 4)
                .Select(_ => rng.Next(1000, 9999).ToString()));
        }
    }
}