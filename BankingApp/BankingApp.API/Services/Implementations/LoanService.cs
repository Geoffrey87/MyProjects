using BankingApp.API.Entities;
using BankingApp.API.Repositories.Interfaces;
using BankingApp.API.Services.Interfaces;

namespace BankingApp.API.Services.Implementations
{
    public class LoanService : BaseService<Loan>, ILoanService
    {
        private readonly ILoanRepository _loanRepository;

        public LoanService(ILoanRepository loanRepository) : base(loanRepository)
        {
            _loanRepository = loanRepository;
        }

        public async Task<List<Loan>> GetByUserIdAsync(int userId)
            => await _loanRepository.GetByUserIdAsync(userId);
    }
}