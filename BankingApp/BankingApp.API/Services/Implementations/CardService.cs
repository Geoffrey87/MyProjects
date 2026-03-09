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
    public class CardService : ICardService
    {
        private readonly ICardRepository _cardRepository;
        private readonly IMapper _mapper;

        public CardService(ICardRepository cardRepository, IMapper mapper)
        {
            _cardRepository = cardRepository;
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
            await _cardRepository.AddAsync(card);
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
    }
}