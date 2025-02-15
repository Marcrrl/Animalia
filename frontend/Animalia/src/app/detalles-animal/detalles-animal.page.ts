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
  id: number | null = null; // Aquí se guardará el id del animal
  animal: any; // Aquí se guardarán los datos del animal
  public imagen: string | null = null; // Aquí se guardará la URL de la imagen
  constructor(
    private route: ActivatedRoute,
    private animalesService: AnimalesService
  ) {}

  ngOnInit() {
    // Capturar el id_animal desde la ruta
    this.id = Number(this.route.snapshot.paramMap.get('id'));

    // Buscar los datos del animal con el id capturado
    this.animalesService.getById(this.id).subscribe(
      (data) => {
        this.animal = data;
        this.imagen = this.animalesService.obtenerImagenUrl(this.animal.foto)
      },
      (error) => {
        console.error('Error fetching animal details:', error);
      }
    );
  }
}
