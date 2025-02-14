import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-register',
  templateUrl: './register.page.html',
  styleUrls: ['./register.page.scss'],
  standalone: false,
})
export class RegisterPage implements OnInit {
  tipo: string = '';

  constructor() { }

  ngOnInit() {
  }

  showContent(tipo: string) {
    this.tipo = tipo;
  }
}
