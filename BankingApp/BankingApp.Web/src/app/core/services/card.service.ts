import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Card } from '../models';

@Injectable({ providedIn: 'root' })
export class CardService {
  private http = inject(HttpClient);
  private base = `${environment.apiUrl}/card`;

  getByAccount(accountId: number): Observable<Card[]> {
    return this.http.get<Card[]>(`${this.base}/account/${accountId}`);
  }

  getById(id: number): Observable<Card> {
    return this.http.get<Card>(`${this.base}/${id}`);
  }

  toggle(id: number): Observable<Card> {
    return this.http.patch<Card>(`${this.base}/${id}/toggle`, {});
  }
}
