import { Component, OnInit, Inject } from '@angular/core';
import { UsuarioService } from '../services/usuario.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-perfil',
  templateUrl: 'perfil.page.html',
  styleUrls: ['perfil.page.scss'],
  standalone: false,
})
export class PerfilPage implements OnInit {
  usuario: any;
  camposActivos: boolean = false;
  imagenPerfil: any;

  constructor(@Inject(UsuarioService) private usuarioService: UsuarioService, private http: HttpClient) {}

  ngOnInit() {
    this.usuarioService.getUsuarios().subscribe((data: any) => {
      this.usuario = data[0];
      this.cargarImagenPerfil(this.usuario.url_foto_perfil);
    });
  }

  activarCampos() {
    this.camposActivos = !this.camposActivos;
    if (!this.camposActivos) {
    }
  }

  guardarDatos() {
    const datosUsuario = {
      id: this.usuario?.id,
      nombre: (document.querySelector('ion-input[label="Nombre"]') as HTMLInputElement).value,
      apellido: (document.querySelector('ion-input[label="Apellidos"]') as HTMLInputElement).value,
      email: (document.querySelector('ion-input[label="Email"]') as HTMLInputElement).value,
      telefono: (document.querySelector('ion-input[label="Teléfono"]') as HTMLInputElement).value,
      direccion: (document.querySelector('ion-input[label="Dirección"]') as HTMLInputElement).value,
      password: this.usuario?.password,
      url_foto_perfil: this.usuario?.url_foto_perfil,
      tipo_usuario: this.usuario?.tipo_usuario,
      fecha_registro: this.usuario?.fecha_registro,
      cantidad_rescates: this.usuario?.cantidad_rescates
    };

    this.http.put('http://localhost:9000/api/usuarios', JSON.stringify(datosUsuario), {
      headers: { 'Content-Type': 'application/json' }
    }).subscribe(response => {
      this.camposActivos = false;
    }, error => {
      console.error('Error al guardar los datos:', error);
    });
  }

  cargarImagenPerfil(nombreImagen: string) {
    this.usuarioService.getImagen(nombreImagen).subscribe((imagen: Blob) => {
      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.imagenPerfil = e.target.result;
      };
      reader.readAsDataURL(imagen);
    });
  }
}
