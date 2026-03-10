import { Component, inject, OnInit, signal } from '@angular/core';
import { AuthService } from '../../../core/services/auth.service';
import { LoanService } from '../../../core/services/loan.service';
import { Loan } from '../../../core/models';

@Component({
  selector: 'app-loan-list',
  standalone: true,
  imports: [],
  templateUrl: './loan-list.html',
  styleUrl: './loan-list.css',
})
export class LoanList implements OnInit {
  private auth = inject(AuthService);
  private loanService = inject(LoanService);

  loans = signal<Loan[]>([]);
  loading = signal(true);

  ngOnInit(): void {
    const user = this.auth.currentUser();
    if (!user) return;

    const userId = Number((user as any).id ?? (user as any).nameid ?? 1);

    this.loanService.getByUser(userId).subscribe({
      next: (loans) => {
        this.loans.set(loans);
        this.loading.set(false);
      },
      error: () => this.loading.set(false),
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

  getProgress(loan: Loan): number {
    if (loan.amount === 0) return 0;
    const paid = loan.amount - loan.remainingBalance;
    return Math.round((paid / loan.amount) * 100);
  }

  getStatusClass(status: string): string {
    const map: Record<string, string> = {
      Active: 'status-active',
      Pending: 'status-pending',
      Completed: 'status-completed',
      Rejected: 'status-rejected',
    };
    return map[status] ?? 'status-pending';
  }
}
