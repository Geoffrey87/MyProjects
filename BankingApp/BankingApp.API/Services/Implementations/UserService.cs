using AutoMapper;
using BankingApp.API.DTOs.RequestDtos;
using BankingApp.API.DTOs.ResponseDtos;
using BankingApp.API.DTOs.User;
using BankingApp.API.Entities;
using BankingApp.API.Exceptions;
using BankingApp.API.Exceptions.Custom;
using BankingApp.API.Repositories.Interfaces;
using BankingApp.API.Services.Interfaces;

namespace BankingApp.API.Services.Implementations
{
    public class UserService : IUserService
    {
        private readonly IUserRepository _userRepository;
        private readonly IMapper _mapper;

        public UserService(IUserRepository userRepository, IMapper mapper)
        {
            _userRepository = userRepository;
            _mapper = mapper;
        }

        public async Task<List<UserResponseDto>> GetAllAsync()
        {
            var users = await _userRepository.GetAllAsync();
            return _mapper.Map<List<UserResponseDto>>(users);
        }

        public async Task<UserResponseDto> GetByIdAsync(int id)
        {
            var user = await _userRepository.GetByIdAsync(id)
                ?? throw new NotFoundException(ErrorMessages.UserNotFound);
            return _mapper.Map<UserResponseDto>(user);
        }

        public async Task<UserResponseDto> GetByEmailAsync(string email)
        {
            if (string.IsNullOrEmpty(email))
                throw new BadRequestException(ErrorMessages.InvalidCredentials);

            var user = await _userRepository.GetByEmailAsync(email)
                ?? throw new NotFoundException(ErrorMessages.UserNotFound);
            return _mapper.Map<UserResponseDto>(user);
        }

        public async Task<UserResponseDto> CreateAsync(UserRequestDto dto)
        {
            await ExistsByEmailAsync(dto.Email);

            var user = _mapper.Map<User>(dto);
            await _userRepository.AddAsync(user);
            return _mapper.Map<UserResponseDto>(user);
        }

        public async Task<UserResponseDto> UpdateAsync(int id, UserRequestDto dto)
        {
            var user = await _userRepository.GetByIdAsync(id)
                ?? throw new NotFoundException(ErrorMessages.UserNotFound);

            _mapper.Map(dto, user);
            await _userRepository.UpdateAsync(user);
            return _mapper.Map<UserResponseDto>(user);
        }

        public async Task DeleteAsync(int id)
        {
            var user = await _userRepository.GetByIdAsync(id)
                ?? throw new NotFoundException(ErrorMessages.UserNotFound);

            await _userRepository.DeleteAsync(user.Id);
        }

        public async Task<bool> ExistsByEmailAsync(string email)
        {
            if (await _userRepository.ExistsByEmailAsync(email))
                throw new ConflictException(ErrorMessages.UserEmailAlreadyExists);
            return false;
        }

    }
}