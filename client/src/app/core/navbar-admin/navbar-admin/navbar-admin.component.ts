import { Component, OnInit } from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import { Router } from '@angular/router';
import { AddAdminComponent } from 'src/app/pages/add-admin/add-admin.component';
import { AuthenticationService } from 'src/app/pages/services/authentication/authentication.service';
import {AddCulturalOfferComponent} from '../../../pages/add-cultural-offer/add-cultural-offer.component';

@Component({
  selector: 'app-navbar-admin',
  templateUrl: './navbar-admin.component.html',
  styleUrls: ['./navbar-admin.component.scss']
})
export class NavbarAdminComponent implements OnInit {

  constructor(public dialog: MatDialog, private router: Router,
    private authenticationService: AuthenticationService) {}

  ngOnInit(){
  }
  newCulturalOffer(){
    const dialogRef = this.dialog.open(AddCulturalOfferComponent);
    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });
  }

  newAdmin(){
    const dialogRef = this.dialog.open(AddAdminComponent);
    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });
  }

  signOut(){
    this.authenticationService.signOut().subscribe(      
			result => {
				localStorage.removeItem('user');
				this.router.navigate(['/login']);
			}
		);
  }
}
