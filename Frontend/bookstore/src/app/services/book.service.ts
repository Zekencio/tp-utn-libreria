import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthorDTO } from './author.service';
import { GenreDTO } from './genre.service';
import { SellerProfileDTO } from './seller-profile.service';

export interface BookDTO {
  id?: number;
  name: string;
  description: string;
  stock: number;
  author: AuthorDTO;
  genres: GenreDTO[];
  seller: SellerProfileDTO;
}

@Injectable({ providedIn: 'root' })
export class BookService {
  private base = '/api/books';

  constructor(private http: HttpClient) {}

  getAll(): Observable<BookDTO[]> {
    return this.http.get<BookDTO[]>(this.base);
  }

  create(payload: { name: string, description: string, stock: number, author: AuthorDTO, genres: GenreDTO[],seller: SellerProfileDTO }): Observable<BookDTO> {
    const token = localStorage.getItem('basicAuth');
    const headers = token ? new HttpHeaders({ Authorization: `Basic ${token}` }) : undefined;
    return this.http.post<BookDTO>(this.base, payload, headers ? { headers } : {});
  }

  update(id: number, payload: { name: string, description: string, stock: number, author: AuthorDTO, genres: GenreDTO[],seller: SellerProfileDTO }): Observable<BookDTO> {
    const token = localStorage.getItem('basicAuth');
    const headers = token ? new HttpHeaders({ Authorization: `Basic ${token}` }) : undefined;
    return this.http.put<BookDTO>(`${this.base}/${id}`, payload, headers ? { headers } : {});
  }

  delete(id: number): Observable<void> {
    const token = localStorage.getItem('basicAuth');
    const headers = token ? new HttpHeaders({ Authorization: `Basic ${token}` }) : undefined;
    return this.http.delete<void>(`${this.base}/${id}`, headers ? { headers } : {});
  }
}
