import { Component, inject, OnInit, signal } from '@angular/core';
import { AuthService } from '../../../core/services/auth.service';
import { AccountService } from '../../../core/services/account.service';
import { LoanService } from '../../../core/services/loan.service';
import { Loan, Account } from '../../../core/models';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-loan-list',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './loan-list.html',
  styleUrl: './loan-list.css',
})
export class LoanList implements OnInit {
  private auth = inject(AuthService);
  private accountService = inject(AccountService);
  private loanService = inject(LoanService);

  loans = signal<Loan[]>([]);
  accounts = signal<Account[]>([]);
  loading = signal(true);

  showRequestModal = signal(false);
  requesting = signal(false);
  requestError = signal('');
  requestSuccess = signal('');

  newLoan = { accountId: 0, amount: 0, termMonths: 12 };

  ngOnInit(): void {
    const userId = this.auth.getUserId();

    this.loanService.getByUser(userId).subscribe({
      next: (loans) => {
        this.loans.set(loans);
        this.loading.set(false);
      },
      error: () => {
        this.loans.set([]);
        this.loading.set(false);
      },
    });

    this.accountService.getByUser(userId).subscribe({
      next: (accounts) => {
        this.accounts.set(accounts.filter((a) => a.isActive));
        if (accounts.length > 0) {
          this.newLoan.accountId = accounts[0].id;
        }
      },
    });
  }

  openRequestModal(): void {
    this.showRequestModal.set(true);
    this.requestError.set('');
    this.requestSuccess.set('');
  }

  cancelRequest(): void {
    this.showRequestModal.set(false);
    this.newLoan = { accountId: this.accounts()[0]?.id ?? 0, amount: 0, termMonths: 12 };
    this.requestError.set('');
  }

  submitRequest(): void {
    if (!this.newLoan.accountId || this.newLoan.amount <= 0 || this.newLoan.termMonths <= 0) {
      this.requestError.set('Please fill in all fields correctly.');
      return;
    }

    this.requesting.set(true);
    this.requestError.set('');

    this.loanService.create(this.newLoan).subscribe({
      next: (loan) => {
        this.loans.update((l) => [...l, loan]);
        this.showRequestModal.set(false);
        this.requestSuccess.set('Loan request submitted successfully! Waiting for approval.');
        this.requesting.set(false);
        this.newLoan = { accountId: this.accounts()[0]?.id ?? 0, amount: 0, termMonths: 12 };
      },
      error: (err) => {
        this.requestError.set(err.error?.message ?? 'Failed to submit loan request.');
        this.requesting.set(false);
      },
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
    const map: Record<string, string> = {
      Active: 'status-active',
      Pending: 'status-pending',
      Completed: 'status-completed',
      Rejected: 'status-rejected',
    };
    return map[status] ?? 'status-pending';
  }

  getProgress(loan: Loan): number {
    if (loan.amount === 0) return 0;
    const paid = loan.amount - loan.remainingBalance;
    return Math.round((paid / loan.amount) * 100);
  }
  calculateMonthlyPayment(amount: number, termMonths: number): number {
    const r = 0.05 / 12;
    const n = termMonths;
    const power = Math.pow(1 + r, n);
    return amount * ((r * power) / (power - 1));
  }
}
