import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ListApiResponse } from '../interfaces/listapi-response.interface';
import { environment } from '../../environments/environment';
import { User } from '../models/user';
import { UserDto } from '../dto/user.dto';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient, private authService: AuthService) { }

  listUsuarios(): Observable<ListApiResponse> {
    const requestOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
        'Access-Control-Allow-Origin': '*'
      })
    };
    return this.http.get<ListApiResponse>(`${environment.ApiUrl}users`, requestOptions);
  }

  deleteUsuario(usuario: User): Observable<User> {
    console.log(usuario.id);
    
    const requestOptions = {
      headers: new HttpHeaders({
        'Content-Type' : 'application/json',
        'Authorization': `Bearer ${this.authService.getToken()}`,
        'Access-Control-Allow-Origin': '*'
      })
    };

    return this.http.delete<User>(`${environment.ApiUrl}/users/${usuario.id}`, requestOptions);
  }

  addUser(user: UserDto): Observable<User>{
    const requestOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
        'Access-Control-Allow-Origin': '*'
      })
    };

    return this.http.post<User>(`${environment.ApiUrl}users`, requestOptions);
  }
  
}
