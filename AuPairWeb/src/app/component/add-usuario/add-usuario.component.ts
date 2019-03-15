import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { UserDto } from '../../../app/dto/user.dto';
import { UserService } from '../../..//app/services/user.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-add-usuario',
  templateUrl: './add-usuario.component.html',
  styleUrls: ['./add-usuario.component.css']
})
export class AddUsuarioComponent implements OnInit {
  public form: FormGroup;
  addUserDto: UserDto;

  constructor(private userService: UserService, private fb: FormBuilder,
    public dialogRef: MatDialogRef<AddUsuarioComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit() {
    this.form = this.fb.group({
      name: [null],
      email: [null],
      password: [null],
      role: [null]
    });
  }

  addUser(){
    this.addUserDto = new UserDto(this.form.controls['name'].value, this.form.controls['email'].value, this.form.controls['password'].value, this.form.controls['role'].value);
        this.userService.addUser(this.addUserDto).subscribe(() => {
          this.dialogRef.close();
        }, error => {
          console.error(error);
        });
  }
}
