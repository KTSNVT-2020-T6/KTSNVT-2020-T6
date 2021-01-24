import { Component, Input, OnInit } from '@angular/core';
import { Img } from '../../../../core/model/Image';
import { Comment } from '../../../../core/model/Comment';
import {CommentService} from '../../../../core/services/comment/comment.service';
import { ImageService } from '../../../../core/services/image/image.service';
import { DomSanitizer } from '@angular/platform-browser';
import { FormBuilder, NumberValueAccessor } from '@angular/forms';
import { UserService } from '../../../../core/services/user/user.service';
import { User } from '../../../../core/model/User';
import { EditCommentComponent } from '../../edit-comment/edit-comment.component';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmationComponent, ConfirmDialogModel } from '../../../shared/confirmation/confirmation.component';
import { ToastrService } from 'ngx-toastr';

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
  result:any;

  constructor(private fb: FormBuilder,
              public dialog: MatDialog,
              private commentService: CommentService,
              private imageService: ImageService,
              private userService : UserService,
              private toastr: ToastrService,
              private sanitizer: DomSanitizer) {
    this.pageSize = 3;
		this.currentPage = 1;
		this.totalSize = 1;
  }
  changePage(newPage: number) {
		this.commentService.getPage(newPage - 1, this.pageSize, this.culturalOfferId).subscribe(
			res => {
        this.comments = res.body.content as Comment[];
				this.totalSize = Number(res.body.totalElements);
		
        this.comments.forEach(element => {
          if(element.userImage?.id  !== undefined){
            this.imageService.getImage(element.userImage?.id).subscribe(
              res => {
                let base64String = btoa(String.fromCharCode(...new Uint8Array(res.body)));
                let objectURL = 'data:image/jpg;base64,' + base64String;   
                element.srcUser = this.sanitizer.bypassSecurityTrustUrl(objectURL);
    
              }, error => {
                this.toastr.error("Cannot load image!");
                
              });
          }
         
          if(element.imageDTO?.id  !== undefined){
            this.imageService.getImage(element.imageDTO?.id).subscribe(
              res => {
                let base64String = btoa(String.fromCharCode(...new Uint8Array(res.body)));
                let objectURL = 'data:image/jpg;base64,' + base64String;   
                element.srcComment = this.sanitizer.bypassSecurityTrustUrl(objectURL);

              }, error => {
                this.toastr.error("Cannot load image!");
                 
              });
            }
       });

      

      }, error => {
        this.toastr.error("Cannot load from server!");
      }
    );
    
  }
  editComment(comId: any){

    const dialogRef = this.dialog.open(EditCommentComponent);
    dialogRef.componentInstance.commentId = comId;
    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
      location.reload();
    });
  }
  deleteComment(comId: any){
      this.commentService.delete(comId).subscribe(
        res =>{
          location.reload();
          this.toastr.success("Successfully deleted comment!");
        }, error => {
          this.toastr.error("Cannot delete comment!");
                  
        }
      );
      
  }
  
  ngOnInit(): void {
    this.userService.getCurrentUser().subscribe(
      res => {
        this.currentUser = res.body as User;
      } , error => {
        this.toastr.error("Cannot load user!");        
      }
    
    )
    this.commentService.getPage(this.currentPage - 1, this.pageSize, this.culturalOfferId).subscribe(
      res => {
        this.comments = res.body.content as Comment[];
				this.totalSize = Number(res.body.totalElements);
		
        this.comments.forEach(element => {
          if(element.userImage?.id  !== undefined){
            this.imageService.getImage(element.userImage?.id).subscribe(
              res => {
                let base64String = btoa(String.fromCharCode(...new Uint8Array(res.body)));
                let objectURL = 'data:image/jpg;base64,' + base64String;   
                element.srcUser = this.sanitizer.bypassSecurityTrustUrl(objectURL);
    
              }, error => {
                this.toastr.error("Cannot load image!");
                                
              });
          }
         
          if(element.imageDTO?.id  !== undefined){
            this.imageService.getImage(element.imageDTO?.id).subscribe(
              res => {
                let base64String = btoa(String.fromCharCode(...new Uint8Array(res.body)));
                let objectURL = 'data:image/jpg;base64,' + base64String;   
                element.srcComment = this.sanitizer.bypassSecurityTrustUrl(objectURL);

              }, error => {
                this.toastr.error("Cannot load image!");
                
                
              });
            }
        });
      },error =>{
        this.toastr.error("Cannot load from server!");
        
      }
     )
     
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
          this.deleteComment(id);
        }
      });
    }
}
