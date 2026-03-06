using BankingApp.API.Entities;

namespace BankingApp.API.Services.Interfaces
{
    public interface IUserService : IBaseService<User>
    {
        Task<User?> GetByEmailAsync(string email);
        Task<bool> ExistsByEmailAsync(string email);
        Task<bool> ExistsByNIFAsync(string nif);
    }
}
