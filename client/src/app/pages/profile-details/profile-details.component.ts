import { UserService } from '../services/user/user.service';
import { Component, OnInit,Input, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router,ActivatedRoute } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { ToastrService } from 'ngx-toastr';
import { User } from '../model/User';
import { Img } from '../model/Image';
import { ImageService } from '../services/image/image.service';
import { DomSanitizer } from '@angular/platform-browser';


@Component({
  selector: 'app-profile-details',
  templateUrl: './profile-details.component.html',
  styleUrls: ['./profile-details.component.scss']
})
export class ProfileDetailsComponent implements OnInit {
  user!: User;
  image!: Img;

  constructor(
    private userService: UserService,
    private imageService: ImageService,
    private route : ActivatedRoute,
    private router: Router,
    private toastr: ToastrService,
    private sanitizer: DomSanitizer
  ) {}

  ngOnInit() {
    this.userService.getCurrentUser().subscribe(
      res => {
        this.user = res.body as User;
        this.imageService.getImage(this.user.idImageDTO).subscribe(
          res => {
            
            let base64String = btoa(String.fromCharCode(...new Uint8Array(res.body)));
            let objectURL = 'data:image/jpg;base64,' + base64String;   
            this.user.src = this.sanitizer.bypassSecurityTrustUrl(objectURL);
        }
      );
      }
    );
   
  }

}
