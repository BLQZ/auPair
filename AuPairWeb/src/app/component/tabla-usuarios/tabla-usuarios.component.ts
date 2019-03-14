import { Component, OnInit, ViewChild } from '@angular/core';
import { ListApiResponse } from '../../../app/interfaces/listapi-response.interface';
import { ListUsuariosResponse } from '../../../app/interfaces/list-usuarios';
import { MatTableDataSource, MatPaginator, MatDialog, MatSnackBar } from '@angular/material';
import { UserService } from '../../../app/services/user.service';
import { AuthService } from '../../../app/services/auth.service';
import { Router } from '@angular/router';
import { User } from '../../../app/models/user';

@Component({
  selector: 'app-tabla-usuarios',
  templateUrl: './tabla-usuarios.component.html',
  styleUrls: ['./tabla-usuarios.component.css']
})
export class TablaUsuariosComponent implements OnInit {

  listaApi: ListApiResponse;
  listaUsuariosRes: ListUsuariosResponse[];
  displayedColumns: string[] = ['username', 'email', 'role', 'address', 'city', 'nHijos', 'date', 'acciones'];
  dataSource = new MatTableDataSource<ListUsuariosResponse[]>();
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private userService: UserService, private authService: AuthService,
    public snackBar: MatSnackBar, public dialog: MatDialog, private router: Router) { }

  ngOnInit() {
    this.listUser();
  }

  listUser() {
    this.userService.listUsuarios().subscribe(lista => {
      this.listaApi = lista;
      this.listaUsuariosRes = this.listaApi.rows;
      this.dataSource = new MatTableDataSource<ListUsuariosResponse[]>(this.listaApi.rows);
      this.dataSource.paginator = this.paginator;
      // this.dataSource = lista;
      console.log(this.listaUsuariosRes);

    }, error => {
      console.error(error);
    });
  }

  deleteUsuario(element: User) {
    console.log(element.id);

    this.userService.deleteUsuario(element).subscribe(resp => {

      console.log(resp);
      this.listUser();
    }, error => console.error(error)
    );
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

}
