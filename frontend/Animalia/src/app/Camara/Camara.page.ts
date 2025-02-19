import { RescatesService } from './../services/rescates.service';
import { UsuarioService } from './../services/usuario.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Camera, CameraResultType, CameraSource } from '@capacitor/camera';
import { Geolocation } from '@capacitor/geolocation';
import { AnimalesService } from '../services/animales.service';
import { FotosService } from '../services/fotos.service';
import { set } from 'lodash';

@Component({
  selector: 'app-Camara',
  templateUrl: 'Camara.page.html',
  styleUrls: ['Camara.page.scss'],
  standalone: false,
})
export class CamaraPage implements OnInit {
  //variablkes nuevas
  rescates: any[] = [];
  rescatesFiltrados: any[] = [];
  fotos: any[] = [];
  estadoAnimal: string = '';
  rescateId: any;
  nombreFoto: string = '';
  foto: string = ''; // Para almacenar la ruta de la foto
  file: File | null = null;
  public animales: any[] = [];
  results: any[] = [];
  animalSeleccionadoId: any = null;
  descripcion: string = '';
  ubicacion: string = '';
  usuario: any;
  usuarioOriginal: any;
  estadoOpciones: string[] = [
    'LEVE',
    'MODERADO',
    'GRAVE',
    'FALLECIDO',
    'DESCONOCIDO',
    'SANO',
  ];
  anteriorAnimalSeleccionadoId: any;

  constructor(
    private http: HttpClient,
    private animalesService: AnimalesService,
    private fotosService: FotosService,
    private usuarioService: UsuarioService,
    private rescatesService: RescatesService
  ) {}

  ngOnInit() {
    this.nombreFoto = '';
    this.descripcion = '';
    this.ubicacion = '';
    this.animalSeleccionadoId = 1;
    this.animalesService.getTotalAnimales().subscribe((animales) => {
      this.animales = animales; // Guardar todos los animales en la lista principal
      this.results = [...this.animales];
    });

    const userId = sessionStorage.getItem('id');
    if (userId) {
      this.usuarioService.getUsuarioById(userId).subscribe((data: any) => {
        this.usuario = data;
        this.usuarioOriginal = { ...this.usuario };
      });
    } else {
      console.error('No se encontró el id del usuario');
    }
    // Obtener todos los rescates una sola vez
    this.rescatesService.getTodosRescates().subscribe((data) => {
      this.rescates = data;
    });
    this.animalesService.getTotalAnimales().subscribe((animales) => {
      this.animales = animales; // Guardar todos los animales en la lista principal
    });

    // if (this.rescateId) {
    //   this.rescatesService.getById(this.rescateId).subscribe((data: any) => {
    //     this.rescate = data;
    //     this.rescateOriginal = { ...this.rescate };
    //   });
    // } else {
    //   console.error('No se encontró el id del rescate');
    // }

    this.obtenerUbicacionPoint();
  }

  onFotoClick(rescateId: number) {
    this.rescateId = rescateId;
    console.log('Rescate seleccionado:', this.rescateId);
  }

  getImagen(foto: any): string {
    console.log('Foto:', foto.url_foto);
    return this.fotosService.obtenerImagenUrl(foto.url_foto);
  }

  getRescatePorIdFoto(idFoto: number) {
    this.fotosService.obtenerRescatePorIdFoto(idFoto).subscribe({
      next: (id) => {
        this.rescateId = id;
        console.log('ID del rescate:', id);
      },
      error: (err) => console.error('Error obteniendo el ID del rescate', err),
    });
  }

  onAnimalChange() {
    if (this.animalSeleccionadoId !== this.anteriorAnimalSeleccionadoId) {
      this.anteriorAnimalSeleccionadoId = this.animalSeleccionadoId;
      console.log('Animal seleccionado:', this.animalSeleccionadoId);

      this.rescatesFiltrados = this.rescates.filter(
        (rescate) =>
          rescate.animal.id === this.animalSeleccionadoId &&
          rescate.estado_rescate === 'NO_ASIGNADO'
      );

      console.log('Rescates filtrados:', this.rescatesFiltrados);

      this.fotos = this.rescatesFiltrados.flatMap((rescate) => {
        rescate.fotos;
      });

      console.log('Fotos:', this.fotos);
    }
  }

  filtrarRescates() {
    this.rescatesFiltrados = this.rescates.filter(
      (rescate) =>
        rescate.animal.id === this.animalSeleccionadoId &&
        rescate.estado_rescate === 'NO_ASIGNADO'
    );
    console.log('Rescates filtrados:', this.rescatesFiltrados);

    console.log('Fotos:', this.fotos);
  }

  resetRescateSeleccionadoId() {
    this.rescateId = null;
    console.log('Rescate a null:', this.rescateId);
  }

  editarRescate() {
    const token = sessionStorage.getItem('token'); // Obtener el token almacenado
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`, // Enviar el token en el header
    });
    this.rescatesService
      .editarRescate(this.rescateId, this.ubicacion, headers)
      .subscribe({
        next: (response) => {
          console.log('Rescate editado', response);
        },
        error: (error) => {
          console.error('Error al editar el rescate', error);
        },
        complete: () => {
          console.log('Proceso de editar rescate completado.');
        },
      });
  }
  subirRescate() {
    /*PUT*/
    if (this.rescateId && this.foto) {
      console.log('Editando Rescate');
      this.subirImagen();
      this.editarRescate();
    } else {
      console.log('Añadiendo Rescate');
      /*POST*/
      if (
        this.animalSeleccionadoId &&
        this.estadoAnimal &&
        this.ubicacion &&
        this.usuarioOriginal &&
        this.foto
      ) {
        const token = sessionStorage.getItem('token'); // Obtener el token almacenado
        const headers = new HttpHeaders({
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`, // Enviar el token en el header
        });
        // Crear un nuevo rescate
        const nuevoRescate = {
          animalId: this.animalSeleccionadoId, // Asegúrate de que el animal esté asignado correctamente
          //estado_rescate: 'EN PROCESO',     // El estado inicial puede ser "EN PROCESO" o lo que prefieras
          estadoAnimal: this.estadoAnimal, // Asigna el estado adecuado para el animal
          //fecha_rescate: null,       // Usa la fecha actual
          //fotos: [],                       // Agrega las fotos si es necesario
          ubicacion: this.ubicacion, // Ubicación por defecto
          usuarioId: this.usuarioOriginal.id, // Información de usuario
          //empresa: null // Información de la empresa
        };

        this.rescatesService.añadirRescate(nuevoRescate, headers).subscribe({
          next: (response) => {
            this.rescateId = (response.body as any).id; // Aquí accedes al ID dentro del objeto completo
            console.log('Rescate agregado con ID:', this.rescateId);

            setTimeout(() => {
              this.subirImagen();
            }, 2000);
          },
          error: (error) => {
            console.error('Error al agregar el rescate', error);
          },
          complete: () => {
            console.log('Proceso de añadir rescate completado.');
          },
        });
      } else {
        console.log('Faltan datos');
      }
    }
  }

  async obtenerUbicacionPoint() {
    try {
      let latitude: number;
      let longitude: number;

      if ('geolocation' in navigator) {
        // Usar navigator.geolocation con Promises
        const position = await new Promise<GeolocationPosition>(
          (resolve, reject) => {
            navigator.geolocation.getCurrentPosition(resolve, reject);
          }
        );

        latitude = position.coords.latitude;
        longitude = position.coords.longitude;
      } else {
        // Usar Capacitor Geolocation
        const coordinates = await Geolocation.getCurrentPosition();
        latitude = coordinates.coords.latitude;
        longitude = coordinates.coords.longitude;
      }

      // Actualizar el formulario con el nuevo punto
      // this.fotoForm.patchValue({
      //   ubicacion: point,
      // });
      this.ubicacion = latitude + '|' + longitude;
    } catch (error) {
      console.error('Error obteniendo ubicación', error);
    }
  }

  // Método para tomar una foto
  async takePicture() {
    try {
      const photo = await Camera.getPhoto({
        resultType: CameraResultType.Uri, // Obtener la foto como URI
        source: CameraSource.Camera, // Usar la cámara
        quality: 100, // Calidad de la foto
      });

      this.foto = photo.webPath || ' '; // Usamos el URI para mostrar la foto

      if (photo.webPath) {
        this.file = await this.convertToFile(photo.webPath);
        console.log(this.file);
      }
    } catch (error) {
      console.log('Error al tomar la foto', error);
    }
  }

  subirImagen() {
    console.log(
      'Rescate:' + this.rescateId,
      'usuario:' + parseInt(this.usuarioOriginal.id),
      'Foto:' + this.nombreFoto,
      this.descripcion,
      this.ubicacion
    );
    if (
      this.rescateId &&
      this.usuarioOriginal &&
      this.nombreFoto &&
      this.descripcion &&
      this.ubicacion
    ) {
      const token = sessionStorage.getItem('token');
      console.log('Token:', token);
      const headers = new HttpHeaders({
        Authorization: `Bearer ${token}`,
      });
      const formData = new FormData();

      if (this.file) {
        console.log('Subiendo archivo:', this.file);
        formData.append('file', this.file);
      }

      this.http
        .post('http://localhost:9000/api/subir-imagen', formData, {
          headers: headers,
          observe: 'response',
        })
        .subscribe(
          (response) => {
            if (response.status === 200 && response.body) {
              console.log('Foto guardada con éxito', response);
            } else {
              console.error('Error al subir la imagen:', response.statusText);
            }
          },
          (error) => {
            console.error('Error al subir la imagen:', error);
          }
        );
      setTimeout(() => {
        this.enviarAnimalRescate();
      }, 1000);
    } else {
      console.log('Faltan datos');
    }
  }

  async convertToFile(webPath: string): Promise<File> {
    const response = await fetch(webPath);
    const blob = await response.blob();
    this.nombreFoto = `photo_${Date.now()}.jpg`;
    console.log(this.nombreFoto);
    const file = new File([blob], this.nombreFoto, { type: blob.type });
    return file;
  }

  enviarAnimalRescate() {
    console.log(
      'Rescate:' + this.rescateId,
      'usuario:' + parseInt(this.usuarioOriginal.id),
      this.nombreFoto,
      this.descripcion,
      this.ubicacion
    );
    const token = sessionStorage.getItem('token'); // Obtener el token almacenado
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`, // Enviar el token en el header
    });
    this.fotosService
      .añadirFoto(
        this.rescateId,
        this.usuarioOriginal.id,
        this.nombreFoto,
        this.descripcion,
        this.ubicacion,
        headers
      )
      .subscribe({
        next: (response) => {
          console.log('Foto agregada', response);
        },
        error: (error) => {
          console.error('Error al agregar la foto', error);
        },
        complete: () => {
          console.log('Proceso de añadir foto completado.');
        },
      });
  }

  //No creo que use este metodo en el lo quitaré
  // submitForm() {
  //   const fechaActual = new Date();
  //   const fechaLegible = fechaActual.toLocaleDateString('es-ES');
  //   console.log(fechaLegible);

  //   if (this.fotoForm.valid && /* this.file &&*/ this.emailUsuario) {
  //     const formData = new FormData();
  //     //formData.append('archivo', this.file, 'foto.jpg'); // Archivo
  //     formData.append('ubicacion', this.fotoForm.value.ubicacion);
  //     formData.append('descripcion', this.fotoForm.value.descripcion);
  //     formData.append('fecha_captura', this.fotoForm.value.fecha_captura);
  //     formData.append('email_usuario', this.emailUsuario);
  //     this.http.post('https://tuapi.com/fotos', formData).subscribe();
  //   }
  // }
}
