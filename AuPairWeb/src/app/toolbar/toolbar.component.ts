import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css']
})
export class ToolbarComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit() {
  }

  irAnuncios(){
    this.router.navigate(['/component/tablaAnuncios']);
  }

  irUsuarios(){
    this.router.navigate(['/component/tablaUsuarios']);
  }

  logout(){
    this.router.navigate(['session/login']);
    localStorage.removeItem('token');
  }

}
