import { Component, OnInit, OnDestroy, Renderer2 } from '@angular/core';
import { AnimalesService } from '../services/animales.service';
import { Router } from '@angular/router';
import { MenuController } from '@ionic/angular';

@Component({
  selector: 'app-animales',
  templateUrl: './animales.page.html',
  styleUrls: ['./animales.page.scss'],
  standalone: false,
})
export class AnimalesPage implements OnInit, OnDestroy {
  public animales: any[] = [];
  public results: any[] = [];
  public showList = false;
  public selectedFamilia: string | null = null;
  menuType: string = 'overlay';
  public isMenuOpen: boolean = false;
  private clickListener!: () => void;
  public imagenes: string[] = [];

  constructor(
    private animalesService: AnimalesService,
    private router: Router,
    private menuCtrl: MenuController,
    private renderer: Renderer2
  ) {}

  ngOnInit() {
    this.animalesService.getAnimales().subscribe(
      (data) => {
        this.animales = data;
        this.results = [...this.animales];
        this.imagenes = this.animales.map(animal => this.animalesService.obtenerImagenUrl(animal.foto)); // Añadir imágenes al array
      },
      (error) => {
        console.error('Error fetching animales:', error);
      }
    );

    this.clickListener = this.renderer.listen('document', 'click', (event) => {
      if (this.isMenuOpen && !event.target.closest('ion-menu')) {
        this.closeEndMenu();
      }
    });
  }

  ngOnDestroy() {
    if (this.clickListener) {
      this.clickListener();
    }
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

  haciaMapa() {
    this.router.navigate(['/mapa']);
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
    this.toggleStickySearchbar(false);
    this.menuCtrl.enable(true, 'end');
    this.menuCtrl.open('end');
    this.isMenuOpen = true;
  }

  closeEndMenu() {
    this.menuCtrl.close('end');
    this.toggleStickySearchbar(true);
    this.isMenuOpen = false;
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
}
