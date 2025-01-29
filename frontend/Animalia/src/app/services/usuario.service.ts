import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  private apiUrl = 'http://localhost:9000/api/usuarios';

  constructor(private http: HttpClient) { }

  getUsuarios(): Observable<any> {
    return this.http.get<any>(this.apiUrl);
  }

  getImagen(nombre: string): Observable<Blob> {
    const url = `${this.apiUrl}/imagen/${nombre}`;
    return this.http.get(url, { responseType: 'blob' });
  }
}
