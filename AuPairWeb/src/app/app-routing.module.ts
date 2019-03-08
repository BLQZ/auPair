import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

export const routes: Routes = [
  // {
  //   path: 'component',
  //   component: FullComponent,
  //   children: [
  //     {
  //       path: '',
  //       loadChildren: './component/component.module#ComponentsModule'
  //     }
  //   ]
  // },
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
