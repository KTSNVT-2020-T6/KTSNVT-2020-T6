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
  form!: FormGroup;
  
  constructor(
    private fb: FormBuilder,
    public dialog: MatDialog,
    private coService: CulturalOfferDetailsService,
    private regUserService: RegisteredUserService,
    private userService: UserService,
    private rateService: RateService,
    private imageService: ImageService,
    private route : ActivatedRoute,
    private router: Router,
		private toastr: ToastrService) {
      this.createForm();
  }
  createForm(){
    this.form = this.fb.group({
      'image': []
    });
  }
  ngOnInit() {
    
    this.getRole();
    //fill data
    this.id = this.route.snapshot.paramMap.get('id');
    this.coService.getOne(this.id).subscribe(
      res => {
        this.culturalOffer = res.body as CulturalOffer;
        this.images =  this.culturalOffer.imageDTO as Img[];
      }
    );
    this.regUserService.getNumberOfSubscribed(this.id).subscribe(
      res => {
        this.subscribed = res.body;
      }
    );
  }
  rateClicked(rated:any){
    this.rate.number = rated.rating as number;
    this.rate.culturalOfferId = this.culturalOffer.id;
    this.rate.registredUserId = 1;
    console.log(this.rate);
    this.rateService.createOrEditRate(this.rate).subscribe(
      result => {
        if(result.body === null){
          this.rateService.createRate(this.rate).subscribe(
            res => {
              this.coService.getOne(this.id).subscribe(
                res => {
                  this.culturalOffer = res.body as CulturalOffer;
                  this.images =  this.culturalOffer.imageDTO as Img[];
                }
              );
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
                }
              );
            }
          );
        }
       
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
   onFileSelect(event: any) {
       if (event.target.files.length > 0) {
          const file = event.target.files[0];
           this.imageAdded = file;
        }
      }

   // addImage(){
    //   this.image.relativePath = this.form.value['image'];
    //   this.image.description = 'new_image';
    //   this.imageService.add(this.image as Image).subscribe(
    //     result => {
    //       console.log(result);
    //       this.imageId = result.body;
    //     }
    //   );
    //   }

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
        this.comment.imageDTO = {'id' : 1}
        console.log(this.imageAdded);
        const formData = new FormData();
        formData.append('file', this.imageAdded);
       // this.imageService.add(formData).subscribe(
        //  res => {
       //     this.toastr.success('Saved!');
        //    this.comment.imageDTO = res.body();
         // }
       // )
        
        console.log(" bice");
      }
    );
    
   

  }


}
