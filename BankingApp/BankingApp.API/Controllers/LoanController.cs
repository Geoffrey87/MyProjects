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
        /// Create new loan — Client
        /// </summary>
        [HttpPost]
        public async Task<ActionResult<LoanResponseDto>> Create([FromBody] LoanRequestDto dto)
        {
            var loan = await _loanService.CreateAsync(dto);
            return CreatedAtAction(nameof(GetById), new { id = loan.Id }, loan);
        }

        /// <summary>
        /// Update loan — Admin only
        /// </summary>
        [HttpPut("{id}")]
        [Authorize(Roles = "Admin")]
        public async Task<ActionResult<LoanResponseDto>> Update(int id, [FromBody] LoanRequestDto dto)
            => Ok(await _loanService.UpdateAsync(id, dto));

        /// <summary>
        /// Delete loan — Admin only
        /// </summary>
        [HttpDelete("{id}")]
        [Authorize(Roles = "Admin")]
        public async Task<ActionResult> Delete(int id)
        {
            await _loanService.DeleteAsync(id);
            return NoContent();
        }

        /// <summary>
        /// Approve loan — Admin only
        /// </summary>
        [HttpPatch("{id}/approve")]
        [Authorize(Roles = "Admin")]
        public async Task<ActionResult> Approve(int id)
        {
            await _loanService.ApproveLoanAsync(id);
            return NoContent();
        }

        /// <summary>
        /// Reject loan — Admin only
        /// </summary>
        [HttpPatch("{id}/reject")]
        [Authorize(Roles = "Admin")]
        public async Task<ActionResult> Reject(int id)
        {
            await _loanService.RejectLoanAsync(id);
            return NoContent();
        }

        /// <summary>
        /// Get all loans — Admin only
        /// </summary>
        [HttpGet]
        [Authorize(Roles = "Admin")]
        public async Task<ActionResult<List<LoanResponseDto>>> GetAll()
            => Ok(await _loanService.GetAllAsync());
    }
}