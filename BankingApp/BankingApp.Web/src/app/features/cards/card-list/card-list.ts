import { Component, inject, OnInit, signal } from '@angular/core';
import { AuthService } from '../../../core/services/auth.service';
import { AccountService } from '../../../core/services/account.service';
import { CardService } from '../../../core/services/card.service';
import { Card, Account, CardType, CardStatus } from '../../../core/models';
import { FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { IbanPipe } from '../../../shared/pipes/iban.pipe';

@Component({
  selector: 'app-card-list',
  standalone: true,
  imports: [ReactiveFormsModule, IbanPipe],
  templateUrl: './card-list.html',
  styleUrl: './card-list.css',
})
export class CardList implements OnInit {
  private auth = inject(AuthService);
  private accountService = inject(AccountService);
  private cardService = inject(CardService);
  private fb = inject(FormBuilder);

  cards = signal<Card[]>([]);
  accounts = signal<Account[]>([]);
  loading = signal(true);
  togglingId = signal<number | null>(null);
  showModal = signal(false);
  requesting = signal(false);

  CardStatus = CardStatus;
  CardType = CardType;

  requestForm = this.fb.group({
    accountId: [0, [Validators.required, Validators.min(1)]],
    cardType: [CardType.Debit, Validators.required],
    dailyLimit: [1000, [Validators.required, Validators.min(1)]],
  });

  ngOnInit(): void {
    const user = this.auth.currentUser();
    if (!user) return;

    const userId = this.auth.getUserId();

    this.accountService.getByUser(userId).subscribe({
      next: (accounts) => {
        this.accounts.set(accounts);
        if (accounts.length === 0) {
          this.loading.set(false);
          return;
        }
        // pre-select first account
        this.requestForm.patchValue({ accountId: accounts[0].id });
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
    if (card.status === CardStatus.Pending || card.status === CardStatus.Rejected) return;

    this.togglingId.set(card.id);
    this.cardService.toggle(card.id).subscribe({
      next: (updated) => {
        this.cards.update((cards) => cards.map((c) => (c.id === updated.id ? updated : c)));
        this.togglingId.set(null);
      },
      error: () => this.togglingId.set(null),
    });
  }

  openModal(): void {
    this.showModal.set(true);
  }

  closeModal(): void {
    this.showModal.set(false);
    this.requestForm.reset({
      accountId: this.accounts()[0]?.id ?? 0,
      cardType: CardType.Debit,
      dailyLimit: 1000,
    });
  }

  submitRequest(): void {
    if (this.requestForm.invalid) return;

    this.requesting.set(true);
    this.cardService.request(this.requestForm.value as any).subscribe({
      next: (card) => {
        this.cards.update((cards) => [...cards, card]);
        this.requesting.set(false);
        this.closeModal();
      },
      error: () => this.requesting.set(false),
    });
  }

  getCardClass(card: Card): string {
    if (card.status === CardStatus.Pending) return 'bank-card bank-card-pending';
    if (card.status === CardStatus.Rejected) return 'bank-card bank-card-rejected';
    return card.isActive ? 'bank-card bank-card-active' : 'bank-card bank-card-inactive';
  }

  getStatusLabel(card: Card): string {
    if (card.status === CardStatus.Pending) return 'Pending Approval';
    if (card.status === CardStatus.Rejected) return 'Rejected';
    return card.isActive ? 'Active' : 'Inactive';
  }

  getStatusClass(card: Card): string {
    if (card.status === CardStatus.Pending) return 'status-pending';
    if (card.status === CardStatus.Rejected) return 'status-rejected';
    return card.isActive ? 'status-active' : 'status-inactive';
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

  visibleCardIds = signal<Set<number>>(new Set());

  toggleCardNumber(cardId: number): void {
    this.visibleCardIds.update((ids) => {
      const newIds = new Set(ids);
      newIds.has(cardId) ? newIds.delete(cardId) : newIds.add(cardId);
      return newIds;
    });
  }

  isCardNumberVisible(cardId: number): boolean {
    return this.visibleCardIds().has(cardId);
  }
}
