import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AnimalesService {
  private apiUrl = 'http://localhost:9000/api';

  constructor(private http: HttpClient) {}

  getAnimales(page: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/animales?page=${page}`);
  }
  // Obtener todos los animales para calcular el total de páginas
  getTotalAnimales() {
    return this.http.get<any>(`${this.apiUrl}/animales/todos`);
  }

  getById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  obtenerImagenUrl(nombreImagen: string): string {
    return `${this.apiUrl}/imagen/${nombreImagen}`;
  }
}