import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { User } from '../model/User';
import { AuthenticationService } from '../services/authentication/authentication.service';

@Component({
  selector: 'app-edit-password',
  templateUrl: './edit-password.component.html',
  styleUrls: ['./edit-password.component.scss']
})
export class EditPasswordComponent implements OnInit {
  form!: FormGroup;
  hide = true;
  passwordError = false;

  constructor(private fb: FormBuilder,
    private authenticationService: AuthenticationService,
    private router: Router,
    private toastr: ToastrService,
    public dialogRef: MatDialogRef<EditPasswordComponent>){
      this.createForm();
    }

    ngOnInit(): void {
  }

  createForm() {
    this.form = this.fb.group({
      'oldPassword':['', Validators.required],
      'newPassword':['', [Validators.required]]
    }
  )};

  saveChanges(){
    this.passwordError = false;
    if(this.form.value['oldPassword'] == this.form.value['newPassword']){
      this.passwordError = true;
      this.toastr.error("Passwords are the same!");
      this.dialogRef.close();
      return;
    }
    const passwordData: any = {};
		passwordData.oldPassword = this.form.value['oldPassword'];
    passwordData.newPassword = this.form.value['newPassword'];

    this.authenticationService.changePassword(passwordData).subscribe(
      result => {
        this.toastr.success('New password saved! You have to sign in again.');
        this.authenticationService.signOut().subscribe(result =>{
          this.form.reset();
          this.dialogRef.close();
          localStorage.removeItem('user');
				  this.router.navigate(['/login']);
        });
      },
      error => {
        this.toastr.error("Error saving new password!");
      }
    );
    }

    cancel(){
      this.dialogRef.close();
    }

}
