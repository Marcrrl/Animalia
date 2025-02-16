import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Camera, CameraResultType, CameraSource } from '@capacitor/camera';

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
  constructor(
    private fb: FormBuilder, private http: HttpClient
  ) {
    this.fotoForm = this.fb.group({
      ubicacion: ['', Validators.required],
      descripcion: ['', Validators.required],
      fecha_captura: ['', Validators.required],
      emailUsuario: ['', Validators.required],
    });
  }

  ngOnInit() {


    // Obtener el email del usuario desde localStorage
    const storedEmail = localStorage.getItem('email');
    if (storedEmail) {
      this.emailUsuario = storedEmail;
      this.fotoForm.patchValue({ email_usuario: this.emailUsuario });
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
    const file = new File([blob], `photo_${Date.now()}.jpeg`, { type: blob.type });
    return file;
  }


  submitForm() {
    if (this.fotoForm.valid &&/* this.file &&*/ this.emailUsuario) {
      const formData = new FormData();
      //formData.append('archivo', this.file, 'foto.jpg'); // Archivo
      formData.append('ubicacion', this.fotoForm.value.ubicacion);
      formData.append('descripcion', this.fotoForm.value.descripcion);
      formData.append('fecha_captura', this.fotoForm.value.fecha_captura);
      formData.append('email_usuario', this.emailUsuario);
      this.http.post('https://tuapi.com/fotos', formData).subscribe(

        /* Aqui poner metodo api fotos post
        response => {
          console.log('Foto guardada con éxito', response);
        },
        error => {
          console.error('Error al guardar la foto', error);
        }*/
      );
    }
  }
}
