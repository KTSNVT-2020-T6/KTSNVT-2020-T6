import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DomSanitizer } from '@angular/platform-browser';
import { JwtHelperService } from '@auth0/angular-jwt';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationComponent, ConfirmDialogModel } from '../../shared/confirmation/confirmation.component';
import { CulturalOffer } from '../../../core/model/CulturalOffer';
import { Img } from '../../../core/model/Image';
import { Post } from '../../../core/model/Post';
import { CulturalOfferDetailsService } from '../../../core/services/cultural-offer-details/cultural-offer-details.service';
import { ImageService } from '../../../core/services/image/image.service';
import { PostService } from '../../../core/services/post/post.service';

@Component({
  selector: 'app-posts-page',
  templateUrl: './posts-page.component.html',
  styleUrls: ['./posts-page.component.scss']
})
export class PostsPageComponent implements OnInit {
  posts!: Post[];
  pageSize: number;
	currentPage: number;
  totalSize: number;
  image: any;
  role!: string|undefined;
  result: any;


  constructor(private postService: PostService,
    private culturalOfferService: CulturalOfferDetailsService,
    private imageService: ImageService,
    private toastr: ToastrService,
    private sanitizer: DomSanitizer,
    public dialog: MatDialog) {
      this.pageSize = 3;
		  this.currentPage = 1;
      this.totalSize = 1;
      this.posts = [];
  }

  ngOnInit(): void {
    this.getRole();
    this.postService.getPage(this.currentPage - 1, this.pageSize).subscribe(
			res => {
        this.posts = res.body.content as Post[];
        this.totalSize = Number(res.body.totalElements);
     
        this.posts.forEach(element => {
          this.image = element.imageDTO as Img;
      
          this.culturalOfferService.getOne(element.culturalOfferId).subscribe(
            res => {
              const co = res.body as CulturalOffer;
              element.culturalOfferName = co.name;
            }, error => {
              console.log(error.error);               
            });
          if(this.image == null){
              return;
            }
          this.imageService.getImage(this.image.id).subscribe(
            res => {
              let base64String = btoa(String.fromCharCode(...new Uint8Array(res.body)));
              let objectURL = 'data:image/jpg;base64,' + base64String;           
              element.src = this.sanitizer.bypassSecurityTrustUrl(objectURL);
            }, error => {
              console.log(error.error);
            });
       });
      

			}, error => {
        console.log(error.console.error);
        
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
  
  changePage(newPage: number) {
		this.postService.getPage(newPage - 1, this.pageSize).subscribe(
			res => {
        this.posts = res.body.content as Post[];
        this.totalSize = Number(res.body.totalElements);
     
        this.posts.forEach(element => {
          this.image = element.imageDTO as Img;
      
          this.culturalOfferService.getOne(element.culturalOfferId).subscribe(
            res => {
              const co = res.body as CulturalOffer;
              element.culturalOfferName = co.name;
            }, error => {
              console.log(error.error);               
            });
          if(this.image == null){
              return;
            }
          this.imageService.getImage(this.image.id).subscribe(
            res => {
              let base64String = btoa(String.fromCharCode(...new Uint8Array(res.body)));
              let objectURL = 'data:image/jpg;base64,' + base64String;           
              element.src = this.sanitizer.bypassSecurityTrustUrl(objectURL);
            }, error => {
              console.log(error.error);
            });
       });
      

			}, error => {
        console.log(error.console.error);
        
      }
    );
	}
  
  deletePost(postId: any)
  {
    this.postService.delete(postId).subscribe(
      res => {
        this.toastr.success("Post successfully deleted.");
        window.location.reload();
    },error =>{
      this.toastr.error("Cannot delete this post!");
      
    });
  }

  confirmDialog(id:any) {
    const message = `Are you sure you want to do this?`;
    const dialogData = new ConfirmDialogModel("Confirm Action", message);
    const dialogRef = this.dialog.open(ConfirmationComponent, {
      maxWidth: "400px", 
      data: dialogData
    });

    dialogRef.afterClosed().subscribe(dialogResult => {
      this.result = dialogResult;
      if(this.result === true){
        this.deletePost(id);
      }
    });
  }

}
