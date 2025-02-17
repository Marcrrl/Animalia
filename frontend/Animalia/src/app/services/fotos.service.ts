import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FotosService {
private apiUrl = 'http://localhost:9000/api';

  constructor(private http: HttpClient) {
  }

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
  añadirFoto(rescateId: number, usuarioId: number, url_foto: string, descripcion: string, ubicacion: any) {
    const token = sessionStorage.getItem('token'); // Obtener el token almacenado
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}` // Enviar el token en el header
    });
    console.log('Headers:', headers);
    const body = {
      rescate: { id: rescateId },
      usuarios: { id: usuarioId },
      url_foto: url_foto,
      descripcion: descripcion,
      ubicacion: ubicacion,
      fecha_captura: new Date().toISOString().split('T')[0] // Fecha actual
    };
    console.log(body);

    return this.http.post(`${this.apiUrl}/fotos`, body,{headers: headers});
  }
}
