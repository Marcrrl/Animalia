import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-gestion',
  templateUrl: './gestion.page.html',
  styleUrls: ['./gestion.page.scss'],
  standalone: false
})
export class GestionPage implements OnInit {
  currentView: string = '';
  usuarios: any[] = [];
  loggedInUserId: number = Number(sessionStorage.getItem('id'));
  showForm: boolean = false;
  newUsuario: any = {};

  constructor(private router: Router, private http: HttpClient) { }

  ngOnInit() {
    this.loggedInUserId = Number(sessionStorage.getItem('id'));
    this.getUsuarios();
  }

  showList(view: string) {
    this.currentView = view;
  }

  goBack() {
    this.currentView = '';
  }

  showAddUserForm() {
    this.showForm = true;
    this.newUsuario = {};
  }

  cancelAddUser() {
    this.showForm = false;
  }

  // Métodos para gestionar cada tipo de entidad
  getEmpresas() {
    // Lógica para obtener la lista de empresas
  }

  getAnimales() {
    // Lógica para obtener la lista de animales
  }

  getUsuarios() {
    this.http.get<any[]>('http://localhost:9000/api/usuarios/todos').subscribe(data => {
      this.usuarios = data.filter(usuario => usuario.id !== this.loggedInUserId);
      console.log('Usuarios obtenidos:', this.usuarios);
    }, error => {
      console.error('Error al obtener usuarios:', error);
    });
  }

  addUsuario() {
    console.log('Nuevo usuario:', this.newUsuario);
    this.http.post('http://localhost:9000/auth/add', this.newUsuario).subscribe(() => {
      this.getUsuarios();
      this.showForm = false;
    }, error => {
      console.error('Error al añadir usuario:', error);
    });
  }

  updateUsuario(usuario: any) {
    this.http.put('http://localhost:9000/api/usuarios', usuario).subscribe(() => {
      this.getUsuarios();
    }, error => {
      console.error('Error al actualizar usuario:', error);
    });
  }

  deleteUsuario(id: number) {
    this.http.delete(`http://localhost:9000/api/usuarios/${id}`).subscribe(() => {
      this.getUsuarios();
    }, error => {
      console.error('Error al eliminar usuario:', error);
    });
  }

  getRescates() {
    // Lógica para obtener la lista de rescates
  }
}
