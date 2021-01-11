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
import { MatDialog } from '@angular/material/dialog';
import { EditProfileComponent } from '../edit-profile/edit-profile.component';
import { EditPasswordComponent } from '../edit-password/edit-password.component';


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
    private sanitizer: DomSanitizer,
    private dialog: MatDialog
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

  edit(){
    const dialogRef = this.dialog.open(EditProfileComponent , {
      width: '350px',
      data: this.user});
    dialogRef.afterClosed().subscribe(result => {
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
    });
  }

  editPassword(){
    const dialogRef = this.dialog.open(EditPasswordComponent);
    dialogRef.afterClosed().subscribe(result => {
      console.log('ok');
      // this.userService.getCurrentUser().subscribe(
      //   res => {
      //     this.user = res.body as User;
      //     this.imageService.getImage(this.user.idImageDTO).subscribe(
      //       res => {
              
      //         let base64String = btoa(String.fromCharCode(...new Uint8Array(res.body)));
      //         let objectURL = 'data:image/jpg;base64,' + base64String;   
      //         this.user.src = this.sanitizer.bypassSecurityTrustUrl(objectURL);
      //     }
      //   );
      //   }
      // );
    });
  }

}
