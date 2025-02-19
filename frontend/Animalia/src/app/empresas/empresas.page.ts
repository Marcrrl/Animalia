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
  public filteredEmpresas: any[] = [];
  menuType: string = 'overlay';
  public isMenuOpen: boolean = false;

  totalEmpresas: number = 0;
  totalPages: number = 0;
  currentPage: number = 0; // Página para el backend
  currentPagehtml: number = 1; // Página que se mostrará en el HTML (empieza desde 1)
  itemsPerPage: number = 5;
  paginaActual: any[] = [];  // Página para el backend


  public showList = false;
  public selectedFamilia: string | null = null;


  public selectedTipo: string | null = null;
  constructor(
    private empresasService: EmpresasService,
    private router: Router,
    private menuCtrl: MenuController
  ) { }

  ngOnInit() {

    // Obtener todos las empresas al iniciar
    this.empresasService.getTotalEmpresas().subscribe((empresas) => {
      this.empresas = empresas; // Guardar todos los animales en la lista principal
      this.filteredEmpresas = [...this.empresas]; // Inicialmente, `filteredEmpresas` tiene todos los animales
      this.totalEmpresas = this.filteredEmpresas.length;
      this.totalPages = Math.ceil(this.totalEmpresas / this.itemsPerPage); // Calcular total de páginas

      // Cargar la primera página
      this.currentPage = 0;
      this.currentPagehtml = 1;
      this.actualizarPagina();
    });
  }


  handleInput(event: Event) {
    const target = event.target as HTMLIonSearchbarElement;
    const query = target.value?.toLowerCase().trim() || '';
    this.searchEmpresas(query);
  }

  
  handleSearchbarClick() {
    const searchbar = document.querySelector('ion-searchbar');
    const query = searchbar?.value?.trim().toLowerCase() || '';
  
    this.searchEmpresas(query);
  }

  searchEmpresas(query: string) {
    this.currentPage = 0; // Reiniciar a la primera página (0) en cada búsqueda
    if (query === '') {
      this.filteredEmpresas = [...this.empresas]; // Mostrar todos los animales si el query está vacío
    } else {
      this.filteredEmpresas = this.empresas.filter((d: any) =>
        d.nombre.toLowerCase().includes(query)
      );
    }
    this.actualizarPagina();
  }

  actualizarPagina() {
    const start = this.currentPage * this.itemsPerPage;
    const end = start + this.itemsPerPage;
    this.paginaActual = this.filteredEmpresas.slice(start, end);
  }
  haciaDatosEmpresa(id: number) {
    this.router.navigate(['/detalles-empresa', id]);
    console.log('ID de la empresa:', id);
  }


  isEmpresaSelected(tipo: string): boolean {
    return this.selectedTipo === tipo;
  }

  haciaMapa(tipo: string) {
    this.router.navigate(['/mapa', tipo]);
  }
  openEndMenu() {
    this.menuCtrl.enable(true, 'end-empresas');
    this.menuCtrl.open('end-empresas');
    this.toggleStickySearchbar(false);
    this.isMenuOpen = true;
  }

  closeEndMenu() {
    this.menuCtrl.close('end-empresas');
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

  // cambioTipo(tipo: string) {
  //   if (this.selectedTipo === tipo) {
  //     this.selectedTipo = null;
  //     this.showAllEmpresas();
  //   } else {
  //     this.selectedTipo = tipo;
  //     this.filteredEmpresas = this.empresas.filter((d: any) => d.tipo === tipo);
  //   }
  // }

  cambioTipo(tipo: string) {
    if (this.selectedTipo === tipo) {
      this.selectedTipo = null;
      this.showAllEmpresas(); // Mostrar todas las empresas si no se ha seleccionado tipo
    } else {
      this.selectedTipo = tipo;
      this.filteredEmpresas = this.empresas.filter((d: any) => d.tipo === tipo);
    }
    this.currentPage = 0; // Reiniciar la página a 0 cuando se cambia el tipo
    this.actualizarPagina();
  }

  makeCall(number: string) {
    window.open(`tel:${number}`, '_system');
  }

  loadEmpresas(event?: any) {
    this.empresasService
      .getEmpresas(this.currentPage)
      .subscribe((empresas: any[]) => {
        this.empresas = empresas;

        if (event) {
          event.target.complete(); // Finaliza el infinite scroll si se está usando
        }
      });
  }

  nextPage() {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.currentPagehtml++; // Aumentamos la página para la vista
      this.actualizarPagina();
    }
  }

  prevPage() {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.currentPagehtml--; // Disminuimos la página para la vista
      this.actualizarPagina();
    }
  }
}