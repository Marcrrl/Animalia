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

  constructor(private http: HttpClient) { }

  ngOnInit() {
    this.cargarTodosRescates();
    this.cargarRescatesAsignados();
  }

  cargarTodosRescates() {
    this.http.get('http://localhost:9000/api/rescates/detalle').subscribe((data: any) => {
      this.todosRescates = data;
    });
  }

  cargarRescatesAsignados() {
    const usuarioId = sessionStorage.getItem('id');
    this.http.get(`http://localhost:9000/api/usuarios/${usuarioId}/empresa`).subscribe((empresa: any) => {
      const empresaId = empresa.id;
      this.rescatesAsignados = this.todosRescates.filter(rescate => rescate.empresaId === empresaId);
    });
  }

  mostrarTab(tab: string) {
    this.tabActual = tab;
  }

  tieneEmpresaAsignada(rescate: any): string {
    return rescate.nombreEmpresa && rescate.nombreEmpresa.trim() !== '' ? rescate.nombreEmpresa : '(Sin asignar)';
  }
}
