import { Injectable, signal, WritableSignal } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';

export interface UserDTO {
  id?: number;
  name: string;
  email?: string;
  roles?: string[];
}

const STORAGE_KEY = 'currentUser';
const AUTH_TOKEN_KEY = 'basicAuth';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private base = '/api/users';

  private currentUserSubject = new BehaviorSubject<UserDTO | null>(this.loadFromStorage());
  currentUser$ = this.currentUserSubject.asObservable();

  userSignal: WritableSignal<UserDTO | null> = signal(this.loadFromStorage());

  constructor(private http: HttpClient) {}

  register(name: string, password: string): Observable<UserDTO> {
    return this.http
      .post<UserDTO>(`${this.base}/register`, { name, password })
      .pipe(tap((user) => this.setCurrentUser(user)));
  }

  login(name: string, password: string): Observable<UserDTO> {
    const token = btoa(`${name}:${password}`);
    const headers = new HttpHeaders({ Authorization: `Basic ${token}` });
    return this.http.get<UserDTO>(`${this.base}/me`, { headers }).pipe(
      tap((user) => {
        try {
          localStorage.setItem(AUTH_TOKEN_KEY, token);
        } catch (e) {}
        this.setCurrentUser(user);
      })
    );
  }

  me(): Observable<UserDTO> {
    const token = this.loadAuthToken();
    const headers = token ? new HttpHeaders({ Authorization: `Basic ${token}` }) : undefined;
    return this.http.get<UserDTO>(`${this.base}/me`, headers ? { headers } : {}).pipe(
      tap((user) => this.setCurrentUser(user)),
      catchError(() => of(null as any))
    );
  }

  setCurrentUser(user: UserDTO | null) {
    this.currentUserSubject.next(user);
    this.userSignal.set(user);
    if (user) {
      try {
        localStorage.setItem(STORAGE_KEY, JSON.stringify(user));
      } catch (e) {}
    } else {
      localStorage.removeItem(STORAGE_KEY);
    }
  }

  logout() {
    this.setCurrentUser(null);
    try {
      localStorage.removeItem(AUTH_TOKEN_KEY);
    } catch (e) {}
  }

  private loadFromStorage(): UserDTO | null {
    try {
      const raw = localStorage.getItem(STORAGE_KEY);
      return raw ? (JSON.parse(raw) as UserDTO) : null;
    } catch (e) {
      return null;
    }
  }

  private loadAuthToken(): string | null {
    try {
      return localStorage.getItem(AUTH_TOKEN_KEY);
    } catch (e) {
      return null;
    }
  }
}
