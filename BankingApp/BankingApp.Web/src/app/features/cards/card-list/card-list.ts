import { Component, inject, OnInit, signal } from '@angular/core';
import { AuthService } from '../../../core/services/auth.service';
import { AccountService } from '../../../core/services/account.service';
import { CardService } from '../../../core/services/card.service';
import { Card, Account } from '../../../core/models';

@Component({
  selector: 'app-card-list',
  standalone: true,
  imports: [],
  templateUrl: './card-list.html',
  styleUrl: './card-list.css',
})
export class CardList implements OnInit {
  private auth = inject(AuthService);
  private accountService = inject(AccountService);
  private cardService = inject(CardService);

  cards = signal<Card[]>([]);
  loading = signal(true);
  togglingId = signal<number | null>(null);

  ngOnInit(): void {
    const user = this.auth.currentUser();
    if (!user) return;

    const userId = Number((user as any).id ?? (user as any).nameid ?? 1);

    this.accountService.getByUser(userId).subscribe({
      next: (accounts) => {
        if (accounts.length === 0) {
          this.loading.set(false);
          return;
        }
        this.loadCardsForAccounts(accounts);
      },
      error: () => this.loading.set(false),
    });
  }

  private loadCardsForAccounts(accounts: Account[]): void {
    const allCards: Card[] = [];
    let completed = 0;

    accounts.forEach((account) => {
      this.cardService.getByAccount(account.id).subscribe({
        next: (cards) => {
          allCards.push(...cards);
          completed++;
          if (completed === accounts.length) {
            this.cards.set(allCards);
            this.loading.set(false);
          }
        },
        error: () => {
          completed++;
          if (completed === accounts.length) {
            this.cards.set(allCards);
            this.loading.set(false);
          }
        },
      });
    });
  }

  toggleCard(card: Card): void {
    this.togglingId.set(card.id);
    this.cardService.toggle(card.id).subscribe({
      next: (updated) => {
        this.cards.update((cards) => cards.map((c) => (c.id === updated.id ? updated : c)));
        this.togglingId.set(null);
      },
      error: () => this.togglingId.set(null),
    });
  }

  formatDate(date: string): string {
    return new Date(date).toLocaleDateString('en-GB', {
      month: '2-digit',
      year: '2-digit',
    });
  }

  maskCardNumber(number: string): string {
    return '**** **** **** ' + number.slice(-4);
  }
}
