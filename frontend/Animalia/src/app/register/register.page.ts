import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-register',
  templateUrl: './register.page.html',
  styleUrls: ['./register.page.scss'],
  standalone: false,
})
export class RegisterPage implements OnInit {
  tipo: string = '';
  user: any = {};
  company: any = {};
  errorMessage: string = '';
  newUsuario: any = {};

  constructor(private http: HttpClient) { }

  ngOnInit() {
  }

  showContent(tipo: string) {
    this.tipo = tipo;
  }

  addUsuario() {
    this.newUsuario.tipoUsuario = 'USER';
    this.newUsuario.fecha_registro = new Date().toISOString().split('T')[0]; // Set default fecha_registro
    console.log('Sending user data:', JSON.stringify(this.newUsuario)); // Log JSON data
    this.http.post('http://localhost:9000/auth/add', this.newUsuario).subscribe(() => {
      this.errorMessage = '';
      this.newUsuario = {}; // Reset form
    }, error => {
      console.error('Error al añadir usuario:', error);
      if (error.status === 400 && error.error.message.includes('telefono ya existe')) {
        this.errorMessage = 'El teléfono ya existe. Por favor, use un número diferente.';
      } else {
        this.errorMessage = 'Error al añadir usuario. Por favor, inténtelo de nuevo.';
      }
    });
  }

  registerCompany() {
    this.http.post('http://localhost:9000/api/empresa', this.company)
  }
}