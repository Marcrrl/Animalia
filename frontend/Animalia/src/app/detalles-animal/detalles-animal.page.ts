import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AnimalesService } from '../services/animales.service';
import { FotosService } from '../services/fotos.service';
import { RescatesService } from '../services/rescates.service';

@Component({
  selector: 'app-detalles-animal',
  templateUrl: './detalles-animal.page.html',
  styleUrls: ['./detalles-animal.page.scss'],
  standalone: false,
})
export class DetallesAnimalPage implements OnInit {
  id: number | null = null;
  animal: any;
  tipo: any;
  rescateId: any;
rescate: any;
  public imagen: string | null = null;
  constructor(
    private route: ActivatedRoute,
    private animalesService: AnimalesService,
    private fotosService: FotosService,
private rescateService: RescatesService
  ) {}

  ngOnInit() {
    // Capturar el id_animal desde la ruta
    this.id = Number(this.route.snapshot.paramMap.get('id'));
    this.tipo = this.route.snapshot.paramMap.get('tipo') || '';
    if (this.tipo == 'animal') {
      this.animalesService.getById(this.id).subscribe(
        (data) => {
          this.animal = {
            ...data,
            estado_conservacion: data.estado_conservacion.replace(/_/g, ' '),
          };

          this.imagen = this.animalesService.obtenerImagenUrl(this.animal.foto);
        },
        (error) => {
          console.error('Error fetching animal details:', error);
        }
      );
    } else if (this.tipo == 'foto') {


this.fotosService.obtenerRescatePorIdFoto(this.id).subscribe(
        (data) => {
          this.rescateId = data;
          this.rescateService.getById(this.rescateId).subscribe(
            (data) => {
              this.rescate = data;
            },
            (error) => {
              console.error('Error fetching rescue details:', error);
            }
          );
        },
        (error) => {
          console.error('Error fetching rescue id:', error);
        });
    }
  }
}
