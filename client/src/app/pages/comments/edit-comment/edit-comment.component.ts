import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { ActivatedRoute, Route } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Comment } from '../../../core/model/Comment';
import { CommentService } from '../../../core/services/comment/comment.service';
import { ImageService } from '../../../core/services/image/image.service';

@Component({
  selector: 'app-edit-comment',
  templateUrl: './edit-comment.component.html',
  styleUrls: ['./edit-comment.component.scss']
})
export class EditCommentComponent implements OnInit {
  editForm!: FormGroup;
  commentId!: number;
  imageAdded!: any;
  comment: Comment = { nameSurname : '', text: '', date : new Date(), userId : 1};

  constructor(
    private fb: FormBuilder,
    private commentService: CommentService,
    private route: ActivatedRoute,
    private imageService: ImageService,
    private toastr: ToastrService,
    public dialogRef: MatDialogRef<EditCommentComponent>) {
      this.createForm();
    }
  createForm(): void {
    this.editForm = this.fb.group({
      text: [''],
      image: [''],
     });
  }
  ngOnInit(): void {
     this.commentService.getComment(this.commentId).subscribe(
          res => {
            this.comment = res.body as Comment;
            this.editForm = this.fb.group({
            text: [this.comment.text],
            image: [this.comment.imageDTO],
           });
          }
      );
    }

  onFileSelect(event: any): void {
    if (event.target.files.length > 0) {
       const file = event.target.files[0];
       this.imageAdded = file;
     }
   }

   editComment(): void
   {
      console.log(this.comment.imageDTO?.id);
      this.comment.text = this.editForm.value.text;
      this.comment.date = new Date();
      if (this.imageAdded !== undefined)
      {
      const formData = new FormData();
      formData.append('file', this.imageAdded);
      this.imageService.add(formData).subscribe(
        res => {
          this.toastr.success('Saved!');
          this.comment.imageDTO = {id: res};
          this.commentService.update(this.comment, this.commentId).subscribe(
          response => {
            this.dialogRef.close();
            this.toastr.success('Comment edited!');
            });
          });
      }
      else
      {
        this.commentService.update(this.comment, this.commentId).subscribe(
          res => {
            this.dialogRef.close();
            this.toastr.success('Comment edited!');
          });
      }
    }

    cancelClicked(): void {
      this.dialogRef.close();
    }

}
