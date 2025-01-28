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

  constructor(@Inject(UsuarioService) private usuarioService: UsuarioService, private http: HttpClient) {}

  ngOnInit() {
    this.usuarioService.getUsuarios().subscribe((data: any) => {
      this.usuario = data[0];
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
      nombre: this.usuario?.nombre,
      apellido: this.usuario?.apellido,
      email: this.usuario?.email,
      password: this.usuario?.password,
      telefono: this.usuario?.telefono,
      direccion: this.usuario?.direccion,
      url_foto_perfil: this.usuario?.url_foto_perfil,
      tipo_usuario: this.usuario?.tipo_usuario,
      fecha_registro: this.usuario?.fecha_registro,
      cantidad_rescates: this.usuario?.cantidad_rescates
    };

    this.http.post('http://localhost:9000/api/usuarios', JSON.stringify(datosUsuario), {
      headers: { 'Content-Type': 'application/json' }
    }).subscribe(response => {
      this.camposActivos = false;
    }, error => {
      console.error('Error al guardar los datos:', error);
    });
  }
}
