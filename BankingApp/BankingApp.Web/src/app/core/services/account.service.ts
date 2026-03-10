import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Account, DepositWithdrawRequest } from '../models';

@Injectable({ providedIn: 'root' })
export class AccountService {
  private http = inject(HttpClient);
  private base = `${environment.apiUrl}/account`;

  getByUser(userId: number): Observable<Account[]> {
    return this.http.get<Account[]>(`${this.base}/user/${userId}`);
  }

  getById(id: number): Observable<Account> {
    return this.http.get<Account>(`${this.base}/${id}`);
  }

  getBalance(id: number): Observable<{ balance: number }> {
    return this.http.get<{ balance: number }>(`${this.base}/${id}/balance`);
  }

  deposit(id: number, req: DepositWithdrawRequest): Observable<Account> {
    return this.http.post<Account>(`${this.base}/${id}/deposit`, req);
  }

  withdraw(id: number, req: DepositWithdrawRequest): Observable<Account> {
    return this.http.post<Account>(`${this.base}/${id}/withdraw`, req);
  }
}
