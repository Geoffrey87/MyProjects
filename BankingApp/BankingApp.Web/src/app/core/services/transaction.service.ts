import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Transaction, TransferRequest, TransferByIbanRequest } from '../models';

@Injectable({ providedIn: 'root' })
export class TransactionService {
  private http = inject(HttpClient);
  private base = `${environment.apiUrl}/transaction`;

  getByAccount(accountId: number): Observable<Transaction[]> {
    return this.http.get<Transaction[]>(`${this.base}/account/${accountId}`);
  }

  getById(id: number): Observable<Transaction> {
    return this.http.get<Transaction>(`${this.base}/${id}`);
  }

  getByDateRange(accountId: number, startDate: string, endDate: string): Observable<Transaction[]> {
    const params = new HttpParams().set('startDate', startDate).set('endDate', endDate);
    return this.http.get<Transaction[]>(`${this.base}/account/${accountId}/range`, { params });
  }

  transfer(req: TransferRequest): Observable<Transaction> {
    return this.http.post<Transaction>(`${this.base}/transfer`, req);
  }

  transferByIban(req: TransferByIbanRequest): Observable<void> {
    return this.http.post<void>(`${this.base}/transfer-iban`, req);
  }
}
