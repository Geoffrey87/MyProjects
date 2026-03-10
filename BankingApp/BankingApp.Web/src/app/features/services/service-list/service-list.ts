import { Component, inject, OnInit, signal } from '@angular/core';
import { AuthService } from '../../../core/services/auth.service';
import { AccountService } from '../../../core/services/account.service';
import { ServicePaymentService } from '../../../core/services/service-payment.service';
import { BankingService, Account } from '../../../core/models';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-service-list',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './service-list.html',
  styleUrl: './service-list.css',
})
export class ServiceList implements OnInit {
  private auth = inject(AuthService);
  private accountService = inject(AccountService);
  private servicePaymentService = inject(ServicePaymentService);

  services = signal<BankingService[]>([]);
  accounts = signal<Account[]>([]);
  loading = signal(true);

  selectedAccountId = signal<number | null>(null);
  selectedService = signal<BankingService | null>(null);
  customAmount = signal<number>(0);
  paying = signal(false);
  successMessage = signal('');
  errorMessage = signal('');

  get categories(): string[] {
    return [...new Set(this.services().map((s) => s.category))];
  }

  getServicesByCategory(category: string): BankingService[] {
    return this.services().filter((s) => s.category === category);
  }

  ngOnInit(): void {
    const user = this.auth.currentUser();
    if (!user) return;
    const userId = Number((user as any).id ?? (user as any).nameid ?? 1);

    this.servicePaymentService.getAll().subscribe({
      next: (services) => {
        this.services.set(services);
        this.loading.set(false);
      },
      error: () => this.loading.set(false),
    });

    this.accountService.getByUser(userId).subscribe({
      next: (accounts) => {
        this.accounts.set(accounts.filter((a) => a.isActive));
        if (accounts.length > 0) {
          this.selectedAccountId.set(accounts[0].id);
        }
      },
    });
  }

  selectService(service: BankingService): void {
    this.selectedService.set(service);
    this.successMessage.set('');
    this.errorMessage.set('');
    if (service.isFixedAmount) {
      this.customAmount.set(service.amount);
    } else {
      this.customAmount.set(0);
    }
  }

  cancelPayment(): void {
    this.selectedService.set(null);
    this.customAmount.set(0);
    this.successMessage.set('');
    this.errorMessage.set('');
  }

  confirmPayment(): void {
    const service = this.selectedService();
    const accountId = this.selectedAccountId();
    if (!service || !accountId) return;

    this.paying.set(true);
    this.errorMessage.set('');

    this.servicePaymentService.pay(service.id, accountId, this.customAmount()).subscribe({
      next: () => {
        this.successMessage.set(`Payment for ${service.name} was successful!`);
        this.selectedService.set(null);
        this.paying.set(false);
      },
      error: (err) => {
        this.errorMessage.set(err.error?.message ?? 'Payment failed. Please try again.');
        this.paying.set(false);
      },
    });
  }

  formatCurrency(value: number): string {
    return new Intl.NumberFormat('pt-PT', {
      style: 'currency',
      currency: 'EUR',
    }).format(value);
  }
}
