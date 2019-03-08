import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ComponentRoutingModule } from './component-routing.module';
import { TablaUsuariosComponent } from './tabla-usuarios/tabla-usuarios.component';

@NgModule({
  declarations: [TablaUsuariosComponent],
  imports: [
    CommonModule,
    ComponentRoutingModule
  ]
})
export class ComponentModule { }
