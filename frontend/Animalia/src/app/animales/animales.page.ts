import { Component, OnInit, OnDestroy, Renderer2 } from '@angular/core';
import { AnimalesService } from '../services/animales.service';
import { Router } from '@angular/router';
import {
  MenuController,
  IonButtons,
  IonContent,
  IonHeader,
  IonMenu,
  IonMenuButton,
  IonTitle,
  IonToolbar,
} from '@ionic/angular';

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
  menuType: string = 'overlay';
  public isMenuOpen: boolean = false;

  totalAnimales: number = 0;
  totalPages: number = 0;
  currentPage: number = 0;  // Página para el backend
  currentPagehtml: number = 1; // Página que se mostrará en el HTML (empieza desde 1)
  itemsPerPage: number = 5;

  constructor(
    private animalesService: AnimalesService,
    private router: Router,
    private menuCtrl: MenuController,
    private renderer: Renderer2
  ) {}

  ngOnInit() {
    // Obtener el total de animales al iniciar
    this.animalesService.getTotalAnimales().subscribe((animales) => {
      this.totalAnimales = animales.length;
      this.totalPages = Math.ceil(this.totalAnimales / this.itemsPerPage); // Calcular total de páginas
      this.loadAnimales();
    });
  }

  loadAnimales(event?: any) {
    this.animalesService.getAnimales(this.currentPage).subscribe((animales: any[]) => {
      this.animales = animales;

      if (event) {
        event.target.complete(); // Finaliza el infinite scroll si se está usando
      }
    });
  }

  nextPage() {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.currentPagehtml++;  // Aumentamos la página para la vista
      this.loadAnimales();
    }
  }

  prevPage() {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.currentPagehtml--;  // Disminuimos la página para la vista
      this.loadAnimales();
    }
  }

  getImagen(animal: any): string {
    return this.animalesService.obtenerImagenUrl(animal.foto);
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
    this.ngOnInit();
  }

  haciaDatosAnimal(id_animal: number) {
    this.router.navigate(['/detalles-animal', id_animal]);
    console.log('ID del animal:', id_animal);
  }

  haciaMapa(tipo: string) {
    this.router.navigate(['/mapa', tipo]);
  }

  searchAnimals(query: string) {
    this.results = this.animales.filter((d: any) =>
      d.nombre_comun.toLowerCase().includes(query)
    );
  }

  cambioFamilia(familia: string) {
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

  openEndMenu() {
    this.menuCtrl.enable(true, 'end');
    this.menuCtrl.open('end');
    this.toggleStickySearchbar(false);
    this.isMenuOpen = true;
  }

  closeEndMenu() {
    this.menuCtrl.close('end');
    this.toggleStickySearchbar(true);
    this.isMenuOpen = false;
    console.log('Menu cerrado');
  }

  toggleStickySearchbar(isSticky: boolean) {
    const searchbar = document.querySelector('ion-searchbar');
    if (searchbar) {
      if (isSticky) {
        searchbar.classList.add('sticky-top');
      } else {
        searchbar.classList.remove('sticky-top');
      }
    }
  }

  makeCall(number: string) {
    window.open(`tel:${number}`, '_system');
  }
}
