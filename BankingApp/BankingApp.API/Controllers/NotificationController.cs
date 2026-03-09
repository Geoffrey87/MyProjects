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
    public class NotificationController : ControllerBase
    {
        private readonly INotificationService _notificationService;

        public NotificationController(INotificationService notificationService)
        {
            _notificationService = notificationService;
        }

        /// <summary>
        /// Get all notifications by user ID
        /// </summary>
        [HttpGet("user/{userId}")]
        public async Task<ActionResult<List<NotificationResponseDto>>> GetByUserId(int userId)
            => Ok(await _notificationService.GetByUserIdAsync(userId));

        /// <summary>
        /// Get unread notifications by user ID
        /// </summary>
        [HttpGet("user/{userId}/unread")]
        public async Task<ActionResult<List<NotificationResponseDto>>> GetUnreadByUserId(int userId)
            => Ok(await _notificationService.GetUnreadByUserIdAsync(userId));

        /// <summary>
        /// Get notification by ID
        /// </summary>
        [HttpGet("{id}")]
        public async Task<ActionResult<NotificationResponseDto>> GetById(int id)
            => Ok(await _notificationService.GetByIdAsync(id));

        /// <summary>
        /// Create new notification — Admin only
        /// </summary>
        [HttpPost]
        [Authorize(Roles = "Admin")]
        public async Task<ActionResult<NotificationResponseDto>> Create([FromBody] NotificationRequestDto dto)
        {
            var notification = await _notificationService.CreateAsync(dto);
            return CreatedAtAction(nameof(GetById), new { id = notification.Id }, notification);
        }

        /// <summary>
        /// Mark notification as read
        /// </summary>
        [HttpPatch("{id}/read")]
        public async Task<ActionResult> MarkAsRead(int id)
        {
            await _notificationService.MarkAsReadAsync(id);
            return NoContent();
        }

        /// <summary>
        /// Update notification — Admin only
        /// </summary>
        [HttpPut("{id}")]
        [Authorize(Roles = "Admin")]
        public async Task<ActionResult<NotificationResponseDto>> Update(int id, [FromBody] NotificationRequestDto dto)
            => Ok(await _notificationService.UpdateAsync(id, dto));

        /// <summary>
        /// Delete notification — Admin only
        /// </summary>
        [HttpDelete("{id}")]
        [Authorize(Roles = "Admin")]
        public async Task<ActionResult> Delete(int id)
        {
            await _notificationService.DeleteAsync(id);
            return NoContent();
        }
    }
}