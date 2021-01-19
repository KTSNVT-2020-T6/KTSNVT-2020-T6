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
import { User } from '../model/User';
import { UserService } from '../services/user/user.service';
import { Comment } from '../model/Comment';
import { ImageService } from '../services/image/image.service';
import { CommentService } from '../services/comment/comment.service';import { EditCulturalOfferComponent } from '../edit-cultural-offer/edit-cultural-offer.component';
import { ConfirmationComponent, ConfirmDialogModel } from '../confirmation/confirmation.component';
import { Subscription } from 'rxjs';

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
    this.id = this.route.snapshot.paramMap.get('id');
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
    this.rate.registredUserId = 1;
    this.rateService.createOrEditRate(this.rate).subscribe(
      result => {
        this.coService.getOne(this.id).subscribe(
          res => {
            this.culturalOffer = res.body as CulturalOffer;
            this.images =  this.culturalOffer.imageDTO as Img[];
          }
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
    dialogRef.componentInstance.culturalOfferId = this.route.snapshot.paramMap.get('id');
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
  onFileSelect(event: any) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
        this.imageAdded = file;
    }
  }
	edit(){
    const dialogRef = this.dialog.open(EditCulturalOfferComponent , {
      data: this.culturalOffer});
    dialogRef.afterClosed().subscribe(result => {
      
    });

  }

  addNewComment(){
    this.userService.getCurrentUser().subscribe(
      res => {
        this.currentUser = res.body as User;
        this.comment.nameSurname = this.currentUser.firstName + ' ' + this.currentUser.lastName;
        this.comment.text = this.commentText;
        this.comment.culturalOfferId = this.id;
        this.comment.userId = this.currentUser.id;
        this.comment.date = new Date();
         // uploadoati sliku
      
        if(this.imageAdded === undefined && this.commentText === undefined) {
           this.toastr.error('Comment cannot be empty!');
           this.comment.imageDTO = undefined;
           return;
        }
        if (this.imageAdded !== undefined)
        { 
          const formData = new FormData();
          formData.append('file', this.imageAdded);
          this.imageService.add(formData).subscribe(
            res => {
              this.toastr.success('Saved!');
              this.comment.imageDTO = {'id': res};
              this.commentService.save(this.comment).subscribe(
             res => {
               this.toastr.success("Comment send!");
              })
            });
          }else
          {
            this.commentService.save(this.comment).subscribe(
              res => {
                this.toastr.success("Comment send!");
               })
          }
          
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
