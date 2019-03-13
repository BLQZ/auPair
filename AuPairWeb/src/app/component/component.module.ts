import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, Form } from '@angular/forms';
import { JsonpModule } from '@angular/http';
import { RouterModule } from '@angular/router';

import { ComponentRoutingModule } from './component-routing.module';
import { TablaUsuariosComponent } from './tabla-usuarios/tabla-usuarios.component';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatRadioModule, MatButtonModule, MatChipsModule, MatSnackBarModule, MatDialogModule, MatToolbarModule } from '@angular/material';
import {MatPaginatorModule} from '@angular/material/paginator';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgxPaginationModule } from 'ngx-pagination';
import { routes } from './component-routing.module';
import { TablaAnunciosComponent } from './tabla-anuncios/tabla-anuncios.component';

@NgModule({
  declarations: [TablaUsuariosComponent, TablaAnunciosComponent],
  imports: [
    CommonModule,
    ComponentRoutingModule,
    RouterModule.forChild(routes),
    FormsModule,
    ReactiveFormsModule,
    JsonpModule,
    NgbModule,
    MatTableModule,
    NgxPaginationModule,
    MatCardModule,
    FlexLayoutModule,
    MatIconModule,
    MatListModule,
    MatRadioModule,
    MatButtonModule,
    MatChipsModule,
    MatPaginatorModule,
    MatSnackBarModule,
    MatDialogModule,
    MatToolbarModule
  ]
})
export class ComponentModule { }
