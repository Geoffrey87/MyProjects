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
    public class ServiceController : ControllerBase
    {
        private readonly IServiceService _serviceService;

        public ServiceController(IServiceService serviceService)
        {
            _serviceService = serviceService;
        }

        /// <summary>
        /// Get all services
        /// </summary>
        [HttpGet]
        public async Task<ActionResult<List<ServiceResponseDto>>> GetAll()
            => Ok(await _serviceService.GetAllAsync());

        /// <summary>
        /// Get services by category
        /// </summary>
        [HttpGet("category/{category}")]
        public async Task<ActionResult<List<ServiceResponseDto>>> GetByCategory(string category)
            => Ok(await _serviceService.GetByCategoryAsync(category));

        /// <summary>
        /// Get service by ID
        /// </summary>
        [HttpGet("{id}")]
        public async Task<ActionResult<ServiceResponseDto>> GetById(int id)
            => Ok(await _serviceService.GetByIdAsync(id));

        /// <summary>
        /// Create new service
        /// </summary>
        [HttpPost]
        public async Task<ActionResult<ServiceResponseDto>> Create([FromBody] ServiceRequestDto dto)
        {
            var service = await _serviceService.CreateAsync(dto);
            return CreatedAtAction(nameof(GetById), new { id = service.Id }, service);
        }

        /// <summary>
        /// Pay a service
        /// </summary>
        [HttpPost("{serviceId}/pay")]
        public async Task<ActionResult> PayService(
            int serviceId,
            [FromQuery] int accountId,
            [FromQuery] decimal amount)
        {
            await _serviceService.PayServiceAsync(serviceId, accountId, amount);
            return Ok();
        }

        /// <summary>
        /// Update service
        /// </summary>
        [HttpPut("{id}")]
        public async Task<ActionResult<ServiceResponseDto>> Update(int id, [FromBody] ServiceRequestDto dto)
            => Ok(await _serviceService.UpdateAsync(id, dto));

        /// <summary>
        /// Delete service
        /// </summary>
        [HttpDelete("{id}")]
        public async Task<ActionResult> Delete(int id)
        {
            await _serviceService.DeleteAsync(id);
            return NoContent();
        }
    }
}