import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { AuditLog } from '../models';

@Injectable({ providedIn: 'root' })
export class AuditLogService {
  private http = inject(HttpClient);
  private base = `${environment.apiUrl}/auditlog`;

  getByUser(userId: number): Observable<AuditLog[]> {
    return this.http.get<AuditLog[]>(`${this.base}/user/${userId}`);
  }

  getById(id: number): Observable<AuditLog> {
    return this.http.get<AuditLog>(`${this.base}/${id}`);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/${id}`);
  }
}
