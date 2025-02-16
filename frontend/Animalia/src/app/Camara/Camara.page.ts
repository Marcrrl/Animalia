import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Camera, CameraResultType, CameraSource } from '@capacitor/camera';
import { Geolocation } from '@capacitor/geolocation';
import { AnimalesService } from '../services/animales.service';
import { FotosService } from '../services/fotos.service';

@Component({
  selector: 'app-Camara',
  templateUrl: 'Camara.page.html',
  styleUrls: ['Camara.page.scss'],
  standalone: false,
})
export class CamaraPage implements OnInit {
  foto: string = ''; // Para almacenar la ruta de la foto
  fotoForm: FormGroup;
  emailUsuario: string | null = null;
  file: File | null = null;
  nombreFoto: string = '';
  public animales: any[] = [];
  results: any[] = [];
  animalSeleccionado: any = null;

  constructor(
    private fb: FormBuilder, private http: HttpClient, private animalesService: AnimalesService, private fotosService: FotosService
  ) {
    // Inicializar el formulario
    this.fotoForm = this.fb.group({
      url_foto: ['', Validators.required],
      rescateId: ['', Validators.required],
      usuarioId: ['', Validators.required],
      ubicacion: [
        {
          type: 'Point',
          coordinates: [0, 0], // Coordenadas por defecto (longitud, latitud)
        },
        Validators.required
      ],
      descripcion: ['', Validators.required],
      fecha_captura: ['', Validators.required]
    });
  }

  ngOnInit() {
    this.nombreFoto = '';
    this.animalesService.getTotalAnimales().subscribe((animales) => {
      this.animales = animales; // Guardar todos los animales en la lista principal
      this.results = [...this.animales];
    });

    // Obtener el email del usuario desde localStorage
    const storedEmail = localStorage.getItem('email');
    if (storedEmail) {
      this.emailUsuario = storedEmail;
      this.fotoForm.patchValue({ email_usuario: this.emailUsuario });
    }
  }

  async obtenerUbicacionPoint() {
    try {
      let latitude: number;
      let longitude: number;

      if ('geolocation' in navigator) {
        // Usar navigator.geolocation con Promises
        const position = await new Promise<GeolocationPosition>((resolve, reject) => {
          navigator.geolocation.getCurrentPosition(resolve, reject);
        });

        latitude = position.coords.latitude;
        longitude = position.coords.longitude;
      } else {
        // Usar Capacitor Geolocation
        const coordinates = await Geolocation.getCurrentPosition();
        latitude = coordinates.coords.latitude;
        longitude = coordinates.coords.longitude;
      }

      // Crear el Point en formato GeoJSON
      const point = {
        type: 'Point',
        coordinates: [longitude, latitude], // GeoJSON usa [longitud, latitud]
      };

      console.log('Ubicación obtenida:', point);

      // Actualizar el formulario con el nuevo punto
      this.fotoForm.patchValue({
        ubicacion: point,
      });

      return point; // Puedes devolverlo para usarlo en tu API
    } catch (error) {
      console.error('Error obteniendo ubicación', error);
      return null;
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

      this.foto = photo.webPath || " "; // Usamos el URI para mostrar la foto

      if (photo.webPath) {
        this.file = await this.convertToFile(photo.webPath);
        console.log(this.file);
      }
    } catch (error) {
      console.log('Error al tomar la foto', error);
    }
  }

  subirImagen() {
    const token = sessionStorage.getItem('token');
    console.log('Token:', token);
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    const formData = new FormData();

    if (this.file) {
      console.log('Subiendo archivo:', this.file);
      formData.append('file', this.file);
    }

    this.http.post('http://localhost:9000/api/subir-imagen', formData, {
      headers: headers,
      observe: 'response'
    }).subscribe(response => {
      if (response.status === 200 && response.body) {
        console.log('Foto guardada con éxito', response);
      } else {
        console.error('Error al subir la imagen:', response.statusText);
      }
    }, error => {
      console.error('Error al subir la imagen:', error);
    });
  }

  async convertToFile(webPath: string): Promise<File> {
    const response = await fetch(webPath);
    const blob = await response.blob();
    this.nombreFoto = `photo_${Date.now()}.jpg`;
    const file = new File([blob], this.nombreFoto, { type: blob.type });
    return file;
  }

  enviarAnimalRescate() {
    if (this.fotoForm.valid) {
      const formValue = this.fotoForm.value;
      const fechaActual = new Date();
      const fechaLegible = fechaActual.toLocaleDateString('es-ES');

      const foto = {
        url_foto: this.nombreFoto,
        rescate: { id: formValue.rescateId },
        usuarios: { id: sessionStorage.getItem('id') },
        ubicacion: formValue.ubicacion,
        descripcion: formValue.descripcion,
        fecha_captura: fechaLegible
      };

      this.fotosService.añadirFoto(foto).subscribe(
        response => {
          console.log('Animal agregado', response);
        },
        error => {
          console.error('Error al agregar el animal', error);
        }
      );
    } else {
      console.error('Formulario inválido');
    }
  }

  //No creo que use este metodo en el lo quitaré
  submitForm() {
    const fechaActual = new Date();
    const fechaLegible = fechaActual.toLocaleDateString('es-ES');
    console.log(fechaLegible);

    if (this.fotoForm.valid &&/* this.file &&*/ this.emailUsuario) {
      const formData = new FormData();
      //formData.append('archivo', this.file, 'foto.jpg'); // Archivo
      formData.append('ubicacion', this.fotoForm.value.ubicacion);
      formData.append('descripcion', this.fotoForm.value.descripcion);
      formData.append('fecha_captura', this.fotoForm.value.fecha_captura);
      formData.append('email_usuario', this.emailUsuario);
      this.http.post('https://tuapi.com/fotos', formData).subscribe(

      );
    }
  }
}
