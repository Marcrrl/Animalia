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
  animales: any[] = [];
  loggedInUserId: number = Number(sessionStorage.getItem('id'));
  showForm: boolean = false;
  showUpdateForm: boolean = false;
  showAnimalForm: boolean = false;
  showUpdateAnimalForm: boolean = false;
  newUsuario: any = {};
  selectedUsuario: any = {};
  newAnimal: any = {};
  selectedAnimal: any = {};
  errorMessage: string = '';

  constructor(private router: Router, private http: HttpClient) { }

  ngOnInit() {
    this.loggedInUserId = Number(sessionStorage.getItem('id'));
    this.getUsuarios();
    this.getAnimales();
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

  // Métodos para gestionar cada tipo de entidad
  getEmpresas() {
    // Lógica para obtener la lista de empresas
  }

  getAnimales() {
    this.http.get<any[]>('http://localhost:9000/api/animales/todos').subscribe(data => {
      this.animales = data;
    }, error => {
      console.error('Error al obtener animales:', error);
    });
  }

  showAddAnimalForm() {
    this.showAnimalForm = true;
    this.showUpdateAnimalForm = false;
    this.newAnimal = {};
  }

  displayUpdateAnimalForm(animal: any) {
    this.showUpdateAnimalForm = true;
    this.showAnimalForm = false;
    this.selectedAnimal = { ...animal };
  }

  cancelAddAnimal() {
    this.showAnimalForm = false;
  }

  cancelUpdateAnimal() {
    this.showUpdateAnimalForm = false;
  }

  addAnimal() {
    console.log('Añadiendo animal:', this.newAnimal);
    const nuevoAnimal = {
      nombre_comun: this.newAnimal.nombre_comun,
      especie: this.newAnimal.especie,
      descripcion: this.newAnimal.descripcion,
      familia: this.newAnimal.familia,
      estado_conservacion: this.newAnimal.estado_conservacion
    };
    this.http.post('http://localhost:9000/api/animales', nuevoAnimal).subscribe(() => {
      this.getAnimales();
      this.showAnimalForm = false;
      this.errorMessage = '';
    }, error => {
      console.error('Error al añadir animal:', error);
      this.errorMessage = 'Error al añadir animal. Por favor, inténtelo de nuevo.';
    });
  }

  updateAnimal() {
    const animalActualizado = {
      id: this.selectedAnimal.id,
      nombre_comun: this.selectedAnimal.nombre_comun,
      especie: this.selectedAnimal.especie,
      descripcion: this.selectedAnimal.descripcion,
      familia: this.selectedAnimal.familia,
      estado_conservacion: this.selectedAnimal.estado_conservacion
    };

    console.log('Actualizando animal:', animalActualizado);
    this.http.put(`http://localhost:9000/api/animales`, animalActualizado).subscribe(() => {
      this.getAnimales();
      this.showUpdateAnimalForm = false;
      this.errorMessage = '';
    }, error => {
      console.error('Error al actualizar animal:', error);
      this.errorMessage = 'Error al actualizar animal. Por favor, inténtelo de nuevo.';
    });
  }

  deleteAnimal(id: number) {
    this.http.delete(`http://localhost:9000/api/animales/${id}`).subscribe(() => {
      this.getAnimales();
    }, error => {
      console.error('Error al eliminar animal:', error);
    });
  }

  uploadImage(event: any) {
    const file = event.target.files[0];
    const formData = new FormData();
    formData.append('imagen', file);

    this.http.post<{ url: string }>('http://localhost:9000/api/subir-imagen', formData).subscribe(response => {
      console.log('Imagen subida:', response);
      this.newAnimal.foto = response.url;
    }, error => {
      console.error('Error al subir imagen:', error);
    });
  }
}