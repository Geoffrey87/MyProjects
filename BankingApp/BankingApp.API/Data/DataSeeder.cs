using BankingApp.API.Entities;
using BankingApp.API.Enums;
using Microsoft.EntityFrameworkCore;

namespace BankingApp.API.Data
{
    public static class DataSeeder
    {
        public static async Task SeedAsync(AppDbContext context)
        {
            // Account Types
            if (!await context.AccountTypes.AnyAsync())
            {
                await context.AccountTypes.AddRangeAsync(new List<AccountType>
                {
                    new AccountType { Name = "Checking", Description = "Current account for daily use" },
                    new AccountType { Name = "Savings", Description = "Account for saving money" },
                    new AccountType { Name = "Investment", Description = "Account for investments" }
                });
                await context.SaveChangesAsync();
            }

            // Loan Statuses
            if (!await context.LoanStatuses.AnyAsync())
            {
                await context.LoanStatuses.AddRangeAsync(new List<LoanStatus>
                {
                    new LoanStatus { Name = "Pending", Description = "Loan is waiting for approval" },
                    new LoanStatus { Name = "Active", Description = "Loan is active and being paid" },
                    new LoanStatus { Name = "Completed", Description = "Loan has been fully paid" },
                    new LoanStatus { Name = "Rejected", Description = "Loan was rejected" }
                });
                await context.SaveChangesAsync();
            }

            // Notification Types
            if (!await context.NotificationTypes.AnyAsync())
            {
                await context.NotificationTypes.AddRangeAsync(new List<NotificationType>
                {
                    new NotificationType { Name = "Transaction", Description = "Notifications about transactions" },
                    new NotificationType { Name = "Security", Description = "Security alerts" },
                    new NotificationType { Name = "Loan", Description = "Notifications about loans" },
                    new NotificationType { Name = "General", Description = "General notifications" }
                });
                await context.SaveChangesAsync();
            }

            // Admin User
            if (!await context.Users.AnyAsync(u => u.Role == UserRole.Admin))
            {
                var admin = new User
                {
                    FirstName = "Admin",
                    LastName = "BankingApp",
                    Email = "admin@bankingapp.com",
                    PasswordHash = BCrypt.Net.BCrypt.HashPassword("Admin123!"),
                    PhoneNumber = "900000000",
                    NIF = "000000000",
                    Address = "Admin Street, 1",
                    DateOfBirth = new DateOnly(1990, 1, 1),
                    Role = UserRole.Admin,
                    IsActive = true
                };
                await context.Users.AddAsync(admin);
                await context.SaveChangesAsync();
            }
          
        }
    }
}