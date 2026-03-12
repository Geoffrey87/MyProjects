using AutoMapper;
using BankingApp.API.DTOs.RequestDtos;
using BankingApp.API.DTOs.ResponseDtos;
using BankingApp.API.Entities;
using BankingApp.API.Enums;
using BankingApp.API.Exceptions;
using BankingApp.API.Exceptions.Custom;
using BankingApp.API.Repositories.Implementations;
using BankingApp.API.Repositories.Interfaces;
using BankingApp.API.Services.Interfaces;

namespace BankingApp.API.Services.Implementations
{
    public class LoanService : ILoanService
    {
        private readonly ILoanRepository _loanRepository;
        private readonly IAccountRepository _accountRepository;
        private readonly ITransactionRepository _transactionRepository;
        private readonly INotificationRepository _notificationRepository;
        private readonly IUserRepository _userRepository;
        private readonly IMapper _mapper;

        public LoanService(
            ILoanRepository loanRepository,
            IAccountRepository accountRepository,
            ITransactionRepository transactionRepository,
            INotificationRepository notificationRepository,
            IUserRepository userRepository,
            IMapper mapper)
        {
            _loanRepository = loanRepository;
            _accountRepository = accountRepository;
            _transactionRepository = transactionRepository;
            _notificationRepository = notificationRepository;
            _userRepository = userRepository;
            _mapper = mapper;
        }

        public async Task<List<LoanResponseDto>> GetAllAsync()
        {
            var loans = await _loanRepository.GetAllAsync();
            return _mapper.Map<List<LoanResponseDto>>(loans);
        }

        public async Task<LoanResponseDto> GetByIdAsync(int id)
        {
            var loan = await _loanRepository.GetByIdAsync(id)
                ?? throw new NotFoundException(ErrorMessages.LoanNotFound);
            return _mapper.Map<LoanResponseDto>(loan);
        }

        public async Task<List<LoanResponseDto>> GetByUserIdAsync(int userId)
        {
            var loans = await _loanRepository.GetByUserIdAsync(userId);

            return _mapper.Map<List<LoanResponseDto>>(loans ?? new List<Loan>());
        }

        public async Task<LoanResponseDto> CreateAsync(LoanRequestDto dto)
        {
            var account = await _accountRepository.GetByIdAsync(dto.AccountId)
                ?? throw new NotFoundException(ErrorMessages.AccountNotFound);

            var pendingStatus = await _loanRepository.GetStatusByNameAsync("Pending");

            const decimal interestRate = 5.0m; // taxa definida pelo banco

            var loan = new Loan
            {
                UserId = account.UserId,
                AccountId = dto.AccountId,
                Amount = dto.Amount,
                InterestRate = interestRate,
                TermMonths = dto.TermMonths,
                MonthlyPayment = CalculateMonthlyPayment(dto.Amount, interestRate, dto.TermMonths),
                RemainingBalance = dto.Amount,
                LoanStatusId = pendingStatus!.Id,
                StartDate = DateTime.UtcNow
            };

            await _loanRepository.AddAsync(loan);

            var user = await _userRepository.GetByIdAsync(account.UserId)
                ?? throw new NotFoundException(ErrorMessages.UserNotFound);

            var notificationType = await _notificationRepository.GetTypeByNameAsync("Loan");
            var adminIds = await _notificationRepository.GetAdminUserIdsAsync();

            foreach (var adminId in adminIds)
            {
                await _notificationRepository.AddAsync(new Notification
                {
                    UserId = adminId,
                    Title = "New Loan Request",
                    Message = $"{user.FirstName} {user.LastName} requested a loan of {dto.Amount}€",
                    NotificationTypeId = notificationType!.Id,
                    IsRead = false
                });
            }

            return _mapper.Map<LoanResponseDto>(loan);
        }

        public async Task<LoanResponseDto> UpdateAsync(int id, LoanRequestDto dto)
        {
            var loan = await _loanRepository.GetByIdAsync(id)
                ?? throw new NotFoundException(ErrorMessages.LoanNotFound);
            _mapper.Map(dto, loan);
            await _loanRepository.UpdateAsync(loan);
            return _mapper.Map<LoanResponseDto>(loan);
        }

        public async Task DeleteAsync(int id)
        {
            var loan = await _loanRepository.GetByIdAsync(id)
                ?? throw new NotFoundException(ErrorMessages.LoanNotFound);
            await _loanRepository.DeleteAsync(loan.Id);
        }

        /// <summary>
        /// Calculates monthly payment using standard amortization formula
        /// MonthlyPayment = P * (r * (1 + r)^n) / ((1 + r)^n - 1)
        /// </summary>
        private decimal CalculateMonthlyPayment(decimal amount, decimal interestRate, int termMonths)
        {
            var r = interestRate / 100 / 12;
            var n = termMonths;
            var power = (double)Math.Pow((double)(1 + r), n);
            return amount * (decimal)(r * (decimal)power / ((decimal)power - 1));
        }
        public async Task ApproveLoanAsync(int loanId)
        {
            var loan = await _loanRepository.GetByIdAsync(loanId)
                ?? throw new NotFoundException(ErrorMessages.LoanNotFound);

            var pendingStatus = await _loanRepository.GetStatusByNameAsync("Pending");
            if (loan.LoanStatusId != pendingStatus!.Id)
                throw new BadRequestException("Loan is not in pending status");

            var activeStatus = await _loanRepository.GetStatusByNameAsync("Active");
            loan.LoanStatusId = activeStatus!.Id;
            loan.StartDate = DateTime.UtcNow;
            loan.EndDate = DateTime.UtcNow.AddMonths(loan.TermMonths);

            await _loanRepository.UpdateAsync(loan);

            // Credita o valor do empréstimo na conta
            var account = await _accountRepository.GetByIdAsync(loan.AccountId)
                ?? throw new NotFoundException(ErrorMessages.AccountNotFound);

            account.Balance += loan.Amount;
            await _accountRepository.UpdateAsync(account);

            await _transactionRepository.AddAsync(new Transaction
            {
                ToAccountId = account.Id,
                Amount = loan.Amount,
                Currency = "EUR",
                TransactionType = TransactionType.Deposit,
                Status = TransactionStatus.Completed,
                Description = $"Loan approved: {loan.Amount}€"
            });
            
            var notificationType = await _notificationRepository.GetTypeByNameAsync("Loan");
            await _notificationRepository.AddAsync(new Notification
            {
                UserId = account.UserId,
                Title = "Loan Approved",
                Message = $"Your loan of {loan.Amount}€ has been approved!",
                NotificationTypeId = notificationType!.Id,
                IsRead = false
            });
        }

        public async Task RejectLoanAsync(int loanId)
        {
            var loan = await _loanRepository.GetByIdAsync(loanId)
                ?? throw new NotFoundException(ErrorMessages.LoanNotFound);

            var rejectedStatus = await _loanRepository.GetStatusByNameAsync("Rejected");
            loan.LoanStatusId = rejectedStatus!.Id;
            await _loanRepository.UpdateAsync(loan);

            // Busca a conta para obter o UserId
            var account = await _accountRepository.GetByIdAsync(loan.AccountId)
                ?? throw new NotFoundException(ErrorMessages.AccountNotFound);

            var notificationType = await _notificationRepository.GetTypeByNameAsync("Loan");
            await _notificationRepository.AddAsync(new Notification
            {
                UserId = account.UserId,  // ← account carregado
                Title = "Loan Rejected",
                Message = $"Your loan request of {loan.Amount}€ has been rejected.",
                NotificationTypeId = notificationType!.Id,
                IsRead = false
            });
        }
    }
}