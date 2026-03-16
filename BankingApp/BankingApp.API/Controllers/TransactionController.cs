using BankingApp.API.DTOs.RequestDtos;
using BankingApp.API.DTOs.ResponseDtos;
using BankingApp.API.Services.Interfaces;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace BankingApp.API.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    [Authorize]
    public class TransactionController : ControllerBase
    {
        private readonly ITransactionService _transactionService;

        public TransactionController(ITransactionService transactionService)
        {
            _transactionService = transactionService;
        }

        /// <summary>
        /// Get all transactions by account ID
        /// </summary>
        [HttpGet("account/{accountId}")]
        public async Task<ActionResult<List<TransactionResponseDto>>> GetByAccountId(int accountId)
            => Ok(await _transactionService.GetByAccountIdAsync(accountId));

        /// <summary>
        /// Get transaction by ID
        /// </summary>
        [HttpGet("{id}")]
        public async Task<ActionResult<TransactionResponseDto>> GetById(int id)
            => Ok(await _transactionService.GetByIdAsync(id));

        /// <summary>
        /// Get transactions by date range
        /// </summary>
        [HttpGet("account/{accountId}/range")]
        public async Task<ActionResult<List<TransactionResponseDto>>> GetByDateRange(
            int accountId,
            [FromQuery] DateTime start,
            [FromQuery] DateTime end)
            => Ok(await _transactionService.GetByDateRangeAsync(accountId, start, end));

        /// <summary>
        /// Create new transaction
        /// </summary>
        [HttpPost]
        public async Task<ActionResult<TransactionResponseDto>> Create([FromBody] TransactionRequestDto dto)
        {
            var transaction = await _transactionService.CreateAsync(dto);
            return CreatedAtAction(nameof(GetById), new { id = transaction.Id }, transaction);
        }

        /// <summary>
        /// Transfer between accounts
        /// </summary>
        [HttpPost("transfer")]
        public async Task<ActionResult> Transfer([FromBody] TransactionRequestDto dto)
        {
            await _transactionService.TransferAsync(dto.FromAccountId, dto.ToAccountId!.Value, dto.Amount);
            return Ok();
        }

            /// <summary>
            /// Delete transaction — Admin only
            /// </summary>
            [HttpDelete("{id}")]
        [Authorize(Roles = "Admin")]
        public async Task<ActionResult> Delete(int id)
        {
            await _transactionService.DeleteAsync(id);
            return NoContent();
        }

        [HttpPost("transfer/iban")]
        public async Task<ActionResult> TransferByIBAN([FromBody] TransactionRequestDto dto)
        {
            if (string.IsNullOrEmpty(dto.ToIBAN))
                return BadRequest("ToIBAN is required");

            await _transactionService.TransferByIBANAsync(
                dto.FromAccountId,
                dto.ToIBAN,
                dto.Amount,
                dto.Description
            );
            return Ok();
        }
    }
}