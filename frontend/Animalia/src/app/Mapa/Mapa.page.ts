import { Component, OnInit } from '@angular/core';
import * as L from 'leaflet';
import { Router } from '@angular/router';

@Component({
  selector: 'app-mapa',
  templateUrl: './mapa.page.html',
  styleUrls: ['./mapa.page.scss'],
  standalone: false,
})
export class MapaPage {
  private map!: L.Map;

  constructor(private router: Router) {}

  ionViewDidEnter() {
    this.map = L.map('map').setView([38.362356, -0.491125], 15);

    L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 19,
      attribution:
        '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
    }).addTo(this.map);

    /*
    // Marcador de empresa
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

*/
    /* Marcador de animal
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
