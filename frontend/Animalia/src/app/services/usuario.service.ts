import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
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
    const url = `http://localhost:9000/api/imagen/${url_foto_perfil}`;
    return this.http.get(url, { responseType: 'blob' });
  }

  getImagenesUsuario(userId: string) {
    return this.http.get<any[]>(`http://localhost:9000/api/fotos/usuario/${userId}/base64`);
  }

  subirImagenPerfil(file: File, headers: HttpHeaders): Observable<any> {
    const formData = new FormData();
    formData.append('imagen', file);

    return this.http.post('http://localhost:9000/api/subir-imagen', formData, {
      headers: headers,
      observe: 'response'
    });
  }
}
