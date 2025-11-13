import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface AuthorDTO {
  id?: number;
  name: string;
  birthDate?: Date | null;
}

@Injectable({ providedIn: 'root' })
export class AuthorService {
  private base = '/api/authors';

  constructor(private http: HttpClient) {}

  getAll(): Observable<AuthorDTO[]> {
    return this.http.get<AuthorDTO[]>(this.base);
  }

  create(payload: { name: string; birthDate?: Date }): Observable<AuthorDTO> {
    const token = localStorage.getItem('basicAuth');
    const headers = token ? new HttpHeaders({ Authorization: `Basic ${token}` }) : undefined;
    return this.http.post<AuthorDTO>(this.base, payload, headers ? { headers } : {});
  }

  update(id: number, payload: { name: string; birthDate?: Date }): Observable<AuthorDTO> {
    const token = localStorage.getItem('basicAuth');
    const headers = token ? new HttpHeaders({ Authorization: `Basic ${token}` }) : undefined;
    return this.http.put<AuthorDTO>(`${this.base}/${id}`, payload, headers ? { headers } : {});
  }

  delete(id: number): Observable<void> {
    const token = localStorage.getItem('basicAuth');
    const headers = token ? new HttpHeaders({ Authorization: `Basic ${token}` }) : undefined;
    return this.http.delete<void>(`${this.base}/${id}`, headers ? { headers } : {});
  }
}
