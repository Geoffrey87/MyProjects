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
        private readonly IMapper _mapper;

        public LoanService(
            ILoanRepository loanRepository,
            IAccountRepository accountRepository,
            ITransactionRepository transactionRepository,
            IMapper mapper)
        {
            _loanRepository = loanRepository;
            _accountRepository = accountRepository;
            _transactionRepository = transactionRepository;
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
            if (loans == null || !loans.Any())
                throw new NotFoundException(ErrorMessages.LoanNotFound);
            return _mapper.Map<List<LoanResponseDto>>(loans);
        }

        public async Task<LoanResponseDto> CreateAsync(LoanRequestDto dto)
        {
            var loan = _mapper.Map<Loan>(dto);
            loan.RemainingBalance = dto.Amount;
            loan.MonthlyPayment = CalculateMonthlyPayment(dto.Amount, dto.InterestRate, dto.TermMonths);
            await _loanRepository.AddAsync(loan);
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
        }

        public async Task RejectLoanAsync(int loanId)
        {
            var loan = await _loanRepository.GetByIdAsync(loanId)
                ?? throw new NotFoundException(ErrorMessages.LoanNotFound);

            var rejectedStatus = await _loanRepository.GetStatusByNameAsync("Rejected");
            loan.LoanStatusId = rejectedStatus!.Id;

            await _loanRepository.UpdateAsync(loan);
        }
    }
}