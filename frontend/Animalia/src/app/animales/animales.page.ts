import { Component, OnInit } from '@angular/core';
import { AnimalesService } from '../services/animales.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-animales',
  templateUrl: './animales.page.html',
  styleUrls: ['./animales.page.scss'],
  standalone: false,
})
export class AnimalesPage implements OnInit {
  public animales: any[] = [];
  public results: any[] = [];
  public showList = false;
  public selectedFamilia: string | null = null;

  constructor(
    private animalesService: AnimalesService,
    private router: Router
  ) {}

  ngOnInit() {
    this.animalesService.getAnimales().subscribe(
      (data) => {
        this.animales = data;
        this.results = [...this.animales];
      },
      (error) => {
        console.error('Error fetching animales:', error);
      }
    );
  }

  handleInput(event: Event) {
    const target = event.target as HTMLIonSearchbarElement;
    const query = target.value?.toLowerCase() || '';
    this.results = this.animales.filter((d: any) =>
      d.nombre_comun.toLowerCase().includes(query)
    );
  }

  handleSearchbarClick() {
    const searchbar = document.querySelector('ion-searchbar');
    const query = searchbar?.value?.trim() || '';

    if (query === '') {
      this.showAllAnimals();
    } else {
      this.searchAnimals(query);
    }
  }

  showAllAnimals() {
    // Lógica para mostrar toda la lista de animales
    this.ngOnInit();
  }

  haciaDatosAnimal(id_animal: number) {
    this.router.navigate(['/detalles-animal', id_animal]);

    console.log('ID del animal:', id_animal);
  }

  haciaMapa() {

    this.router.navigate(['/mapa']);
  }

  searchAnimals(query: string) {
    // Lógica para buscar animales según el query
    this.results = this.animales.filter((d: any) =>
      d.nombre_comun.toLowerCase().includes(query)
    );
  }

  cambioFamilia(familia: string) {
    /*this.results = this.animales.filter((d: any) => d.familia === familia);*/
    if (this.selectedFamilia === familia) {
      this.selectedFamilia = null;
      this.showAllAnimals();
    } else {
      this.selectedFamilia = familia;
      this.results = this.animales.filter((d: any) => d.familia === familia);
    }
  }

  isFamiliaSelected(familia: string): boolean {
    return this.selectedFamilia === familia;
  }
}
