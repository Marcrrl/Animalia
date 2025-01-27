import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-animales',
  templateUrl: './animales.page.html',
  styleUrls: ['./animales.page.scss'],
  standalone: false,
})
export class AnimalesPage implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  public animales = [
    'Erizo',
    'Conejo',
    'Perro',
    'Gato',
    'Pez',
    'Pájaro',
    'Tortuga',
    'Cerdo',
    'Vaca',
    'Cabra',
    'Oveja',
    'Caballo',
    'Burro',
    'Mono',
    'Elefante',
    'Jirafa',
    'León',
    'Tigre'
  ];
  public results = [...this.animales];
  public showList = false;

  handleInput(event: Event) {
    const target = event.target as HTMLIonSearchbarElement;
    const query = target.value?.toLowerCase() || '';
    this.results = this.animales.filter((d) => d.toLowerCase().includes(query));
  }

  handleItemClick(result: string) {
    console.log('Item clicked:', result);
  }

  handleSearchbarClick() {
    this.showList = true;
  }

}
