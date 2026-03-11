import { Component, inject, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { AccountService } from '../../../core/services/account.service';
import { Account } from '../../../core/models';

@Component({
  selector: 'app-account-list',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './account-list.html',
  styleUrl: './account-list.css',
})
export class AccountList implements OnInit {
  private auth = inject(AuthService);
  private accountService = inject(AccountService);

  accounts = signal<Account[]>([]);
  loading = signal(true);

  get totalBalance(): number {
    return this.accounts().reduce((sum, acc) => sum + acc.balance, 0);
  }

  ngOnInit(): void {
    const user = this.auth.currentUser();
    if (!user) return;

    const userId = this.auth.getUserId();

    this.accountService.getByUser(userId).subscribe({
      next: (accounts) => {
        this.accounts.set(accounts);
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
}
