import { Injectable, signal, computed, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { tap } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { AuthResponse, LoginRequest, RegisterRequest } from '../models';
import { CLAIMS } from '../constants/claims.constants';
import { NotificationService } from './notification.service';


@Injectable({ providedIn: 'root' })
export class AuthService {
  private http = inject(HttpClient);
  private router = inject(Router);
  private notificationService = inject(NotificationService);

  private _authData = signal<AuthResponse | null>(this.loadFromStorage());
  private _token = signal<string | null>(sessionStorage.getItem('banking_token'));

  readonly token = this._token.asReadonly();
  readonly isAuthenticated = computed(() => !!this._authData());
  readonly isAdmin = computed(() => this._authData()?.role === 'Admin');
  readonly fullName = computed(() => this._authData()?.fullName ?? '');
  readonly currentUser = computed(() => this._authData());

  private loadFromStorage(): AuthResponse | null {
    try {
      const raw = sessionStorage.getItem('banking_auth');
      return raw ? JSON.parse(raw) : null;
    } catch {
      return null;
    }
  }

  login(request: LoginRequest): Observable<AuthResponse> {
    return this.http
      .post<AuthResponse>(`${environment.apiUrl}/auth/login`, request)
      .pipe(tap((res) => this.storeSession(res)));
  }

  register(request: RegisterRequest): Observable<AuthResponse> {
    return this.http
      .post<AuthResponse>(`${environment.apiUrl}/auth/register`, request)
      .pipe(tap((res) => this.storeSession(res)));
  }

  getUserId(): number {
    const token = sessionStorage.getItem('banking_token');
    if (!token) return 0;
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      return Number(payload[CLAIMS.NAMEID] ?? 0);
    } catch {
      return 0;
    }
  }

  logout(): void {
    sessionStorage.removeItem('banking_token');
    sessionStorage.removeItem('banking_auth');
    this._authData.set(null);
    this._token.set(null);
    this.notificationService.unreadCount.set(0);
    this.router.navigate(['/login']);
  }

  isTokenExpired(): boolean {
    const data = this._authData();
    if (!data) return true;
    try {
      return new Date(data.expiresAt) < new Date();
    } catch {
      return true;
    }
  }

  private storeSession(res: AuthResponse): void {
    sessionStorage.setItem('banking_token', res.token);
    sessionStorage.setItem('banking_auth', JSON.stringify(res));
    this._authData.set(res);
    this._token.set(res.token);
  }
}
