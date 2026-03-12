using BankingApp.API.Entities;

namespace BankingApp.API.Repositories.Interfaces
{
    public interface IServiceRepository : IBaseRepository<Service>
    {
        Task<List<Service>> GetByUserIdAsync(int userId);
        Task<List<Service>> GetByCategoryAsync(int userId, string category);
    }
}