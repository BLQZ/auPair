import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';

export const Sessionroutes: Routes = [
  {
    path: '',
    children: [
      { path: '', redirectTo: '/session/login' },
      {
        path: 'login',
        component: LoginComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(Sessionroutes)],
  exports: [RouterModule]
})
export class SessionRoutingModule { }
