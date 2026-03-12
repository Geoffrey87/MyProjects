import { Component, inject, OnInit, signal } from '@angular/core';
import { LoanService } from '../../../core/services/loan.service';
import { Loan } from '../../../core/models';

@Component({
  selector: 'app-loan-approval',
  standalone: true,
  imports: [],
  templateUrl: './loan-approval.html',
  styleUrl: './loan-approval.css',
})
export class LoanApproval implements OnInit {
  private loanService = inject(LoanService);

  loans = signal<Loan[]>([]);
  loading = signal(true);
  processingId = signal<number | null>(null);

  get pendingLoans(): Loan[] {
    return this.loans().filter((l) => l.loanStatus?.toUpperCase() === 'PENDING');
  }

  get processedLoans(): Loan[] {
    return this.loans().filter((l) => l.loanStatus?.toUpperCase() !== 'PENDING');
  }

  ngOnInit(): void {
    this.loanService.getAll().subscribe({
      next: (loans) => {
        console.log('Loans from backend:', loans);
        this.loans.set(loans);
        this.loading.set(false);
      },
      error: () => this.loading.set(false),
    });
  }

  approve(id: number): void {
    this.processingId.set(id);
    this.loanService.approve(id).subscribe({
      next: () => {
        this.loans.update((loans) =>
          loans.map((l) => (l.id === id ? { ...l, loanStatus: 'Active' } : l)),
        );
        this.processingId.set(null);
      },
      error: () => this.processingId.set(null),
    });
  }

  reject(id: number): void {
    this.processingId.set(id);
    this.loanService.reject(id).subscribe({
      next: () => {
        this.loans.update((loans) =>
          loans.map((l) => (l.id === id ? { ...l, loanStatus: 'Rejected' } : l)),
        );
        this.processingId.set(null);
      },
      error: () => this.processingId.set(null),
    });
  }

  formatCurrency(value: number): string {
    return new Intl.NumberFormat('pt-PT', {
      style: 'currency',
      currency: 'EUR',
    }).format(value);
  }

  formatDate(date: string | null): string {
    if (!date) return '—';
    return new Date(date).toLocaleDateString('en-GB', {
      day: '2-digit',
      month: 'short',
      year: 'numeric',
    });
  }

  getStatusClass(status: string): string {
    const normalized = status?.toUpperCase();

    const map: Record<string, string> = {
      ACTIVE: 'status-active',
      PENDING: 'status-pending',
      COMPLETED: 'status-completed',
      REJECTED: 'status-rejected',
    };

    return map[normalized] ?? 'status-pending';
  }
}
