import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Route } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Comment } from '../model/Comment';
import { CommentService } from '../services/comment/comment.service';
import { ImageService } from '../services/image/image.service';

@Component({
  selector: 'app-edit-comment',
  templateUrl: './edit-comment.component.html',
  styleUrls: ['./edit-comment.component.css']
})
export class EditCommentComponent implements OnInit {
  editForm!: FormGroup;
  commentId!: number;
  imageAdded!: any;
  comment: Comment = {'nameSurname' : '', "text": '', 'date' : new Date(), 'userId' : 1};

  constructor(private fb: FormBuilder,
		private commentService: CommentService,
    private route: ActivatedRoute,
    private imageService: ImageService,
    private toastr: ToastrService,
		) { 
      this.createForm();
    }

  createForm(){
    this.editForm = this.fb.group({
      'text': [''],
      'image': [''],
     });
  }
  ngOnInit(): void {
    // dobaviti komentar
    /*
     this.commentService.getComment(this.commentId).subscribe(
      {
          res => {
            this.comment = res.body as Comment;
            this.editForm = this.fb.group({
            'text': [this.comment.text],
            'image': [this.comment.imageDTO],
           });
          }
      }
      
    )
    */
  }

  onFileSelect(event: any) {
    if (event.target.files.length > 0) {
       const file = event.target.files[0];
        this.imageAdded = file;
     }
   }

   editComment()
   {
     //podesiti detalje komentara
     
     this.comment.text = this.editForm.value('text');
     this.comment.date = new Date();
     // za slanje slike 
    const formData = new FormData();
    formData.append('file', this.imageAdded);
    this.imageService.add(formData).subscribe(
      res => {
        this.toastr.success('Saved!');
        this.comment.imageDTO = {'id': res};
        this.commentService.save(this.comment).subscribe(
        res => {
           this.toastr.success("Comment edited!");
          })
        });
      }
   }