import { Component, OnInit,Input, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router,ActivatedRoute } from '@angular/router';
import { CulturalOffer } from '../model/CulturalOffer';
import { JwtHelperService } from '@auth0/angular-jwt';
import { CulturalOfferDetailsService } from '../services/cultural-offer-details/cultural-offer-details.service';
import { RegisteredUserService } from '../services/registered-user/registered-user.service';
import { ToastrService } from 'ngx-toastr';
import { AddPostComponent } from '../add-post/add-post.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-cultural-offer-details',
  templateUrl: './cultural-offer-details.component.html',
  styleUrls: ['./cultural-offer-details.component.scss']
})
export class CulturalOfferDetailsComponent implements OnInit {

  role!: string|undefined;
  culturalOffer!: CulturalOffer;
  id: any = '';
  subscribed!: number;
  
  constructor(
    public dialog: MatDialog,
    private coService: CulturalOfferDetailsService,
    private regUserService: RegisteredUserService,
    private route : ActivatedRoute,
    private router: Router,
		private toastr: ToastrService) {
  }

  ngOnInit() {
    this.getRole();
    //fill data
    this.id = this.route.snapshot.paramMap.get('id');
    this.coService.getOne(this.id).subscribe(
      res => {
        this.culturalOffer = res.body as CulturalOffer;
      }
    );
    this.regUserService.getNumberOfSubscribed(this.id).subscribe(
      res => {
        this.subscribed = res.body;
      }
    );
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
  addPost(){
    const dialogRef = this.dialog.open(AddPostComponent);
    dialogRef.componentInstance.culturalOfferId = this.route.snapshot.paramMap.get('id');
    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });
  }
  deleteCulturalOffer(){
    this.coService.delete(this.id).subscribe(
      result => {
        this.router.navigate(['home']);
        this.toastr.success(result);
      }
    );
  }


}
