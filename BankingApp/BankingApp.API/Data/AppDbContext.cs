using BankingApp.API.Entities;
using Microsoft.EntityFrameworkCore;

namespace BankingApp.API.Data
{
    public class AppDbContext : DbContext
    {
        public AppDbContext(DbContextOptions<AppDbContext> options) : base(options) { }

        // Entities
        public DbSet<User> Users { get; set; }
        public DbSet<Account> Accounts { get; set; }
        public DbSet<Transaction> Transactions { get; set; }
        public DbSet<Card> Cards { get; set; }
        public DbSet<Loan> Loans { get; set; }
        public DbSet<Beneficiary> Beneficiaries { get; set; }
        public DbSet<Notification> Notifications { get; set; }
        public DbSet<AuditLog> AuditLogs { get; set; }
        public DbSet<AccountType> AccountTypes { get; set; }
        public DbSet<LoanStatus> LoanStatuses { get; set; }
        public DbSet<NotificationType> NotificationTypes { get; set; }
        public DbSet<Service> Services { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);

            // Transaction — duas FK para Account
            modelBuilder.Entity<Transaction>()
                .HasOne(t => t.FromAccount)
                .WithMany(a => a.Transactions)
                .HasForeignKey(t => t.FromAccountId)
                .OnDelete(DeleteBehavior.Restrict)
                .IsRequired(false);

            modelBuilder.Entity<Transaction>()
                .HasOne(t => t.ToAccount)
                .WithMany()
                .HasForeignKey(t => t.ToAccountId)
                .OnDelete(DeleteBehavior.Restrict);
        }
    }
}