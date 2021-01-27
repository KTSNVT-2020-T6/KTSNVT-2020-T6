import { NONE_TYPE } from '@angular/compiler';
import { Component, Inject, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { CulturalOffer } from '../../../core/model/CulturalOffer';
import { Post } from '../../../core/model/Post';
import { Img } from '../../../core/model/Image';
import { CulturalOfferDetailsService } from '../../../core/services/cultural-offer-details/cultural-offer-details.service';
import { ImageService } from '../../../core/services/image/image.service';
import { PostService } from '../../../core/services/post/post.service';

@Component({
  selector: 'app-add-post',
  templateUrl: './add-post.component.html',
  styleUrls: ['./add-post.component.scss']
})
export class AddPostComponent implements OnInit {

  culturalOfferId: any = '';
  post: Post = {};
  postForm!: FormGroup;
  co!: CulturalOffer;
  todayDate!: Date;

  constructor(
    private fb: FormBuilder,
    private postService: PostService,
    private coService: CulturalOfferDetailsService,
    private imageService: ImageService,
    private route: ActivatedRoute,
    private router: Router,
    private toastr: ToastrService,
    public dialogRef: MatDialogRef<AddPostComponent>
  ) {
    this.createForm();
    this.todayDate = new Date();
  }

  ngOnInit(): void {
    this.coService.getOne(this.culturalOfferId).subscribe(
      res => {
        this.co = res.body as CulturalOffer;
        this.post.culturalOfferId = this.co.id;
      }
    );
  }
  createForm(): void {
    this.postForm = this.fb.group({
      text: ['', Validators.required],
      image: ['']
       });
  }
  addPost(): void{
    this.post.text = this.postForm.controls.text.value;
    this.post.date = new Date();
    this.post.culturalOfferId = this.co.id;
    this.postService.addPost(this.post as Post).subscribe(
      result => {
        this.toastr.success('Successfully added post!');
        this.postForm.reset();
        this.dialogRef.close();
      }, error => {
        this.toastr.error('Cannot create post!');
      }
    );

  }
  onFileSelect(event: any): void {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      this.postForm.value.image = file;
    }
    this.saveImage();
  }

  saveImage(): void{
    const formData = new FormData();
    formData.append('file', this.postForm.value.image);

    this.imageService.add(formData).subscribe(
      result => {
        const img: Img = {id: result};
        this.post.imageDTO = img;
      },
      error => {
        this.toastr.error('Error saving image! Choose different one!');
      }

    );
  }
  cancel(): void{
    this.dialogRef.close();
  }

}
