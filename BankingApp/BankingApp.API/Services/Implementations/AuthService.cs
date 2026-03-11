using AutoMapper;
using BankingApp.API.DTOs.RequestDtos;
using BankingApp.API.DTOs.ResponseDtos;
using BankingApp.API.DTOs.User;
using BankingApp.API.Entities;
using BankingApp.API.Enums;
using BankingApp.API.Exceptions;
using BankingApp.API.Exceptions.Custom;
using BankingApp.API.Repositories.Interfaces;
using BankingApp.API.Services.Interfaces;
using Microsoft.Extensions.Configuration;
using Microsoft.IdentityModel.Tokens;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;

namespace BankingApp.API.Services.Implementations
{
    public class AuthService : IAuthService
    {
        private readonly IUserRepository _userRepository;
        private readonly IAccountRepository _accountRepository;
        private readonly ITransactionRepository _transactionRepository;
        private readonly IMapper _mapper;
        private readonly IConfiguration _configuration;

        public AuthService(
            IUserRepository userRepository,
            IAccountRepository accountRepository,
            ITransactionRepository transactionRepository,
            IMapper mapper,
            IConfiguration configuration)
        {
            _userRepository = userRepository;
            _accountRepository = accountRepository;
            _transactionRepository = transactionRepository;
            _mapper = mapper;
            _configuration = configuration;
        }

 

        public async Task<AuthResponseDto> RegisterAsync(UserRequestDto dto)
        {
            // Verifica se o email já existe
            if (await _userRepository.ExistsByEmailAsync(dto.Email))
                throw new ConflictException(ErrorMessages.UserEmailAlreadyExists);

            // Cria o utilizador
            var user = _mapper.Map<User>(dto);
            user.PasswordHash = BCrypt.Net.BCrypt.HashPassword(dto.Password);
            await _userRepository.AddAsync(user);

            var accountType = await _accountRepository.GetCheckingAccountTypeAsync();

            if (accountType == null)
                throw new BadRequestException("AccountType 'Checking' not found. Seeder may not have run.");

            // Cria conta bancária automaticamente
            var account = new Account
            {
                UserId = user.Id,
                AccountNumber = GenerateAccountNumber(),
                IBAN = GenerateIBAN(),
                Balance = 10000,
                Currency = "EUR",
                AccountTypeId = accountType!.Id,
                IsActive = true
            };
            await _accountRepository.AddAsync(account);

            // Regista transação de boas vindas
            await _transactionRepository.AddAsync(new Transaction
            {
                ToAccountId = account.Id,
                Amount = 10000,
                Currency = "EUR",
                TransactionType = TransactionType.Deposit,
                Status = TransactionStatus.Completed,
                Description = "Welcome bonus"
            });

            return GenerateToken(user);
        }

        public async Task<AuthResponseDto> LoginAsync(LoginRequestDto dto)
        {
            var user = await _userRepository.GetByEmailAsync(dto.Email)
                ?? throw new UnauthorizedException(ErrorMessages.InvalidCredentials);

            if (!BCrypt.Net.BCrypt.Verify(dto.Password, user.PasswordHash))
                throw new UnauthorizedException(ErrorMessages.InvalidCredentials);

            return GenerateToken(user);
        }

        /// <summary>
        /// Generates a random account number
        /// </summary>
        private string GenerateAccountNumber()
            => new Random().Next(100000000, 999999999).ToString();

        /// <summary>
        /// Generates a random IBAN (PT format)
        /// </summary>
        private string GenerateIBAN()
        {
            var random = new Random();
            return $"PT50 {random.Next(1000, 9999)} {random.Next(1000, 9999)} {random.Next(1000, 9999)} {random.Next(1000, 9999)} {random.Next(100, 999)}";
        }

        /// <summary>
        /// Generates JWT token for authenticated user
        /// </summary>
        private AuthResponseDto GenerateToken(User user)
        {
            var jwtSettings = _configuration.GetSection("JwtSettings");
            var secret = jwtSettings["Secret"]!;
            var issuer = jwtSettings["Issuer"]!;
            var audience = jwtSettings["Audience"]!;
            var expirationInHours = int.Parse(jwtSettings["ExpirationInHours"]!);

            var key = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(secret));
            var credentials = new SigningCredentials(key, SecurityAlgorithms.HmacSha256);
            var expiresAt = DateTime.UtcNow.AddHours(expirationInHours);

            var claims = new[]
            {
                new Claim(ClaimTypes.NameIdentifier, user.Id.ToString()),
                new Claim(ClaimTypes.Email, user.Email),
                new Claim(ClaimTypes.Role, user.Role.ToString()),
                new Claim(ClaimTypes.Name, $"{user.FirstName} {user.LastName}")
            };

            var token = new JwtSecurityToken(
                issuer: issuer,
                audience: audience,
                claims: claims,
                expires: expiresAt,
                signingCredentials: credentials
            );

            return new AuthResponseDto
            {
                Token = new JwtSecurityTokenHandler().WriteToken(token),
                Email = user.Email,
                FullName = $"{user.FirstName} {user.LastName}",
                Role = user.Role.ToString(),
                ExpiresAt = expiresAt
            };
        }

    }
}