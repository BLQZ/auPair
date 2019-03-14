import { Component, OnInit, ViewChild } from '@angular/core';
import { AnuncioService } from '../../../app/services/anuncio.service';
import { AuthService } from '../../../app/services/auth.service';
import { MatSnackBar, MatDialog, MatTableDataSource, MatPaginator } from '@angular/material';
import { Router } from '@angular/router';
import { ListApiResponse } from '../../../app/interfaces/listapi-response.interface';
import { ListAnuncioResponse } from '../../../app/interfaces/listanuncio-response.interface';

@Component({
  selector: 'app-tabla-anuncios',
  templateUrl: './tabla-anuncios.component.html',
  styleUrls: ['./tabla-anuncios.component.css']
})
export class TablaAnunciosComponent implements OnInit {
  listaApi: ListApiResponse;
  listaAnunciosRes: ListAnuncioResponse[];
  displayedColumns: string[] = ['createdAt', 'ownerId', 'contenido', 'acciones'];
  dataSource = new MatTableDataSource<ListAnuncioResponse[]>();
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private anuncioService: AnuncioService, private authService: AuthService,
    public snackBar: MatSnackBar, public dialog: MatDialog, private router: Router) {}

  ngOnInit() {
    this.listAnuncios();
  }

  listAnuncios() {
    this.anuncioService.listAnuncios().subscribe(lista => {
      this.listaApi = lista;
      this.listaAnunciosRes = this.listaApi.rows;
      this.dataSource = new MatTableDataSource<ListAnuncioResponse[]>(this.listaApi.rows);
      this.dataSource.paginator = this.paginator;
      // this.dataSource = lista;
      console.log(this.listaAnunciosRes);

    }, error => {
      console.error(error);
    });
  }

  deleteAnuncio(id: String){
    this.anuncioService.deleteAnuncio(id).subscribe(result => {this.listAnuncios()})
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

}
