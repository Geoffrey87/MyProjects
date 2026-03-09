using BankingApp.API.Entities;

namespace BankingApp.API.Repositories.Interfaces
{
    public interface IServiceRepository : IBaseRepository<Service>
    {
        Task<List<Service>> GetByCategoryAsync(string category);
    }
}