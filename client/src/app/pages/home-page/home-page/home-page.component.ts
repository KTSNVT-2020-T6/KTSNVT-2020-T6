import { Component, OnInit } from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {AddTypeComponent} from '../../add-type/add-type.component';
import {AddCulturalOfferComponent} from '../../add-cultural-offer/add-cultural-offer.component';
import {AddCategoryComponent} from '../../add-category/add-category.component';
@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss']
})
export class HomePageComponent implements OnInit {

  constructor(public dialog: MatDialog) {}

  ngOnInit() {
  }
  newType() {

    const dialogRef = this.dialog.open(AddTypeComponent);
    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });
  }
  newCategory() {
    const dialogRef = this.dialog.open(AddCategoryComponent);
    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });
    
  }
  newCulturalOffer(){
    const dialogRef = this.dialog.open(AddCulturalOfferComponent);
    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });
  }
}
