import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-rescates',
  templateUrl: './rescates.page.html',
  styleUrls: ['./rescates.page.scss'],
  standalone: false,
})
export class RescatesPage implements OnInit {
  todosRescates: any[] = [];
  rescatesAsignados: any[] = [];
  tabActual: string = 'todosRescates';
  empresaId: number = 0;

  constructor(private http: HttpClient) { }

  ngOnInit() {
    this.cargarTodosRescates();
  }

  cargarTodosRescates() {
    this.http.get('http://localhost:9000/api/rescates/detalle').subscribe((data: any) => {
      this.todosRescates = data;
      this.todosRescates.forEach(rescate => {
      });
      this.cargarRescatesAsignados();
    });
  }

  cargarRescatesAsignados() {
    const usuarioId = sessionStorage.getItem('id');
    this.http.get(`http://localhost:9000/api/usuarios/${usuarioId}/empresa`, { responseType: 'text' }).subscribe((response: string) => {
      const empresaId = Number(response);
      this.http.get(`http://localhost:9000/api/rescates/empresa/${empresaId}/rescates`).subscribe((data: any) => {
        this.rescatesAsignados = data;
      });
    });
  }

  mostrarTab(tab: string) {
    this.tabActual = tab;
  }

  tieneEmpresaAsignada(rescate: any): string {
    return rescate.nombreEmpresa && rescate.nombreEmpresa.trim() !== '' ? rescate.nombreEmpresa : '(Sin asignar)';
  }

  asignarEmpresa(rescateId: number, empresaId: number) {
    const usuarioId = sessionStorage.getItem('id');
    if (!usuarioId) {
      console.error('Usuario no identificado');
      return;
    }
    const rescate = this.todosRescates.find(r => r.id === rescateId);

    this.http.get<any[]>(`http://localhost:9000/api/empresas/todos`).subscribe((empresas) => {
      this.http.get(`http://localhost:9000/api/usuarios/${usuarioId}/empresa`, { responseType: 'text' }).subscribe((response: string) => {
        const empresaIdUsuario = Number(response);
        const empresa = empresas.find(e => e.id === empresaIdUsuario);
        if (empresa) {
          const body = {
            id: rescateId,
            nombreEmpresa: empresa.nombre,
            nombreUsuario: rescate.nombreUsuario,
            nombreAnimal: rescate.nombreAnimal,
            ubicacion: rescate.ubicacion,
            estadoRescate: 'ASIGNADO',
            estadoAnimal: rescate.estadoAnimal,
            fechaRescate: rescate.fechaRescate
          };

          const url = `http://localhost:9000/api/rescates/${rescateId}?empresaId=${empresa.id}&usuarioId=${usuarioId}`;

          this.http.put(url, body).subscribe(() => {
            location.reload();
          });
        }
      });
    });
  }
}
