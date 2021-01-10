import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthenticationService } from '../../pages/services/authentication/authentication.service';
import { Img } from '../model/Image';
import { User } from '../model/User';
import { ImageService } from '../services/image/image.service';


@Component({
  selector: 'app-add-admin',
  templateUrl: './add-admin.component.html',
  styleUrls: ['./add-admin.component.scss']
})
export class AddAdminComponent implements OnInit {
  user!: User;
  form!: FormGroup;
  emailRegx = /^(([^<>+()\[\]\\.,;:\s@"-#$%&=]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,3}))$/;
  hide = true;
  passwordError = false;

  constructor(private fb: FormBuilder,
    private authenticationService: AuthenticationService,
    private router: Router,
    private toastr: ToastrService) {
      this.createForm();
     }

  ngOnInit(): void {
  }

  createForm() {
    this.form = this.fb.group({
      'firstName': ['', Validators.required],
      'lastName': ['', Validators.required],
      'email':['', [Validators.required, Validators.pattern(this.emailRegx)]],
      'password':['', Validators.required],
      'repeatPassword':['', [Validators.required]]
    }
  )};

  addAdmin(){
    this.passwordError = false;
    if(this.form.value['password'] != this.form.value['repeatPassword']){
      this.passwordError = true;
    }
    this.user = this.form.value as User;


    this.authenticationService.registerAdmin(this.user as User).subscribe(
      result => {
        this.toastr.success('New admin added!');
        this.form.reset();
      },
      error => {
        this.toastr.error("Email already exists!");
      }
    );
    
    
    }
}
