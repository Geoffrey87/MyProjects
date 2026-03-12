import { Component, inject, OnInit, signal } from '@angular/core';
import { CardService } from '../../../core/services/card.service';
import { Card, CardStatus } from '../../../core/models';

@Component({
  selector: 'app-card-approval',
  standalone: true,
  imports: [],
  templateUrl: './card-approval.html',
  styleUrl: './card-approval.css',
})
export class CardApproval implements OnInit {
  private cardService = inject(CardService);

  cards = signal<Card[]>([]);
  loading = signal(true);
  processingId = signal<number | null>(null);

  get pendingCards(): Card[] {
    return this.cards().filter((c) => c.status === CardStatus.Pending);
  }

  get processedCards(): Card[] {
    return this.cards().filter((c) => c.status !== CardStatus.Pending);
  }

  ngOnInit(): void {
    this.cardService.getAll().subscribe({
      next: (cards) => {
        this.cards.set(cards);
        this.loading.set(false);
      },
      error: () => this.loading.set(false),
    });
  }

  approve(id: number): void {
    this.processingId.set(id);
    this.cardService.approve(id).subscribe({
      next: (updated) => {
        this.cards.update((cards) => cards.map((c) => (c.id === id ? updated : c)));
        this.processingId.set(null);
      },
      error: () => this.processingId.set(null),
    });
  }

  reject(id: number): void {
    this.processingId.set(id);
    this.cardService.reject(id).subscribe({
      next: (updated) => {
        this.cards.update((cards) => cards.map((c) => (c.id === id ? updated : c)));
        this.processingId.set(null);
      },
      error: () => this.processingId.set(null),
    });
  }

  getStatusClass(status: string): string {
    const map: Record<string, string> = {
      Active: 'status-active',
      Pending: 'status-pending',
      Inactive: 'status-inactive',
      Rejected: 'status-rejected',
    };
    return map[status] ?? 'status-pending';
  }

  formatDate(date: string): string {
    return new Date(date).toLocaleDateString('en-GB', {
      day: '2-digit',
      month: 'short',
      year: 'numeric',
    });
  }
}
