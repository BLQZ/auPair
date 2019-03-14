import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ListApiResponse } from '../interfaces/listapi-response.interface';
import { environment } from '../..//environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AnuncioService {

  constructor(private http: HttpClient, private authService: AuthService) { }

  listAnuncios(): Observable<ListApiResponse>{
    const requestOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'Bearer 4JeZTCtYMb9PIyFN0juQY3FMP8Hdrt6Q',
        'Access-Control-Allow-Origin': '*'
      })
    };
    // class Metakey {
    //   access_token: String;

    //   constructor(access_token: String) {
    //     this.access_token = access_token;
    //   }
    // }
    // const metaKey = new Metakey('4JeZTCtYMb9PIyFN0juQY3FMP8Hdrt6Q');
    return this.http.get<ListApiResponse>(`${environment.ApiUrl}anuncios`, requestOptions);

  }

  deleteAnuncio(id: String) {
    const requestOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
        'Access-Control-Allow-Origin': '*'
      })
    };

    return this.http.delete(`${environment.ApiUrl}anuncios/${id}`, requestOptions);
  }
}
