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
    public class AccountController : ControllerBase
    {
        private readonly IAccountService _accountService;

        public AccountController(IAccountService accountService)
        {
            _accountService = accountService;
        }

        /// <summary>
        /// Get all accounts by user ID
        /// </summary>
        [HttpGet("user/{userId}")]
        public async Task<ActionResult<List<AccountResponseDto>>> GetByUserId(int userId)
            => Ok(await _accountService.GetByUserIdAsync(userId));

        /// <summary>
        /// Get account by ID
        /// </summary>
        [HttpGet("{id}")]
        public async Task<ActionResult<AccountResponseDto>> GetById(int id)
            => Ok(await _accountService.GetByIdAsync(id));

        /// <summary>
        /// Get account balance
        /// </summary>
        [HttpGet("{id}/balance")]
        public async Task<ActionResult<decimal>> GetBalance(int id)
            => Ok(await _accountService.GetBalanceAsync(id));

        /// <summary>
        /// Create new account — Admin only
        /// </summary>
        [HttpPost]
        [Authorize(Roles = "Admin")]
        public async Task<ActionResult<AccountResponseDto>> Create([FromBody] AccountRequestDto dto)
        {
            var account = await _accountService.CreateAsync(dto);
            return CreatedAtAction(nameof(GetById), new { id = account.Id }, account);
        }

        /// <summary>
        /// Deposit to account
        /// </summary>
        [HttpPost("{id}/deposit")]
        public async Task<ActionResult> Deposit(int id, [FromBody] decimal amount)
        {
            await _accountService.DepositAsync(id, amount);
            return Ok();
        }

        /// <summary>
        /// Withdraw from account
        /// </summary>
        [HttpPost("{id}/withdraw")]
        public async Task<ActionResult> Withdraw(int id, [FromBody] decimal amount)
        {
            await _accountService.WithdrawAsync(id, amount);
            return Ok();
        }

        /// <summary>
        /// Update account — Admin only
        /// </summary>
        [HttpPut("{id}")]
        [Authorize(Roles = "Admin")]
        public async Task<ActionResult<AccountResponseDto>> Update(int id, [FromBody] AccountRequestDto dto)
            => Ok(await _accountService.UpdateAsync(id, dto));

        /// <summary>
        /// Delete account — Admin only
        /// </summary>
        [HttpDelete("{id}")]
        [Authorize(Roles = "Admin")]
        public async Task<ActionResult> Delete(int id)
        {
            await _accountService.DeleteAsync(id);
            return NoContent();
        }
    }
}