import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EmpresasService {
  private apiUrl = 'http://localhost:9000/api/empresas';

  constructor(private http: HttpClient) { }

  getEmpresas(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}`);
  }

  getById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  /*obtenerImagenUrl(nombreImagen: string): string {
    return `${this.apiUrl}/imagen/${nombreImagen}`;
  }*/
}
