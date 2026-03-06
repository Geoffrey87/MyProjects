using BankingApp.API.Entities;
using BankingApp.API.Exceptions;
using BankingApp.API.Exceptions.Custom;
using BankingApp.API.Repositories.Interfaces;
using BankingApp.API.Services.Interfaces;

namespace BankingApp.API.Services.Implementations
{
    public class CardService : BaseService<Card>, ICardService
    {
        private readonly ICardRepository _cardRepository;

        public CardService(ICardRepository cardRepository) : base(cardRepository)
        {
            _cardRepository = cardRepository;
        }

        public async Task<List<Card>> GetByAccountIdAsync(int accountId)
            => await _cardRepository.GetByAccountIdAsync(accountId);

        public async Task<Card?> GetByCardNumberAsync(string cardNumber)
        {
            if (string.IsNullOrEmpty(cardNumber))
                throw new BadRequestException(ErrorMessages.InvalidAmount);

            return await _cardRepository.GetByCardNumberAsync(cardNumber)
                ?? throw new NotFoundException(ErrorMessages.CardNotFound);
        }
    }
}