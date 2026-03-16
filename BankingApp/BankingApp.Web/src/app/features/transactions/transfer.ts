import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AccountService } from '../../core/services/account.service';
import { TransactionService } from '../../core/services/transaction.service';
import { AuthService } from '../../core/services/auth.service';
import { Account } from '../../core/models';

@Component({
  selector: 'app-transfer',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './transfer.html',
  styleUrl: './transfer.css',
})
export class TransferComponent implements OnInit {
  private auth = inject(AuthService);
  private accountService = inject(AccountService);
  private transactionService = inject(TransactionService);
  private fb = inject(FormBuilder);
  private router = inject(Router);

  accounts = signal<Account[]>([]);
  loading = signal(true);
  submitting = signal(false);
  success = signal(false);
  errorMessage = signal('');

  transferForm = this.fb.group({
    fromAccountId: [0, [Validators.required, Validators.min(1)]],
    toIBAN: ['', [Validators.required, Validators.minLength(10)]],
    amount: [null as number | null, [Validators.required, Validators.min(0.01)]],
    description: [''],
  });

  ngOnInit(): void {
    const userId = this.auth.getUserId();
    this.accountService.getByUser(userId).subscribe({
      next: (accounts) => {
        this.accounts.set(accounts.filter((a) => a.isActive));
        if (accounts.length > 0) {
          this.transferForm.patchValue({ fromAccountId: accounts[0].id });
        }
        this.loading.set(false);
      },
      error: () => this.loading.set(false),
    });
  }

  getSelectedAccount(): Account | undefined {
    const id = this.transferForm.value.fromAccountId;
    return this.accounts().find((a) => a.id === id);
  }

  submit(): void {
    if (this.transferForm.invalid) return;

    this.submitting.set(true);
    this.errorMessage.set('');
    this.success.set(false);

    const { fromAccountId, toIBAN, amount, description } = this.transferForm.value;

    this.transactionService
      .transferByIban({
        fromAccountId: fromAccountId!,
        toIBAN: toIBAN!.trim(),
        amount: amount!,
        description: description ?? '',
      })
      .subscribe({
        next: () => {
          this.submitting.set(false);
          this.success.set(true);
          this.transferForm.patchValue({ toIBAN: '', amount: null, description: '' });
          // Refresh accounts to show updated balance
          const userId = this.auth.getUserId();
          this.accountService.getByUser(userId).subscribe((accounts) => {
            this.accounts.set(accounts.filter((a) => a.isActive));
          });
        },
        error: (err) => {
          this.submitting.set(false);
          this.errorMessage.set(err?.error?.message ?? 'Transfer failed. Please try again.');
        },
      });
  }

  goBack(): void {
    this.router.navigate(['/dashboard']);
  }
}
