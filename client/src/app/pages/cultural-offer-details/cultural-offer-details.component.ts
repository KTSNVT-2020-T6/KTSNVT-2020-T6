import { Component, OnInit,Input, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router,ActivatedRoute } from '@angular/router';
import { CulturalOffer } from '../model/CulturalOffer';
import { JwtHelperService } from '@auth0/angular-jwt';
import { CulturalOfferDetailsService } from '../services/cultural-offer-details/cultural-offer-details.service';
import { RegisteredUserService } from '../services/registered-user/registered-user.service';
import { RateService } from '../services/rate/rate.service';
import { ToastrService } from 'ngx-toastr';
import { AddPostComponent } from '../add-post/add-post.component';
import { MatDialog } from '@angular/material/dialog';
import { Img} from '../../pages/model/Image';
import { Rate } from '../model/Rate';
import { EditCulturalOfferComponent } from '../edit-cultural-offer/edit-cultural-offer.component';

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
  images!: Img[];
  rate: Rate = {};
  
  constructor(
    public dialog: MatDialog,
    private coService: CulturalOfferDetailsService,
    private regUserService: RegisteredUserService,
    private rateService: RateService,
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
        this.images =  this.culturalOffer.imageDTO as Img[];
      },error => {
        this.toastr.error(error.console.error);
      }
    );
    this.regUserService.getNumberOfSubscribed(this.id).subscribe(
      res => {
        this.subscribed = res.body;
      },
      error => {
        this.toastr.error(error.console.error);
      }
    );
  }
  rateClicked(rated:any){
    this.rate.number = rated.rating as number;
    this.rate.culturalOfferId = this.culturalOffer.id;
    this.rate.registredUserId = 1;

    this.rateService.createOrEditRate(this.rate).subscribe(
      result => {
        if(result.body === null){
          this.rateService.createRate(this.rate).subscribe(
            res => {
              this.coService.getOne(this.id).subscribe(
                res => {
                  this.culturalOffer = res.body as CulturalOffer;
                  this.images =  this.culturalOffer.imageDTO as Img[];
                },
                  error => {
                    this.toastr.error(error.console.error);
                  }
              );
            },
            error => {
              this.toastr.error(error.console.error);
            }
          );
        }
        else{
          this.rate = result.body as Rate;
          this.rate.number = rated.rating as number;
          this.rateService.editRate(result.body).subscribe(
            res => {
              this.coService.getOne(this.id).subscribe(
                res => {
                  this.culturalOffer = res.body as CulturalOffer;
                  this.images =  this.culturalOffer.imageDTO as Img[];
                },
                error => {
                  this.toastr.error(error.console.error);
                }
              );
            },
            error => {
              this.toastr.error(error.console.error);
            }
          );
        }
       
      },
      error => {
        this.toastr.error(error.console.error);
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
      },error => {
        this.toastr.error(error.console.error);
      }
    );
  }

  edit(){
    const dialogRef = this.dialog.open(EditCulturalOfferComponent , {
      data: this.culturalOffer});
    dialogRef.afterClosed().subscribe(result => {
      
    });

  }
  addNewComment(){}


}
