import { Component, OnInit } from '@angular/core';
import * as L from 'leaflet';
import { Router } from '@angular/router';
import { EmpresasService } from '../services/empresas.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-mapa',
  templateUrl: './mapa.page.html',
  styleUrls: ['./mapa.page.scss'],
  standalone: false,
})
export class MapaPage {
  private map!: L.Map;
  private mapContainerId = 'map'; // ID del contenedor del mapa
  empresas: any[] = [];
  private markers: L.Marker[] = []; // Lista de marcadores para limpiar en cada actualización
  public mapInitialized: boolean = false; // Control para saber si el mapa ya ha sido inicializado

  constructor(private router: Router, private empresaService: EmpresasService, private http: HttpClient) {}
 
 ionViewWillEnter() {
    console.log('Cargando datos antes de inicializar el mapa...');
    this.cargarEmpresas();
  }

  async cargarEmpresas() {
    try {
      const data = await this.empresaService.getEmpresas().toPromise();
      this.empresas = await Promise.all(data.map(async (empresa: any) => {
        try {
          const coords = await this.geocodificarDireccion(empresa.direccion);
          return { ...empresa, latitud: coords.lat, longitud: coords.lon };
        } catch (error) {
          console.error(`Error al geocodificar ${empresa.nombre}:`, error);
          return null; // Si falla la geocodificación, no se agrega
        }
      }));
      
      this.empresas = this.empresas.filter(e => e !== null);
      this.ionViewDidEnter();
    } catch (error) {
      console.error('Error al obtener empresas:', error);
    }
  }

  geocodificarDireccion(direccion: string): Promise<{ lat: number, lon: number }> {
    const url = `https://nominatim.openstreetmap.org/search?format=json&q=${encodeURIComponent(direccion)}`;
    
    return this.http.get<any[]>(url).toPromise().then(response => {
      if (response && response.length > 0) {
        return { lat: parseFloat(response[0].lat), lon: parseFloat(response[0].lon) };
      } else {
        throw new Error('No se encontraron coordenadas');
      }
    });
  }

  
  ionViewDidEnter() {

    if (this.empresas.length === 0){
      console.log("no hay empresas");
    return;
    }
      
      
    if (!this.mapInitialized) {
      console.log("Creando nuevo mapa...");
      this.map = L.map(this.mapContainerId).setView([this.empresas[0].latitud, this.empresas[0].longitud], 13);

      L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
      }).addTo(this.map);

      this.mapInitialized = true;
    } else {
      console.log("Mapa ya inicializado, actualizando marcadores...");
      //this.map.setView([this.empresas[0].latitud, this.empresas[0].longitud], 13);
    }

    // Limpiar marcadores previos antes de agregar nuevos
    this.markers.forEach(marker => marker.remove());
    this.markers = [];

    const customIcon = L.icon({
      iconUrl: 'assets/icon/empresaMap.png', // Ruta de la imagen dentro de assets
      iconSize: [60, 60], // Tamaño del icono
      iconAnchor: [16, 32], // Punto del icono que se ubicará en la coordenada

    });
    // Agregar marcadores
    this.empresas.forEach(empresa => {

      const marker=L.marker([empresa.latitud, empresa.longitud],{icon:customIcon})
        .addTo(this.map)
        // .bindPopup(`<b>${empresa.nombre}</b><br>${empresa.direccion}`)
        .bindTooltip(`<b>${empresa.nombre}</b><br>${empresa.direccion}`, {
          permanent: false,
          direction: 'top',
          offset: [ 13.5,-30] // Centrado y arriba del icono
        });
        marker.on('click', () => {
          this.router.navigate(['/detalles-animal', 1]); // Cambia la ruta según tu necesidad
        });
      });

       // Asegurar que el mapa se redimensione bien
    setTimeout(() => {
      this.map?.invalidateSize();
    }, 300);

/*
    this.map = L.map('map').setView([38.362356, -0.491125], 15);

    L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 19,
      attribution:
        '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
    }).addTo(this.map);



    // Define el icono con la imagen personalizada
    const customIcon = L.icon({
      iconUrl: 'assets/icon/empresaMap.png', // Ruta de la imagen dentro de assets
      iconSize: [40, 40], // Tamaño del icono
      iconAnchor: [16, 32], // Punto del icono que se ubicará en la coordenada

    });

    const marker = L.marker([38.362356, -0.491125],{icon:customIcon}).addTo(this.map);

    // Tooltip que aparece al hacer hover
    marker
      .bindTooltip('Ver detalles del animal', {
        permanent: false,
        direction: 'top',
        offset: [ -2,-35] // Centrado y arriba del icono
      })
      .openTooltip();


 //Marcador de animal
// Define el icono con la imagen personalizada
const customIcon = L.icon({
  iconUrl: 'assets/icon/animalMap.png', // Ruta de la imagen dentro de assets
  iconSize: [40, 40], // Tamaño del icono
  iconAnchor: [16, 32], // Punto del icono que se ubicará en la coordenada

});

const marker = L.marker([38.362356, -0.491125],{icon:customIcon}).addTo(this.map);

// Tooltip que aparece al hacer hover
marker
  .bindTooltip('Ver detalles del animal', {
    permanent: false,
    direction: 'top',
    offset: [ 5,-35] // Centrado y arriba del icono
  })
  .openTooltip();

    marker.on('click', () => {
      this.router.navigate(['/detalles-animal', 1]); // Cambia la ruta según tu necesidad
    });
*/
    // Mueve los controles de zoom a la parte superior izquierda
    this.map.zoomControl.setPosition('topleft');
  }
}
