import { Component, inject, OnInit, signal } from '@angular/core';
import { NotificationService } from '../../../core/services/notification.service';
import { AuthService } from '../../../core/services/auth.service';
import { Notification } from '../../../core/models';

@Component({
  selector: 'app-notification-list',
  standalone: true,
  imports: [],
  templateUrl: './notification-list.html',
  styleUrl: './notification-list.css',
})
export class NotificationList implements OnInit {
  private auth = inject(AuthService);
  private notificationService = inject(NotificationService);

  notifications = signal<Notification[]>([]);
  loading = signal(true);

  ngOnInit(): void {
    const userId = this.auth.getUserId();
    this.notificationService.getByUser(userId).subscribe({
      next: (notifications) => {
        this.notifications.set(notifications);
        this.loading.set(false);
      },
      error: () => this.loading.set(false),
    });
  }

  markAsRead(id: number): void {
    this.notificationService.markAsRead(id).subscribe({
      next: () => {
        this.notifications.update((list) =>
          list.map((n) => (n.id === id ? { ...n, isRead: true } : n)),
        );
        this.notificationService.unreadCount.update((c) => Math.max(0, c - 1));
      },
    });
  }

  markAllAsRead(): void {
    this.notifications()
      .filter((n) => !n.isRead)
      .forEach((n) => this.markAsRead(n.id));
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

  get unreadCount(): number {
    return this.notifications().filter((n) => !n.isRead).length;
  }
}
