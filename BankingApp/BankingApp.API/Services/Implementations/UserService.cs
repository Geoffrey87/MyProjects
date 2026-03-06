using BankingApp.API.Entities;
using BankingApp.API.Repositories.Interfaces;
using BankingApp.API.Services.Interfaces;

namespace BankingApp.API.Services.Implementations
{
    public class UserService : BaseService<User>, IUserService
    {
        private readonly IUserRepository _userRepository;

        public UserService(IUserRepository userRepository) : base(userRepository)
        {
            _userRepository = userRepository;
        }

        public async Task<User?> GetByEmailAsync(string email)
            => await _userRepository.GetByEmailAsync(email);

        public async Task<bool> ExistsByEmailAsync(string email)
            => await _userRepository.ExistsByEmailAsync(email);

        public async Task<bool> ExistsByNIFAsync(string nif)
            => await _userRepository.ExistsByNIFAsync(nif);
    }
}