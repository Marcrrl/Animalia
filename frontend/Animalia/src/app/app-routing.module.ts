import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    loadChildren: () => import('./tabs/tabs.module').then(m => m.TabsPageModule)
  },
  {
    path: 'Animales',
    loadChildren: () => import('./animales/animales.module').then( m => m.AnimalesPageModule)
  },
  {
    path: 'detalles-animal/:id',
    loadChildren: () => import('./detalles-animal/detalles-animal.module').then( m => m.DetallesAnimalPageModule)
  },
  {
    path: 'Mapa',
    loadChildren: () => import('./Mapa/Mapa.module').then( m => m.MapaPageModule)
  },
  {
    path: 'sign-form',
    loadChildren: () => import('./sign-form/sign-form.module').then( m => m.SignFormPageModule)
  },
  {
    path: 'Empresas',
    loadChildren: () => import('./empresas/empresas.module').then( m => m.EmpresasPageModule)
  }


];
@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {}
