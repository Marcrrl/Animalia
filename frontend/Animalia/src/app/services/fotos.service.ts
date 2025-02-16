import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HttpHeaders } from '@capacitor/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FotosService {
private apiUrl = 'http://localhost:9000/api';

  constructor(private http: HttpClient) {}

  getFotos(page: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/fotos?page=${page}`);
  }
  // Obtener todos los animales para calcular el total de páginas
  getTotalFotos() {
    return this.http.get<any>(`${this.apiUrl}/fotos/todos`);
  }

  getById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/fotos/${id}`);
  }

  obtenerImagenUrl(nombreImagen: string): string {
    return `${this.apiUrl}/imagen/${nombreImagen}`;
  }
  añadirFoto(foto: any) {
    return this.http.post('http://localhost:9000/api/fotos', foto);
  }
}
