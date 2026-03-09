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
    public class BeneficiaryController : ControllerBase
    {
        private readonly IBeneficiaryService _beneficiaryService;

        public BeneficiaryController(IBeneficiaryService beneficiaryService)
        {
            _beneficiaryService = beneficiaryService;
        }

        /// <summary>
        /// Get all beneficiaries by user ID
        /// </summary>
        [HttpGet("user/{userId}")]
        public async Task<ActionResult<List<BeneficiaryResponseDto>>> GetByUserId(int userId)
            => Ok(await _beneficiaryService.GetByUserIdAsync(userId));

        /// <summary>
        /// Get beneficiary by ID
        /// </summary>
        [HttpGet("{id}")]
        public async Task<ActionResult<BeneficiaryResponseDto>> GetById(int id)
            => Ok(await _beneficiaryService.GetByIdAsync(id));

        /// <summary>
        /// Create new beneficiary
        /// </summary>
        [HttpPost]
        public async Task<ActionResult<BeneficiaryResponseDto>> Create([FromBody] BeneficiaryRequestDto dto)
        {
            var beneficiary = await _beneficiaryService.CreateAsync(dto);
            return CreatedAtAction(nameof(GetById), new { id = beneficiary.Id }, beneficiary);
        }

        /// <summary>
        /// Update beneficiary
        /// </summary>
        [HttpPut("{id}")]
        public async Task<ActionResult<BeneficiaryResponseDto>> Update(int id, [FromBody] BeneficiaryRequestDto dto)
            => Ok(await _beneficiaryService.UpdateAsync(id, dto));

        /// <summary>
        /// Delete beneficiary
        /// </summary>
        [HttpDelete("{id}")]
        public async Task<ActionResult> Delete(int id)
        {
            await _beneficiaryService.DeleteAsync(id);
            return NoContent();
        }
    }
}