import { Component, OnInit, Inject } from '@angular/core';
import { UsuarioService } from '../Servicios/usuario.service';

@Component({
  selector: 'app-perfil',
  templateUrl: 'perfil.page.html',
  styleUrls: ['perfil.page.scss'],
  standalone: false,
})
export class PerfilPage implements OnInit {
  usuario: any;

  constructor(@Inject(UsuarioService) private usuarioService: UsuarioService) {}

  ngOnInit() {
    this.usuarioService.getUsuarios().subscribe((data: any) => {
      this.usuario = data[0];
    });
  }

}
