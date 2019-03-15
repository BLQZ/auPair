import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../../src/app/services/auth.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginDto } from '../../../../src/app/dto/login.dto';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  email: string;
  password: string;
  public form: FormGroup;

  constructor(private loginService: AuthService,
    private router: Router, private snackBar: MatSnackBar, private fb: FormBuilder) { }

  public isError = false;

  ngOnInit() {
    this.form = this.fb.group({
      email: [null, Validators.compose([Validators.required])],
      password: [null, Validators.compose([Validators.required])]
    });
  }

  doLogin() {



    const loginDto = new LoginDto(this.email, this.password);
    this.loginService.login(loginDto).subscribe(loginResp => {
      console.log(loginResp);
      this.loginService.setLoginData(loginResp);
      console.log('ROL: ' + localStorage.getItem('role'));
      this.router.navigate(['/component/tablaUsuarios']);

    }, error => {
      this.snackBar.open(`Acceso único a administradores`, 'X', {
        duration: 3000
      });
    }
    );

  }

}
