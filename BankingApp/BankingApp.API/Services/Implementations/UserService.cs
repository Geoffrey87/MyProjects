using BankingApp.API.Entities;
using BankingApp.API.Exceptions;
using BankingApp.API.Exceptions.Custom;
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
        {
            if (string.IsNullOrEmpty(email))
                throw new BadRequestException(ErrorMessages.InvalidCredentials);

            return await _userRepository.GetByEmailAsync(email)
                ?? throw new NotFoundException(ErrorMessages.UserNotFound);
        }

        public async Task<bool> ExistsByEmailAsync(string email)
        {
            if (string.IsNullOrEmpty(email))
                throw new BadRequestException(ErrorMessages.InvalidCredentials);

            if (await _userRepository.ExistsByEmailAsync(email))
                throw new ConflictException(ErrorMessages.UserEmailAlreadyExists);

            return false;
        }

        public async Task<bool> ExistsByNIFAsync(string nif)
        {
            if (string.IsNullOrEmpty(nif))
                throw new BadRequestException(ErrorMessages.InvalidCredentials);

            if (await _userRepository.ExistsByNIFAsync(nif))
                throw new ConflictException(ErrorMessages.UserNIFAlreadyExists);

            return false;
        }
    }
}