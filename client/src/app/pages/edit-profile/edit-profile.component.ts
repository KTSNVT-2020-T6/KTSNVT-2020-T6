import { Component, Inject, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { ToastrService } from 'ngx-toastr';
import { User } from '../model/User';
import { AuthenticationService } from '../services/authentication/authentication.service';
import { ImageService } from '../services/image/image.service';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.scss']
})
export class EditProfileComponent implements OnInit {
  form!: FormGroup;
  emailRegx = /^(([^<>+()\[\]\\.,;:\s@"-#$%&=]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,3}))$/;
  role!: string|undefined;

  constructor(private fb: FormBuilder,
    private authenticationService: AuthenticationService,
    private router: Router,
    private toastr: ToastrService,
    private imageService: ImageService,public dialogRef: MatDialogRef<EditProfileComponent>,
    @Inject(MAT_DIALOG_DATA) public user: User) {
      this.createForm();
      this.form.patchValue(this.user);
     }

  ngOnInit(): void {
    this.getRole();
  }

  getRole() {
    const item = localStorage.getItem('user');
    if (!item) {
      this.role = undefined;
      return;
    }
    const jwt: JwtHelperService = new JwtHelperService();
    this.role = jwt.decodeToken(item).role;
  }

  createForm() {
    this.form = this.fb.group({
      'firstName': ['', Validators.required],
      'lastName': ['', Validators.required],
      'email':['', [Validators.required, Validators.pattern(this.emailRegx)]],
      'image':[]
    }
  )};

  onFileSelect(event: any) {
        if (event.target.files.length > 0) {
          const file = event.target.files[0];
          this.form.value['image'] = file;
        }
        this.saveImage();
  };

  saveChanges(){
    
    this.user.firstName = this.form.value['firstName'];
    this.user.lastName = this.form.value['lastName'];
    this.user.email = this.form.value['email'];

    if(this.role == 'ROLE_ADMIN'){
      this.authenticationService.editAdmin(this.user as User).subscribe(
        result => {
          this.toastr.success('Profile information saved!');
          this.form.reset();
          this.dialogRef.close();
        },
        error => {
          this.toastr.error("Error saving data!");
        }
      );
   } else{
    this.authenticationService.editUser(this.user as User).subscribe(
      result => {
        this.toastr.success('Profile information saved!');
        this.form.reset();
        this.dialogRef.close();
      },
      error => {
        this.toastr.error("Error saving data!");
      }
    );

   }
    }

    saveImage(){
      const formData = new FormData();
      formData.append('file', this.form.value['image']);

      this.imageService.add(formData).subscribe(
        result => {
          this.user.idImageDTO = result; 
        },
        error => {
          this.toastr.error("Error saving image! Choose different one!");
        }

      );
    }

    cancel(){
      this.dialogRef.close();
    }

}
