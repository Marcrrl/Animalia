import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RescatesService {
  private apiUrl = 'http://localhost:9000/api';
  constructor(private http: HttpClient) {
   }
getById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/rescates/${id}`);
  }
}
