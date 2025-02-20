import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AnimalesService } from '../services/animales.service';

@Component({
  selector: 'app-detalles-animal',
  templateUrl: './detalles-animal.page.html',
  styleUrls: ['./detalles-animal.page.scss'],
  standalone: false,
})
export class DetallesAnimalPage implements OnInit {
  id: number | null = null;
  animal: any;
  public imagen: string | null = null;
  constructor(
    private route: ActivatedRoute,
    private animalesService: AnimalesService
  ) {}

  ngOnInit() {
    // Capturar el id_animal desde la ruta
    this.id = Number(this.route.snapshot.paramMap.get('id'));

    this.animalesService.getById(this.id).subscribe(
      (data) => {
        this.animal = {
          ...data,
          estado_conservacion: data.estado_conservacion.replace(/_/g, ' ')
        };

        this.imagen = this.animalesService.obtenerImagenUrl(this.animal.foto);
      },
      (error) => {
        console.error('Error fetching animal details:', error);
      }
    );
  }
}
