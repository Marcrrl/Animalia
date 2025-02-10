import { Router } from '@angular/router';
import { Component } from '@angular/core';

@Component({
  selector: 'app-tabs',
  templateUrl: 'tabs.page.html',
  styleUrls: ['tabs.page.scss'],
  standalone: false,
})
export class TabsPage {
  currentRoute = '/Animales';
  currentLabel = 'Empresas';
  currentIcon = 'business';  // El ícono inicial es "paw"

  constructor(private router: Router) {}
  toggleTab() {
    if (this.currentRoute === '/Animales') {
      this.currentRoute = '/Empresas';
      this.currentLabel = 'Animales';
      this.currentIcon = 'paw';  // Cambia el ícono a "business" para Empresas
    } else {
      this.currentRoute = '/Animales';
      this.currentLabel = 'Empresas';
      this.currentIcon = 'business';  // Vuelve al ícono "paw" para Animales
    }
    // Navega a la nueva ruta
    this.router.navigateByUrl(this.currentRoute);
  }
}
