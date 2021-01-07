import { Component, OnInit } from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {AddCulturalOfferComponent} from '../../../pages/add-cultural-offer/add-cultural-offer.component';

@Component({
  selector: 'app-navbar-admin',
  templateUrl: './navbar-admin.component.html',
  styleUrls: ['./navbar-admin.component.scss']
})
export class NavbarAdminComponent implements OnInit {

  constructor(public dialog: MatDialog) {}

  ngOnInit(){
  }
  newCulturalOffer(){
    const dialogRef = this.dialog.open(AddCulturalOfferComponent);
    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });
  }
}
