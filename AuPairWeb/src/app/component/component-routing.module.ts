import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { TablaUsuariosComponent } from './tabla-usuarios/tabla-usuarios.component';
import { TablaAnunciosComponent } from './tabla-anuncios/tabla-anuncios.component';

export const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'tablaUsuarios',
        component: TablaUsuariosComponent
      }
    ]
  },
  {
    path: '',
    children: [
      {
        path: 'tablaAnuncios',
        component: TablaAnunciosComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ComponentRoutingModule { }
