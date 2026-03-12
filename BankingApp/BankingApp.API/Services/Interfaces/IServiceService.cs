using BankingApp.API.DTOs.RequestDtos;
using BankingApp.API.DTOs.ResponseDtos;

public interface IServiceService
{
    Task<List<ServiceResponseDto>> GetAllAsync(int userId);
    Task<List<ServiceResponseDto>> GetByCategoryAsync(int userId, string category);
    Task<ServiceResponseDto> GetByIdAsync(int id);
    Task<ServiceResponseDto> CreateAsync(ServiceRequestDto dto, int userId);
    Task<ServiceResponseDto> UpdateAsync(int id, ServiceRequestDto dto);
    Task DeleteAsync(int id);
    Task PayServiceAsync(int serviceId, int accountId, decimal amount);
}

