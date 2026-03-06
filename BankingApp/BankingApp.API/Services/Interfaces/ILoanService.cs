using BankingApp.API.Entities;

namespace BankingApp.API.Services.Interfaces
{
    public interface ILoanService : IBaseService<Loan>
    {
        Task<List<Loan>> GetByUserIdAsync(int userId);
    }
}