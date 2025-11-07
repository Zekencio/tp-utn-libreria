import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface SellerProfileDTO {
  id?: number;
  name: string;
  address: string;
}

export interface SellerProfileDTOFull extends SellerProfileDTO {
  inventory?: any[];
}

@Injectable({ providedIn: 'root' })
export class SellerProfileService {
  private base = '/api/sellerProfiles';

  constructor(private http: HttpClient) {}

  createSellerProfile(dto: { name: string; address: string }): Observable<SellerProfileDTO> {
    try {
      const token = localStorage.getItem('basicAuth');
      if (token) {
        return this.http.post<SellerProfileDTO>(this.base, dto, {
          headers: { Authorization: `Basic ${token}` } as any,
        });
      }
    } catch (e) {}
    return this.http.post<SellerProfileDTO>(this.base, dto);
  }

  getMySellerProfile(): Observable<SellerProfileDTOFull> {
    try {
      const token = localStorage.getItem('basicAuth');
      if (token) {
        return this.http.get<SellerProfileDTOFull>(`${this.base}/me`, {
          headers: { Authorization: `Basic ${token}` } as any,
        });
      }
    } catch (e) {}
    return this.http.get<SellerProfileDTOFull>(`${this.base}/me`);
  }

  updateSellerProfile(dto: { name?: string; address?: string }): Observable<SellerProfileDTO> {
    try {
      const token = localStorage.getItem('basicAuth');
      if (token) {
        return this.http.put<SellerProfileDTO>(`${this.base}/update`, dto, {
          headers: { Authorization: `Basic ${token}` } as any,
        });
      }
    } catch (e) {}
    return this.http.put<SellerProfileDTO>(`${this.base}/update`, dto);
  }
}
