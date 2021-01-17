import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthenticationService } from '../../pages/services/authentication/authentication.service';
import { Img } from '../model/Image';
import { User } from '../model/User';
import { ImageService } from '../services/image/image.service';

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.scss']
})
export class RegisterPageComponent implements OnInit {

  user!: User;
  form!: FormGroup;
  image!: Img;
  imageId!: number;
  emailRegx = /^(([^<>+()\[\]\\.,;:\s@"-#$%&=]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,3}))$/;
  hide = true;
  passwordError = false;

	constructor(
		private fb: FormBuilder,
    private authenticationService: AuthenticationService,
    private imageService: ImageService,
    private router: Router,
    private toastr: ToastrService
		
  ) {
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
      'repeatPassword':['', [Validators.required]],
      // 'image':['']
    }//,{ validator: validatePasswordRepeat}
  )};
    

    // addImage(){
    //   this.image.relativePath = this.form.value['image'];
    //   this.image.description = 'new_image';
    //   this.imageService.add(this.image as Image).subscribe(
    //     result => {
    //       console.log(result);
    //       this.imageId = result.body;
    //     }
    //   );
    //   }

    addUser(){
      this.passwordError = false;
      if(this.form.value['password'] != this.form.value['repeatPassword']){
        this.passwordError = true;
      }
      this.user = this.form.value as User;


      this.authenticationService.register(this.user as User).subscribe(
        result => {
          console.log(result);
          this.toastr.success('Check your email to confirm registration!');
          this.router.navigate(['home']);
          this.form.reset();
        },
        error => {
          console.log(error);
          this.toastr.error("Email already exists!");
        }
      );
      
      
      }

      // onFileSelect(event: any) {
      //   if (event.target.files.length > 0) {
      //     const file = event.target.files[0];
      //     this.form.value['image'].setValue(file);
      //   }
      // }

}
