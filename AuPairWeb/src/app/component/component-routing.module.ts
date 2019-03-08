import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { TablaUsuariosComponent } from './tabla-usuarios/tabla-usuarios.component';

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'tablaUsuarios',
        component: TablaUsuariosComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ComponentRoutingModule { }
