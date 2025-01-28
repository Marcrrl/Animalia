import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AnimalesService {

  private apiUrl = 'http://localhost:9000/api';

  constructor(private http: HttpClient) { }

  getAnimales(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/animales`);

  }
}
