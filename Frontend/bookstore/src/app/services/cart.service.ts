import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';
import { BookDTO } from  './book.service';

@Injectable({ providedIn: 'root'})
export class CartService {
  private base = '/api/books';

  constructor(private http: HttpClient, private auth: AuthService){}

  addToCart(bookId: number, cant: number): Observable<BookDTO[]>{
    const token = this.auth.getAuthToken();
    return this.http.put<BookDTO[]>(`${this.base}/add/${bookId}`,cant,
      {headers: {Authorization: `Basic ${token}`},
    });
  }

  removeFromCart(bookId: number, cant: number): Observable<BookDTO[]>{
    const token = this.auth.getAuthToken();
    return this.http.put<BookDTO[]>(`${this.base}/remove/${bookId}`,cant,
      {headers:{ Authorization: `Basic ${token}`}
    });
  }

  getCart(): Observable<BookDTO[]>{
    const token= this.auth.getAuthToken();
    return this.http.get<BookDTO[]>(`${this.base}/cart`,
      {headers: {Authorization: `Basic ${token}`}
    });
  }
}
