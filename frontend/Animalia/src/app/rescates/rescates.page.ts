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
  idEmpresaUsuario: number = 1;

  constructor(private http: HttpClient) { }

  ngOnInit() {
    this.obtenerTodosRescates();
    this.rescatesAsignados = this.obtenerRescatesAsignados();
  }

  obtenerTodosRescates() {
    this.http.get<any[]>('http://localhost:9000/api/rescates/detalle')
      .subscribe(rescates => {
        this.todosRescates = rescates;
      }, error => {
        console.error('Error al obtener los rescates:', error);
      });
  }

  obtenerRescatesAsignados() {
    return this.todosRescates.filter(rescate => rescate.idEmpresa === this.idEmpresaUsuario);
  }

  asignarRescate(rescate: any) {
    this.rescatesAsignados.push(rescate);
    this.todosRescates = this.todosRescates.filter(r => r !== rescate);
  }

  mostrarTab(tab: string) {
    this.tabActual = tab;
  }

  tieneEmpresaAsignada(rescate: any): boolean {
    return rescate.nombreEmpresa && rescate.nombreEmpresa.trim() !== '';
  }
}
