using BankingApp.API.Common;
using BankingApp.API.Enums;
using Microsoft.VisualBasic;
using System.ComponentModel.DataAnnotations.Schema;

namespace BankingApp.API.Entities
{
    [Table("cards")]
    public class Card : BaseEntity
    {
        public string CardNumber { get; set; } = string.Empty;
        public string CardHolderName { get; set; } = string.Empty;
        public CardType CardType { get; set; }
        public DateOnly ExpiryDate { get; set; }
        public bool IsActive { get; set; } = true;
        public decimal DailyLimit { get; set; } = 1000;
        public CardStatus Status { get; set; } = CardStatus.Active;

        // Foreign Key
        public int AccountId { get; set; }

        // Navigation property
        public Account Account { get; set; } = null!;
    }
}