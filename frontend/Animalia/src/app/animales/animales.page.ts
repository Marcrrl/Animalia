import { Component, OnInit } from '@angular/core';
import { AnimalesService } from '../services/animales.service';
import { Router } from '@angular/router';
import { MenuController } from '@ionic/angular';

@Component({
  selector: 'app-animales',
  templateUrl: './animales.page.html',
  styleUrls: ['./animales.page.scss'],
  standalone: false,
})
export class AnimalesPage implements OnInit {
isCaracteristicasSelected(): any {
throw new Error('Method not implemented.');
}
  public animales: any[] = [];
  public results: any[] = [];
  public showList = false;
  public selectedFamilia: string | null = null;
  menuType: string = 'overlay';
  public isMenuOpen: boolean = false;

  constructor(
    private animalesService: AnimalesService,
    private router: Router,
    private menuCtrl: MenuController
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
