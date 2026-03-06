using BankingApp.API.Entities;

namespace BankingApp.API.Repositories.Interfaces
{
    public interface IUserRepository : IBaseRepository<User>
    {
        Task<User?> GetByEmailAsync(string email);
        Task<bool> ExistsByEmailAsync(string email);
        Task<bool> ExistsByNIFAsync(string nif);
    }
}