import { Component, OnInit } from '@angular/core';
import { AnimalesService } from '../services/animales.service';

@Component({
  selector: 'app-animales',
  templateUrl: './animales.page.html',
  styleUrls: ['./animales.page.scss'],
  standalone: false
})
export class AnimalesPage implements OnInit {
  public animales = [];
  public results = [];
  public showList = false;

  constructor(private animalesService: AnimalesService) { }

  ngOnInit() {
    this.animalesService.getAnimales().subscribe(
      data => {
        this.animales = data;
        this.results = [...this.animales];
      },
      error => {
        console.error('Error fetching animales:', error);
      }
    );
  }

  handleInput(event: Event) {
    const target = event.target as HTMLIonSearchbarElement;
    const query = target.value?.toLowerCase() || '';
    this.results = this.animales.filter((d: string) => d.toLowerCase().includes(query));
  }

  handleItemClick(result: string) {
    console.log('Item clicked:', result);
  }

  handleSearchbarClick() {
    this.showList = true;
  }
  
}