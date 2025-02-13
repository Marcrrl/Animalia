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
  showUpdateForm: boolean = false;
  newUsuario: any = {};
  selectedUsuario: any = {};
  errorMessage: string = '';

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
    this.showUpdateForm = false;
    this.newUsuario = {};
  }

  showUpdateUserForm(usuario: any) {
    this.showUpdateForm = true;
    this.showForm = false;
    this.selectedUsuario = { ...usuario };
  }

  cancelAddUser() {
    this.showForm = false;
  }

  cancelUpdateUser() {
    this.showUpdateForm = false;
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
    }, error => {
      console.error('Error al obtener usuarios:', error);
    });
  }

  addUsuario() {
    this.http.post('http://localhost:9000/auth/add', this.newUsuario).subscribe(() => {
      this.getUsuarios();
      this.showForm = false;
      this.errorMessage = '';
    }, error => {
      console.error('Error al añadir usuario:', error);
      if (error.status === 400 && error.error.message.includes('telefono ya existe')) {
        this.errorMessage = 'El teléfono ya existe. Por favor, use un número diferente.';
      } else {
        this.errorMessage = 'Error al añadir usuario. Por favor, inténtelo de nuevo.';
      }
    });
  }

  updateUsuario() {
    const usuarioActualizado = {
      id: this.selectedUsuario.id,
      nombre: this.selectedUsuario.nombre,
      apellido: this.selectedUsuario.apellido,
      email: this.selectedUsuario.email,
      password: this.selectedUsuario.password,
      telefono: this.selectedUsuario.telefono,
      direccion: this.selectedUsuario.direccion,
      url_foto_perfil: this.selectedUsuario.url_foto_perfil,
      tipo_usuario: this.selectedUsuario.tipo_usuario,
      fecha_registro: this.selectedUsuario.fecha_registro,
      cantidad_rescates: this.selectedUsuario.cantidad_rescates
    };

    this.http.put(`http://localhost:9000/api/usuarios`, usuarioActualizado).subscribe(() => {
      this.getUsuarios();
      this.showUpdateForm = false;
      this.errorMessage = '';
    }, error => {
      console.error('Error al actualizar usuario:', error);
      if (error.status === 400 && error.error.message.includes('telefono ya existe')) {
        this.errorMessage = 'El teléfono ya existe. Por favor, use un número diferente.';
      } else {
        this.errorMessage = 'Error al actualizar usuario. Por favor, inténtelo de nuevo.';
      }
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
