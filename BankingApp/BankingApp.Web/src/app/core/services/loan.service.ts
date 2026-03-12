import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Loan } from '../models';

@Injectable({ providedIn: 'root' })
export class LoanService {
  private http = inject(HttpClient);
  private base = `${environment.apiUrl}/loan`;

  getByUser(userId: number): Observable<Loan[]> {
    return this.http.get<Loan[]>(`${this.base}/user/${userId}`);
  }

  getById(id: number): Observable<Loan> {
    return this.http.get<Loan>(`${this.base}/${id}`);
  }

  getAll(): Observable<Loan[]> {
    return this.http.get<Loan[]>(this.base);
  }

  create(req: { accountId: number; amount: number; termMonths: number }): Observable<Loan> {
    return this.http.post<Loan>(this.base, req);
  }

  approve(id: number): Observable<Loan> {
    return this.http.patch<Loan>(`${this.base}/${id}/approve`, {});
  }

  reject(id: number): Observable<Loan> {
    return this.http.patch<Loan>(`${this.base}/${id}/reject`, {});
  }
}
