import { Component, inject, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';
import { AccountService } from '../../core/services/account.service';
import { NotificationService } from '../../core/services/notification.service';
import { Account } from '../../core/models';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard implements OnInit {
  private auth = inject(AuthService);
  private accountService = inject(AccountService);
  private notifService = inject(NotificationService);

  accounts = signal<Account[]>([]);
  loading = signal(true);

  get totalBalance(): number {
    return this.accounts().reduce((sum, acc) => sum + acc.balance, 0);
  }

  get activeAccounts(): number {
    return this.accounts().filter((a) => a.isActive).length;
  }

  get unreadCount(): number {
    return this.notifService.unreadCount();
  }

  get userName(): string {
    return this.auth.fullName();
  }

  ngOnInit(): void {
    const user = this.auth.currentUser();
    if (!user) return;

    const userId = this.auth.getUserId();

    this.accountService.getByUser(userId).subscribe({
      next: (accounts) => {
        this.accounts.set(accounts);
        this.loading.set(false);

        if (accounts.length > 0) {
          this.notifService.getUnread(userId).subscribe();
        }
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
