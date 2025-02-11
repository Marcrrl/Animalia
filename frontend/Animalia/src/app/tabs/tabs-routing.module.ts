import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TabsPage } from './tabs.page';

const routes: Routes = [
  {
    path: '',
    component: TabsPage,
    children: [
      {
        path: 'Camara',
        loadChildren: () => import('../Camara/Camara.module').then(m => m.CamaraPageModule)
      },
      {
        path: 'Animales',
        loadChildren: () => import('../animales/animales.module').then(m => m.AnimalesPageModule)
      },
      {
        path: 'Empresas',
        loadChildren: () => import('../empresas/empresas.module').then(m => m.EmpresasPageModule)
      },
      {
        path: 'Sign',
        loadChildren: () => import('../sign-form/sign-form.module').then(m => m.SignFormPageModule)
      },
      {
        path: 'Perfil',
        loadChildren: () => import('../perfil/perfil.module').then(m => m.PerfilPageModule)
      },
      
      {
        path: '',
        redirectTo: '/Animales',
        pathMatch: 'full'
      }
    ]}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
})
export class TabsPageRoutingModule {}
