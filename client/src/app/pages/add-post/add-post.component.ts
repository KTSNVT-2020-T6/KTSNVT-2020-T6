import { NONE_TYPE } from '@angular/compiler';
import { Component, Inject, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { CulturalOffer } from '../model/CulturalOffer';
import { Post } from '../model/Post';
import { Img } from '../model/Image';
import { CulturalOfferDetailsService } from '../services/cultural-offer-details/cultural-offer-details.service';
import { ImageService } from '../services/image/image.service';
import { PostService } from '../services/post/post.service';

@Component({
  selector: 'app-add-post',
  templateUrl: './add-post.component.html',
  styleUrls: ['./add-post.component.scss']
})
export class AddPostComponent implements OnInit {
/*
  @Input()
  coId!: number;
*/
// @Inject(MAT_DIALOG_DATA) public coId: any;
  culturalOfferId: any = '';
  post: Post = {"text" : "",  "culturalOfferId": 1, "imageDTO": {"id" : 1}};
  postForm!: FormGroup;
  co!: CulturalOffer;

  constructor(
    private fb: FormBuilder,
    private postService: PostService,
    private coService: CulturalOfferDetailsService,
    private imageService: ImageService,
    private route : ActivatedRoute,
    private router : Router,
    private toastr: ToastrService
  ) { 
    this.createForm();
  }

  ngOnInit(): void {
    this.coService.getOne(this.culturalOfferId).subscribe(
      res => {
        this.co = res.body as CulturalOffer;
        this.post.culturalOfferId = this.co.id;
      }
    );
  }
  createForm() {
    this.postForm = this.fb.group({
      'text': [''],
      'date':['']
       });
  }
  addPost(){
    this.post.text = this.postForm.controls['text'].value;
    this.post.date = this.postForm.controls['date'].value;
    this.post.culturalOfferId = this.co.id;
    this.imageService.getImage(1).subscribe(
      res => {
        this.post.imageDTO = res.body as Img;
        console.log(this.post.imageDTO.id);
    }
  );
    
    this.postService.addPost(this.post as Post).subscribe(
      result => {
        this.toastr.success(result);
        this.router.navigate(['home']);
      }
    );
    this.postForm.reset();
  }

}
