import { Injectable, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Notification } from '../models';

@Injectable({ providedIn: 'root' })
export class NotificationService {
  private http = inject(HttpClient);
  private base = `${environment.apiUrl}/notification`;

  unreadCount = signal<number>(0);

  getByUser(userId: number): Observable<Notification[]> {
    return this.http.get<Notification[]>(`${this.base}/user/${userId}`);
  }

  getUnread(userId: number): Observable<Notification[]> {
    return this.http
      .get<Notification[]>(`${this.base}/user/${userId}/unread`)
      .pipe(tap((list) => this.unreadCount.set(list.length)));
  }

  markAsRead(id: number): Observable<Notification> {
    return this.http
      .patch<Notification>(`${this.base}/${id}/read`, {})
      .pipe(tap(() => this.unreadCount.update((c) => Math.max(0, c - 1))));
  }
}
