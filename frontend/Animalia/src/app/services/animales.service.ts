import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Animal {
  id_animal: number;
  especie: string;
  nombre_comun: string;
  descripcion: string;
  estado_animal: string;
  foto: string; // Nueva propiedad para la foto
}

@Injectable({
  providedIn: 'root'
})
export class AnimalesService {
  private apiUrl = 'http://localhost:9000/api';

  constructor(private http: HttpClient) { }

  getAnimales(): Observable<Animal> {
    return this.http.get<any>(`${this.apiUrl}/animales`);

  }
}
