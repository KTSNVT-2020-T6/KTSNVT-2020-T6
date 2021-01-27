import { UserService } from '../../../core/services/user/user.service';
import { Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { ToastrService } from 'ngx-toastr';
import { User } from '../../../core/model/User';
import { Img } from '../../../core/model/Image';
import { ImageService } from '../../../core/services/image/image.service';
import { DomSanitizer } from '@angular/platform-browser';
import { MatDialog } from '@angular/material/dialog';
import { EditProfileComponent } from '../edit-profile/edit-profile.component';
import { EditPasswordComponent } from '../edit-password/edit-password.component';
import { AdminService } from '../../../core/services/admin/admin.service';
import { AuthenticationService } from 'src/app/core/services/authentication/authentication.service';
import { RegisteredUserService } from '../../../core/services/registered-user/registered-user.service';
import { ConfirmationComponent, ConfirmDialogModel } from '../../shared/confirmation/confirmation.component';

@Component({
  selector: 'app-profile-details',
  templateUrl: './profile-details.component.html',
  styleUrls: ['./profile-details.component.scss']
})
export class ProfileDetailsComponent implements OnInit {
  user!: User;
  image!: Img;
  role!: string|undefined;
  result!: any;

  constructor(
    private userService: UserService,
    private adminService: AdminService,
    private regUserService: RegisteredUserService,
    private imageService: ImageService,
    private router: Router,
    private toastr: ToastrService,
    private sanitizer: DomSanitizer,
    private dialog: MatDialog,
    private authenticationService: AuthenticationService
  ) {}

  ngOnInit(): void {
    this.getRole();
    this.userService.getCurrentUser().subscribe(
      res => {
        this.user = res.body as User;
        if (this.user.idImageDTO === null){
          return;
        }
        this.imageService.getImage(this.user.idImageDTO).subscribe(
          response => {
            const base64String = btoa(String.fromCharCode(...new Uint8Array(response.body)));
            const objectURL = 'data:image/jpg;base64,' + base64String;
            this.user.src = this.sanitizer.bypassSecurityTrustUrl(objectURL);
        }
      );
      }
    );
  }

  edit(): void{
    const dialogRef = this.dialog.open(EditProfileComponent , {
      width: '350px',
      data: this.user});
    dialogRef.afterClosed().subscribe(result => {
      this.userService.getCurrentUser().subscribe(
        res => {
          this.user = res.body as User;
          this.imageService.getImage(this.user.idImageDTO).subscribe(
            response => {
              const base64String = btoa(String.fromCharCode(...new Uint8Array(response.body)));
              const objectURL = 'data:image/jpg;base64,' + base64String;
              this.user.src = this.sanitizer.bypassSecurityTrustUrl(objectURL);
          }
        );
        }
      );
    });
  }

  editPassword(): void{
    const dialogRef = this.dialog.open(EditPasswordComponent);
    dialogRef.afterClosed().subscribe(result => {
      console.log('ok');
    });
  }

  confirmDialog(): void {
    const message = `Are you sure you want to deactivate account`;
    const dialogData = new ConfirmDialogModel('Confirm Action', message);
    const dialogRef = this.dialog.open(ConfirmationComponent, {
      maxWidth: '400px',
      data: dialogData
    });

    dialogRef.afterClosed().subscribe(dialogResult => {
      this.result = dialogResult;
      if (this.result === true){
        this.deleteProfile();
      }
    });
  }
  deleteProfile(): void {
    if (this.role === 'ROLE_ADMIN'){
      this.adminService.delete(this.user.id).subscribe(
        result => {
          this.authenticationService.signOut().subscribe(
            res => {
              this.toastr.warning('Profile successfully deactivated.');
              localStorage.removeItem('user');
              this.router.navigate(['/login']);
            }
          );
        }
      );
    }
    else{
      this.regUserService.delete(this.user.id).subscribe(
        result => {
          this.authenticationService.signOut().subscribe(
            res => {
              this.toastr.warning('Profile successfully deactivated.');
              localStorage.removeItem('user');
              this.router.navigate(['/login']);
            }
          );
        }
      );
    }

  }
  getRole(): void {
    const item = localStorage.getItem('user');
    if (!item) {
      this.role = undefined;
      return;
    }
    const jwt: JwtHelperService = new JwtHelperService();
    this.role = jwt.decodeToken(item).role;
  }

}
