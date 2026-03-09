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
        /// Pay a service
        /// </summary>
        [HttpPost("{serviceId}/pay")]
        public async Task<ActionResult> PayService(
            int serviceId,
            [FromQuery] int accountId,
            [FromQuery] decimal? customAmount = null)
        {
            await _serviceService.PayServiceAsync(serviceId, accountId, customAmount);
            return Ok();
        }
    }
}