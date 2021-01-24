import { Component, Input, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { CulturalOffer } from '../../../core/model/CulturalOffer';
import { User } from '../../../core/model/User';
import { CommentService } from '../../../core/services/comment/comment.service';
import { CulturalOfferDetailsService } from '../../../core/services/cultural-offer-details/cultural-offer-details.service';
import { ImageService } from '../../../core/services/image/image.service';
import { RegisteredUserService } from '../../../core/services/registered-user/registered-user.service';
import { UserService } from '../../../core/services/user/user.service';
import { Comment } from '../../../core/model/Comment';

@Component({
  selector: 'app-add-comment',
  templateUrl: './add-comment.component.html',
  styleUrls: ['./add-comment.component.scss']
})
export class AddCommentComponent implements OnInit {
  @Input() culturalOfferId!:number;
  commentForm!: FormGroup;
  imageAdded: any;
  comment!: Comment;
  currentUser!: User;
  commentText!: string;
  role!: string|undefined;
  culturalOffer!: CulturalOffer;
  subscribed!: number;
  result:any;
  checker!: boolean;
  
  constructor(
    private fb: FormBuilder,
    private commentService: CommentService,
    private userService: UserService,
    private imageService: ImageService,
		private toastr: ToastrService) {
    this.createForm();
    this.comment =  {'nameSurname' : '', "text": '', 'date' : new Date(), 'userId' : 0};
  }
   createForm(){
    this.commentForm = this.fb.group({
      'text': [''],
      'image':['']
       });
  }
  ngOnInit(): void {
  }
  addNewComment(){
    this.userService.getCurrentUser().subscribe(
      res => {
        console.log("aaaaaaaaa");
        console.log(res);
        console.log(res.body);
        this.currentUser = res.body as User;
        this.comment.nameSurname = this.currentUser.firstName + ' ' + this.currentUser.lastName;
        this.comment.text = this.commentForm.controls['text'].value;
        this.comment.culturalOfferId = this.culturalOfferId;
        this.comment.userId = this.currentUser.id;
        this.comment.date = new Date();
        if(this.commentForm.invalid) {
           this.toastr.error('Comment cannot be empty!');
           this.comment.imageDTO = undefined;
           return;
        }
        else if (this.commentForm.value['image'] !== '')
        {
          const formData = new FormData();
          formData.append('file', this.commentForm.value['image']);
          this.imageService.add(formData).subscribe(
            res => {
              this.comment.imageDTO = {'id': res};
              this.commentService.save(this.comment).subscribe(
              res => {
                this.toastr.success("Comment send!");
                this.commentForm.reset()
                this.windowReload();
              },error=>{
                this.toastr.error("Error on server!");
              }
              )
          });
          }else 
          { 
            this.commentService.save(this.comment).subscribe(
              res => {
                this.toastr.success("Comment send!");
                this.commentForm.reset()
                this.windowReload();
               },error=>{
                this.toastr.error("Error on server!");
            }
           )
        }
      });
  }
  onFileSelect(event: any) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
       this.commentForm.value['image'] = file;
    }
  }
  windowReload(){
    window.location.reload();
  }
}
