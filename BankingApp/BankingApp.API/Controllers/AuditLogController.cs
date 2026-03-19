using BankingApp.API.DTOs.ResponseDtos;
using BankingApp.API.Services.Interfaces;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace BankingApp.API.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    [Authorize(Roles = "Admin")]
    public class AuditLogController : ControllerBase
    {
        private readonly IAuditLogService _auditLogService;

        public AuditLogController(IAuditLogService auditLogService)
        {
            _auditLogService = auditLogService;
        }
        /// <summary>
        /// Get all audit logs — Admin only
        /// </summary>
        [HttpGet]
        public async Task<ActionResult<List<AuditLogResponseDto>>> GetAll()
            => Ok(await _auditLogService.GetAllAsync());

        /// <summary>
        /// Get audit logs by user ID — Admin only
        /// </summary>
        [HttpGet("user/{userId}")]
        public async Task<ActionResult<List<AuditLogResponseDto>>> GetByUserId(int userId)
            => Ok(await _auditLogService.GetByUserIdAsync(userId));

        /// <summary>
        /// Get audit log by ID — Admin only
        /// </summary>
        [HttpGet("{id}")]
        public async Task<ActionResult<AuditLogResponseDto>> GetById(int id)
            => Ok(await _auditLogService.GetByIdAsync(id));

        /// <summary>
        /// Delete audit log — Admin only
        /// </summary>
        [HttpDelete("{id}")]
        public async Task<ActionResult> Delete(int id)
        {
            await _auditLogService.DeleteAsync(id);
            return NoContent();
        }
    }
}