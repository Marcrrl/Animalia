import { IonicModule } from '@ionic/angular';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HospitalesPage } from './hospitales.page';
import { HospitalesPageRoutingModule } from './hospitales-routing.module';

@NgModule({
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    HospitalesPageRoutingModule
  ],
  declarations: [HospitalesPage]
})
export class HospitalesPageModule {}
