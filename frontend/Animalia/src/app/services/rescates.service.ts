import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class RescatesService {
  private apiUrl = 'http://localhost:9000/api';
  constructor(private http: HttpClient) {}
  getById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/rescates/${id}`);
  }
  getTodosRescates(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/rescates/todos`);
  }

  añadirRescate(rescate: any, headers: any) {
    console.log('Headers:', headers);
    console.log(rescate);

    return this.http.post(`${this.apiUrl}/rescates/crear`, rescate, {
      headers: headers,
      observe: 'response',
    });
  }

  editarRescate(id: any, ubicacion: any, headers: any) {
    return this.http.put(`${this.apiUrl}/rescates/${id}/ubicacion`, ubicacion, {
      headers: headers,
      observe: 'response',
    });
  }
}
