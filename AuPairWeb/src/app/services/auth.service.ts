import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { LoginDto } from '../dto/login.dto';
import { Observable } from 'rxjs';
import { LoginResponse } from '../interfaces/login-response.interface';
import { environment } from '../../../src/environments/environment';

const jwtDecode = require('jwt-decode');


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient,
    private router: Router) { }

  login(loginDto: LoginDto): Observable<LoginResponse> {
    const requestOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Basic ` + btoa(`${loginDto.email}:${loginDto.password}`),
        'Access-Control-Allow-Origin': '*'
      })
    };
    class Metakey {
      access_token: String;

      constructor(access_token: String) {
        this.access_token = access_token;
      }
    }
    const metaKey = new Metakey('4JeZTCtYMb9PIyFN0juQY3FMP8Hdrt6Q');
    return this.http.post<LoginResponse>(`${environment.ApiUrl}auth/admin`, metaKey, requestOptions);
  }

  setLoginData(loginResponse: LoginResponse) {

    localStorage.setItem('token', loginResponse.token);
    localStorage.setItem('id', loginResponse.user.id);
    localStorage.setItem('name', loginResponse.user.name);
    localStorage.setItem('email', loginResponse.user.email);
    localStorage.setItem('role', loginResponse.user.role);

  }

  getToken() {

    return localStorage.getItem('token');
  }

  getTokenDecode() {
    if (!(this.getToken() == null))
      return jwtDecode(this.getToken());
    else
      return null
  }

  isAdmin() {
    if (!(this.getTokenDecode() == null)) {
      if (this.getTokenDecode().role === 'admin') {
        return true;
      } else {
        return false;
      }
    } else
      return false;
  }
}
