using BankingApp.API.DTOs.RequestDtos;
using BankingApp.API.DTOs.ResponseDtos;
using BankingApp.API.DTOs.User;
using BankingApp.API.Services.Interfaces;
using Microsoft.AspNetCore.Mvc;

namespace BankingApp.API.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class UserController : ControllerBase
    {
        private readonly IUserService _userService;

        public UserController(IUserService userService)
        {
            _userService = userService;
        }

        /// <summary>
        /// Get all users
        /// </summary>
        [HttpGet]
        public async Task<ActionResult<List<UserResponseDto>>> GetAll()
            => Ok(await _userService.GetAllAsync());

        /// <summary>
        /// Get user by ID
        /// </summary>
        [HttpGet("{id}")]
        public async Task<ActionResult<UserResponseDto>> GetById(int id)
            => Ok(await _userService.GetByIdAsync(id));

        /// <summary>
        /// Get user by email
        /// </summary>
        [HttpGet("email/{email}")]
        public async Task<ActionResult<UserResponseDto>> GetByEmail(string email)
            => Ok(await _userService.GetByEmailAsync(email));

        /// <summary>
        /// Create new user
        /// </summary>
        [HttpPost]
        public async Task<ActionResult<UserResponseDto>> Create([FromBody] UserRequestDto dto)
        {
            var user = await _userService.CreateAsync(dto);
            return CreatedAtAction(nameof(GetById), new { id = user.Id }, user);
        }

        /// <summary>
        /// Update user
        /// </summary>
        [HttpPut("{id}")]
        public async Task<ActionResult<UserResponseDto>> Update(int id, [FromBody] UserRequestDto dto)
            => Ok(await _userService.UpdateAsync(id, dto));

        /// <summary>
        /// Delete user
        /// </summary>
        [HttpDelete("{id}")]
        public async Task<ActionResult> Delete(int id)
        {
            await _userService.DeleteAsync(id);
            return NoContent();
        }
    }
}