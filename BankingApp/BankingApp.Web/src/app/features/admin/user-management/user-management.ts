import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserService } from '../../../core/services/user-service';
import { AuditLogService } from '../../../core/services/audit-log.service';
import { User, AuditLog } from '../../../core/models';

@Component({
  selector: 'app-user-management',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './user-management.html',
  styleUrl: './user-management.css',
})
export class UserManagement implements OnInit {
  private userService = inject(UserService);
  private auditLogService = inject(AuditLogService);

  users = signal<User[]>([]);
  loading = signal(true);

  // Modal
  showModal = signal(false);
  selectedUser = signal<User | null>(null);
  logs = signal<AuditLog[]>([]);
  logsLoading = signal(false);

  ngOnInit(): void {
    this.userService.getAll().subscribe({
      next: (users) => {
        this.users.set(users);
        this.loading.set(false);
      },
      error: () => this.loading.set(false),
    });
  }

  openLogs(user: User): void {
    this.selectedUser.set(user);
    this.showModal.set(true);
    this.logsLoading.set(true);
    this.logs.set([]);

    this.auditLogService.getByUser(user.id).subscribe({
      next: (logs) => {
        this.logs.set(logs);
        this.logsLoading.set(false);
      },
      error: () => this.logsLoading.set(false),
    });
  }

  closeModal(): void {
    this.showModal.set(false);
    this.selectedUser.set(null);
    this.logs.set([]);
  }

  formatDate(date: string): string {
    return new Date(date).toLocaleString('pt-PT', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
    });
  }

  getActionClass(action: string): string {
    switch (action.toLowerCase()) {
      case 'login':
        return 'badge-login';
      case 'register':
        return 'badge-register';
      case 'transfer':
        return 'badge-transfer';
      default:
        return 'badge-default';
    }
  }
}
