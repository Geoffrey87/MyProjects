import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { BankingService, Transaction } from '../models';

@Injectable({ providedIn: 'root' })
export class ServicePaymentService {
  private http = inject(HttpClient);
  private base = `${environment.apiUrl}/service`;

  getAll(): Observable<BankingService[]> {
    return this.http.get<BankingService[]>(this.base);
  }

  pay(serviceId: number, accountId: number, amount: number): Observable<void> {
    return this.http.post<void>(
      `${this.base}/${serviceId}/pay?accountId=${accountId}&amount=${amount}`,
      {},
    );
  }
}
