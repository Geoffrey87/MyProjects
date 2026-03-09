using BankingApp.API.DTOs.RequestDtos;
using BankingApp.API.DTOs.ResponseDtos;
using BankingApp.API.Services.Interfaces;
using Microsoft.AspNetCore.Mvc;

namespace BankingApp.API.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class LoanController : ControllerBase
    {
        private readonly ILoanService _loanService;

        public LoanController(ILoanService loanService)
        {
            _loanService = loanService;
        }

        /// <summary>
        /// Get all loans by user ID
        /// </summary>
        [HttpGet("user/{userId}")]
        public async Task<ActionResult<List<LoanResponseDto>>> GetByUserId(int userId)
            => Ok(await _loanService.GetByUserIdAsync(userId));

        /// <summary>
        /// Get loan by ID
        /// </summary>
        [HttpGet("{id}")]
        public async Task<ActionResult<LoanResponseDto>> GetById(int id)
            => Ok(await _loanService.GetByIdAsync(id));

        /// <summary>
        /// Create new loan
        /// </summary>
        [HttpPost]
        public async Task<ActionResult<LoanResponseDto>> Create([FromBody] LoanRequestDto dto)
        {
            var loan = await _loanService.CreateAsync(dto);
            return CreatedAtAction(nameof(GetById), new { id = loan.Id }, loan);
        }

        /// <summary>
        /// Update loan
        /// </summary>
        [HttpPut("{id}")]
        public async Task<ActionResult<LoanResponseDto>> Update(int id, [FromBody] LoanRequestDto dto)
            => Ok(await _loanService.UpdateAsync(id, dto));

        /// <summary>
        /// Delete loan
        /// </summary>
        [HttpDelete("{id}")]
        public async Task<ActionResult> Delete(int id)
        {
            await _loanService.DeleteAsync(id);
            return NoContent();
        }
    }
}