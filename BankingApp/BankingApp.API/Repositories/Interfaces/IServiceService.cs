using BankingApp.API.DTOs.ResponseDtos;

namespace BankingApp.API.Services.Interfaces
{
    public interface IServiceService
    {
        Task<List<ServiceResponseDto>> GetAllAsync();
        Task<List<ServiceResponseDto>> GetByCategoryAsync(string category);
        Task PayServiceAsync(int serviceId, int accountId, decimal? customAmount);
    }
}