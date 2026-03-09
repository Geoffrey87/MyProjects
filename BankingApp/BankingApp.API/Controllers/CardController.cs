using BankingApp.API.DTOs.RequestDtos;
using BankingApp.API.DTOs.ResponseDtos;
using BankingApp.API.Services.Interfaces;
using Microsoft.AspNetCore.Mvc;

namespace BankingApp.API.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class CardController : ControllerBase
    {
        private readonly ICardService _cardService;

        public CardController(ICardService cardService)
        {
            _cardService = cardService;
        }

        /// <summary>
        /// Get all cards by account ID
        /// </summary>
        [HttpGet("account/{accountId}")]
        public async Task<ActionResult<List<CardResponseDto>>> GetByAccountId(int accountId)
            => Ok(await _cardService.GetByAccountIdAsync(accountId));

        /// <summary>
        /// Get card by ID
        /// </summary>
        [HttpGet("{id}")]
        public async Task<ActionResult<CardResponseDto>> GetById(int id)
            => Ok(await _cardService.GetByIdAsync(id));

        /// <summary>
        /// Get card by card number
        /// </summary>
        [HttpGet("number/{cardNumber}")]
        public async Task<ActionResult<CardResponseDto>> GetByCardNumber(string cardNumber)
            => Ok(await _cardService.GetByCardNumberAsync(cardNumber));

        /// <summary>
        /// Create new card
        /// </summary>
        [HttpPost]
        public async Task<ActionResult<CardResponseDto>> Create([FromBody] CardRequestDto dto)
        {
            var card = await _cardService.CreateAsync(dto);
            return CreatedAtAction(nameof(GetById), new { id = card.Id }, card);
        }

        /// <summary>
        /// Update card
        /// </summary>
        [HttpPut("{id}")]
        public async Task<ActionResult<CardResponseDto>> Update(int id, [FromBody] CardRequestDto dto)
            => Ok(await _cardService.UpdateAsync(id, dto));

        /// <summary>
        /// Delete card
        /// </summary>
        [HttpDelete("{id}")]
        public async Task<ActionResult> Delete(int id)
        {
            await _cardService.DeleteAsync(id);
            return NoContent();
        }
    }
}