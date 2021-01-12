import { Component, Input, OnInit } from '@angular/core';
import { Img } from '../../model/Image';
import { Comment } from '../../model/Comment';
import {CommentService} from '../../services/comment/comment.service';
import { ImageService } from '../../services/image/image.service';
import { DomSanitizer } from '@angular/platform-browser';
import { FormBuilder, NumberValueAccessor } from '@angular/forms';
import { UserService } from '../../services/user/user.service';
import { User } from '../../model/User';
import { EditCommentComponent } from '../../edit-comment/edit-comment.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-comment-list',
  templateUrl: './comment-list.component.html',
  styleUrls: ['./comment-list.component.scss']
})
export class CommentListComponent implements OnInit {
  
  @Input() culturalOfferId!:number;
  comments!: Comment[];
  pageSize: number;
	currentPage: number;
  totalSize: number;
  userImage!: Img;
  commentImage!:Img;
  currentUser! : User ;

  constructor(private fb: FormBuilder,
              public dialog: MatDialog,
              private commentService: CommentService,
              private imageService: ImageService,
              private userService : UserService,
              private sanitizer: DomSanitizer) {
    this.pageSize = 5;
		this.currentPage = 1;
		this.totalSize = 1;
  }
  changePage(newPage: number) {
		this.commentService.getPage(newPage - 1, this.pageSize, this.culturalOfferId).subscribe(
			res => {
        this.comments = res.body.content as Comment[];
        console.log(this.comments);
				this.totalSize = Number(res.headers.get('Total-pages'));
			},error => {
        console.log(error.error);
      }
		);
  }
  editComment(comId: any){
    console.log("editovace se");
    const dialogRef = this.dialog.open(EditCommentComponent);
    dialogRef.componentInstance.commentId = comId;
    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
      location.reload();
    });
  }
  deleteComment(comId: any){
    // ne obrise se
    // promeni dialog

    if(confirm("Are you sure to delete your comment?")) {
      this.commentService.delete(comId).subscribe(
        res =>{
          console.log("deleted");
          location.reload();
        }
      )
    }
  }
  
  ngOnInit(): void {
    this.userService.getCurrentUser().subscribe(
      res => {
        this.currentUser = res.body as User;
      }
    )
    this.commentService.getPage(this.currentPage - 1, this.pageSize, this.culturalOfferId).subscribe(
			res => {
        this.comments = res.body.content as Comment[];
        console.log(this.comments);
        this.totalSize = Number(res.headers.get('Total-pages'));
       
        this.comments.forEach(element => {
            this.imageService.getImage(element.userImage?.id).subscribe(
            res => {
              let base64String = btoa(String.fromCharCode(...new Uint8Array(res.body)));
              let objectURL = 'data:image/jpg;base64,' + base64String;   
              element.srcUser = this.sanitizer.bypassSecurityTrustUrl(objectURL);

            }, error => {
              console.log(error.error);
              
            });
            //im
            
            this.imageService.getImage(element.imageDTO?.id).subscribe(
              res => {
                let base64String = btoa(String.fromCharCode(...new Uint8Array(res.body)));
                let objectURL = 'data:image/jpg;base64,' + base64String;   
                element.srcComment = this.sanitizer.bypassSecurityTrustUrl(objectURL);
  
              }, error => {
                console.log(error.error);
                
              });
         });

        
  
        }, error => {
          console.log(error.error);
          
        }
      );
      
    }

}
