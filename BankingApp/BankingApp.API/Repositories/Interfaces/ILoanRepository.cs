using BankingApp.API.Entities;

namespace BankingApp.API.Repositories.Interfaces
{
    public interface ILoanRepository : IBaseRepository<Loan>
    {
        Task<List<Loan>> GetByUserIdAsync(int userId);

        Task<LoanStatus?> GetStatusByNameAsync(string name);
    }
}
