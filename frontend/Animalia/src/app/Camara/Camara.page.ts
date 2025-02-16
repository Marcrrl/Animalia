import { HttpClient } from '@angular/common/http';
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
  fotoForm: FormGroup ;
  file: Blob | null = null;
  emailUsuario: string | null = null;
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
    } catch (error) {
      console.log('Error al tomar la foto', error);
    }
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
