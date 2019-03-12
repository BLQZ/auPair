import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ToolbarComponent } from './toolbar/toolbar.component';

export const routes: Routes = [
  {
    path: 'component',
    component: ToolbarComponent,
    children: [
      {
        path: '',
        loadChildren: './component/component.module#ComponentModule'
      }
    ]
  },
  {
    path: 'session',
    children: [
      {
        path: '',
        loadChildren: './session/session.module#SessionModule'
      }
    ]
  }
  // { path: '', redirectTo: '/component/inicio', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
