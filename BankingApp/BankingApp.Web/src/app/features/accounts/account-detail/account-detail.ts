import { Component, inject, OnInit, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { AccountService } from '../../../core/services/account.service';
import { TransactionService } from '../../../core/services/transaction.service';
import { Account, Transaction } from '../../../core/models';

@Component({
  selector: 'app-account-detail',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './account-detail.html',
  styleUrl: './account-detail.css',
})
export class AccountDetail implements OnInit {
  private route = inject(ActivatedRoute);
  private accountService = inject(AccountService);
  private transactionService = inject(TransactionService);

  account = signal<Account | null>(null);
  transactions = signal<Transaction[]>([]);
  loading = signal(true);

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));

    this.accountService.getById(id).subscribe({
      next: (account) => {
        this.account.set(account);
        this.loadTransactions(id);
      },
      error: () => this.loading.set(false),
    });
  }

  private loadTransactions(accountId: number): void {
    this.transactionService.getByAccount(accountId).subscribe({
      next: (transactions) => {
        this.transactions.set(transactions);
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

  formatDate(date: string): string {
    return new Date(date).toLocaleDateString('en-GB', {
      day: '2-digit',
      month: 'short',
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
    });
  }

  isCredit(transaction: Transaction, accountId: number): boolean {
    return transaction.toAccountId === accountId;
  }
}
