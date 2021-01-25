import { Component, OnInit,Input, Output, EventEmitter, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router,ActivatedRoute } from '@angular/router';
import { CulturalOffer } from '../../../core/model/CulturalOffer';
import { JwtHelperService } from '@auth0/angular-jwt';
import { CulturalOfferDetailsService } from '../../../core/services/cultural-offer-details/cultural-offer-details.service';
import { RegisteredUserService } from '../../../core/services/registered-user/registered-user.service';
import { RateService } from '../../../core/services/rate/rate.service';
import { ToastrService } from 'ngx-toastr';
import { AddPostComponent } from '../../posts/add-post/add-post.component';
import { MatDialog } from '@angular/material/dialog';
import { Img} from '../../../core/model/Image';
import { Rate } from '../../../core/model/Rate';
import { User } from '../../../core/model/User';
import { UserService } from '../../../core/services/user/user.service';
import { Comment } from '../../../core/model/Comment';
import { ImageService } from '../../../core/services/image/image.service';
import { CommentService } from '../../../core/services/comment/comment.service';import { EditCulturalOfferComponent } from '../edit-cultural-offer/edit-cultural-offer.component';
import { ConfirmationComponent, ConfirmDialogModel } from '../../shared/confirmation/confirmation.component';
import { Subscription } from 'rxjs';
import { StarRatingComponent } from '../star-rating/star-rating.component';

@Component({
  selector: 'app-cultural-offer-details',
  templateUrl: './cultural-offer-details.component.html',
  styleUrls: ['./cultural-offer-details.component.scss']
})
export class CulturalOfferDetailsComponent implements OnInit {
 
  imageAdded: any;
  comment: Comment = {'nameSurname' : '', "text": '', 'date' : new Date(), 'userId' : 1};
  currentUser!: User;
  commentText!: string;
  role!: string|undefined;
  culturalOffer!: CulturalOffer;
  id: any = ''; // cultural offer id
  userId:any= '';
  subscribed!: number;
  images!: Img[];
  rate: Rate = {};
  result:any;
  checker!: boolean;
  subscription: Subscription;
  
  
  constructor(
    private fb: FormBuilder,
    public dialog: MatDialog,
    private coService: CulturalOfferDetailsService,
    private regUserService: RegisteredUserService,
    private commentService: CommentService,
    private userService: UserService,
    private rateService: RateService,
    private imageService: ImageService,
    private route : ActivatedRoute,
    private router: Router,
		private toastr: ToastrService) {
    this.checker = false;
    this.subscription = coService.RegenerateData$.subscribe(() =>
      this.getCulturalOffer()
    );
  }
  ngOnInit() {
    this.getRole();
    this.getCulturalOffer();
    this.checkSubscription();
    this.getNumberOfSubscribed();

  }
  getCulturalOffer(){
    //fill data
    this.id = this.route.snapshot.params['id'];
    this.coService.getOne(this.id).subscribe(
      res => {
        this.culturalOffer = res.body as CulturalOffer;
        this.images =  this.culturalOffer.imageDTO as Img[];
      }
    );
  }
  getNumberOfSubscribed(){
    this.regUserService.getNumberOfSubscribed(this.id).subscribe(
      res => {
        this.subscribed = res.body;
      }, error => {
        this.toastr.error("Cannot load from server!");
      }
    );
  }

  rateClicked(rated:any){
    this.rate.number = rated.rating as number;
    this.rate.culturalOfferId = this.culturalOffer.id;
    this.rate.registredUserId =  1;
    this.rateService.createOrEditRate(this.rate).subscribe(
      result => {
        this.coService.getOne(this.id).subscribe(
          res => {
            this.culturalOffer = res.body as CulturalOffer;
            this.coService.announceChange();
            this.images =  this.culturalOffer.imageDTO as Img[];
          },error => {
            this.toastr.error("Error loading!")}
        );
      },error => {
        this.toastr.error("Cannot load from server!");
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
    dialogRef.componentInstance.culturalOfferId = this.id;
    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });
  }
  deleteCulturalOffer(){
     
    this.coService.delete(this.id).subscribe(
      result => {
        this.router.navigate(['/']);
        this.toastr.success("Successfully deleted cultural offer");
      }, error => {
        this.toastr.error("Cannot delete from server!");
      }
    );
  }
	edit(){
    const dialogRef = this.dialog.open(EditCulturalOfferComponent , {
      data: this.culturalOffer});
    dialogRef.afterClosed().subscribe(result => {
      
    });

  }

  confirmDialog() {
    const message = `Are you sure you want to do this?`;

    const dialogData = new ConfirmDialogModel("Confirm Action", message);

    const dialogRef = this.dialog.open(ConfirmationComponent, {
      maxWidth: "400px",
      data: dialogData
    });

    dialogRef.afterClosed().subscribe(dialogResult => {
      this.result = dialogResult;
     
      if(this.result === true){
        this.deleteCulturalOffer();
      }
    });
  }
checkSubscription() {
  this.coService.getFavorite().subscribe(
    res =>{
      const favorites = res.body as CulturalOffer[];
      favorites.forEach(element => {
        if(element.id === this.culturalOffer.id){
          this.checker = true;
          return;
        }else{
          this.checker = false;
        }
      });
      
    }, error => {
      if(this.role === 'ROLE_REGISTERED_USER')
        this.toastr.error("Cannot load from server!");
    }
  )
} 
subscribeUser() {
  this.coService.subscribeUser(this.culturalOffer.id).subscribe(
    res =>{
      this.toastr.success("Subscribed to "+this.culturalOffer.name);
      this.checker = true;
      this.getNumberOfSubscribed();
      
    }, error => {
      this.toastr.error("Cannot subscribe user!");
    
    }
  )
}
unsubscribe() {
  this.coService.unsubscribe(this.culturalOffer.id).subscribe(
    res =>{
      this.toastr.success("Unsubscribed from "+this.culturalOffer.name);
      this.checker = false;
      this.getNumberOfSubscribed();
      
    },error => {
      this.toastr.error("Cannot unsubscribe user!");
    }
  )
}

}
