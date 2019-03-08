import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SessionRoutingModule } from './session-routing.module';
import { LoginComponent } from './login/login.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { environment } from '../../environments/environment';
import { AuthService } from '../services/auth.service';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import { Sessionroutes } from './session-routing.module';

@NgModule({
  declarations: [LoginComponent],
  imports: [
    CommonModule,
    SessionRoutingModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    RouterModule,
    FormsModule,
    MatSnackBarModule,
    RouterModule.forChild(Sessionroutes)
  ],
  providers: [
    AuthService
  ]
})
export class SessionModule { }
