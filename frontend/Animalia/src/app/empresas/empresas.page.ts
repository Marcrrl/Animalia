import { Component, OnInit } from '@angular/core';
import { AnimalesService } from '../services/animales.service';
import { Router } from '@angular/router';
import { MenuController } from '@ionic/angular';
import { EmpresasService } from '../services/empresas.service';

@Component({
  selector: 'app-empresas',
  templateUrl: './empresas.page.html',
  styleUrls: ['./empresas.page.scss'],
  standalone: false,
})
export class EmpresasPage implements OnInit {

  public empresas: any[] = [];
  public results: any[] = [];
  menuType: string = 'overlay';
  public isMenuOpen: boolean = false;

  public selectedTipo: string | null = null;
  constructor(
    private empresasService: EmpresasService,
    private router: Router,
    private menuCtrl: MenuController
  ) {}

  ngOnInit() {

    this.empresasService.getEmpresas().subscribe(
      (data) => {
        this.empresas = data;
        this.results = [...this.empresas];
      },
      (error) => {
        console.error('Error fetching empresas:', error);
      }
    );
  }

  handleInput(event: Event) {
    const target = event.target as HTMLIonSearchbarElement;
    const query = target.value?.toLowerCase() || '';
    this.results = this.empresas.filter((d: any) =>
      d.nombre.toLowerCase().includes(query)
    );
  }
  handleSearchbarClick() {
    const searchbar = document.querySelector('ion-searchbar');
    const query = searchbar?.value?.trim() || '';

    if (query === '') {
      this.showAllEmpresas();
    } else {
      this.searchEmpresas(query);
    }
  }
  haciaDatosAnimal(id_animal: number) {
    this.router.navigate(['/detalles-animal', id_animal]);
    console.log('ID del animal:', id_animal);
  }

  searchEmpresas(query: string) {
    this.results = this.empresas.filter((d: any) =>
      d.nombre.toLowerCase().includes(query)
    );
  }

  isEmpresaSelected(tipo: string): boolean {
    return this.selectedTipo === tipo;
  }

  haciaMapa() {
    this.router.navigate(['/mapa']);
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
  showAllEmpresas() {
    this.ngOnInit();
  }


  cambioTipo(tipo: string) {

    if (this.selectedTipo === tipo) {
      this.selectedTipo = null;
      this.showAllEmpresas();
    } else {
      this.selectedTipo = tipo;
      this.results = this.empresas.filter((d: any) => d.tipo === tipo);
    }
  }

}
