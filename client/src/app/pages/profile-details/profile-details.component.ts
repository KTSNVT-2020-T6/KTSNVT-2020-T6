import { UserService } from '../services/user/user.service';
import { Component, OnInit,Input, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router,ActivatedRoute } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { ToastrService } from 'ngx-toastr';
import { User } from '../model/User';
import { Image } from '../model/Image';
import { ImageService } from '../services/image/image.service';


@Component({
  selector: 'app-profile-details',
  templateUrl: './profile-details.component.html',
  styleUrls: ['./profile-details.component.css']
})
export class ProfileDetailsComponent implements OnInit {
  user!: User;
  image!: Image;

  constructor(
    private userService: UserService,
    private imageService: ImageService,
    private route : ActivatedRoute,
    private router: Router,
		private toastr: ToastrService
  ) {}

  ngOnInit() {
    this.userService.getCurrentUser().subscribe(
      res => {
        this.user = res.body as User;
        this.imageService.getImage(this.user.idImageDTO).subscribe(
          res => {
            this.image = res.body as Image;
        }
      );
      }
    );
   
  }

}
