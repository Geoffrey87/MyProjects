using BankingApp.API.DTOs.RequestDtos;
using BankingApp.API.DTOs.ResponseDtos;

public interface IServiceService
{
    Task<List<ServiceResponseDto>> GetAllAsync();
    Task<List<ServiceResponseDto>> GetByCategoryAsync(string category);
    Task<ServiceResponseDto> GetByIdAsync(int id);
    Task<ServiceResponseDto> CreateAsync(ServiceRequestDto dto);
    Task<ServiceResponseDto> UpdateAsync(int id, ServiceRequestDto dto);
    Task DeleteAsync(int id);
    Task PayServiceAsync(int serviceId, int accountId, decimal amount);
}

