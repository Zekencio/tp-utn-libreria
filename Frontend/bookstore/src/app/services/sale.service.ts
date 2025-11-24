import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface SaleDTO {
  id?: number;
  date?: string;
  user?: any;
  card?: any;
  books?: any[];
}

@Injectable({ providedIn: 'root' })
export class SaleService {
  private base = '/api/sales';

  constructor(private http: HttpClient) {}

  getAll(): Observable<SaleDTO[]> {
    return this.http.get<SaleDTO[]>(this.base);
  }

  create(cardId: number): Observable<SaleDTO> {
    const token = localStorage.getItem('basicAuth');
    const headers = token ? new HttpHeaders({ Authorization: `Basic ${token}` }) : undefined;
    return this.http.post<SaleDTO>(this.base, cardId, headers ? { headers } : {});
  }
}
