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
        path: 'Perfil',
        loadChildren: () => import('../perfil/perfil.module').then(m => m.PerfilPageModule)
      },
      {
        path: '',
        redirectTo: '/Perfil',
        pathMatch: 'full'
      }
    ]
  },
  {
    path: '',
    redirectTo: '/Perfil',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
})
export class TabsPageRoutingModule {}
