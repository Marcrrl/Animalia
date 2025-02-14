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

  getUsuarioById(id: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  getImagen(url_foto_perfil: string): Observable<Blob> {
    const url = `${this.apiUrl}/imagen/${url_foto_perfil}`;
    return this.http.get(url, { responseType: 'blob' });
  }

  subirImagenPerfil(file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post(`http://localhost:9000/api/subir-imagen`, formData, {
      observe: 'response'
    });
  }
}
