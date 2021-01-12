import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { CulturalOffer } from '../model/CulturalOffer';
import { Img } from '../model/Image';
import { Post } from '../model/Post';
import { CulturalOfferDetailsService } from '../services/cultural-offer-details/cultural-offer-details.service';
import { ImageService } from '../services/image/image.service';
import { PostService } from '../services/post/post.service';

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

  constructor(private postService: PostService,
    private culturalOfferService: CulturalOfferDetailsService,
    private imageService: ImageService,
    private sanitizer: DomSanitizer) {
      this.pageSize = 5;
		  this.currentPage = 0;
		  this.totalSize = 2;
  }

  ngOnInit(): void {
    this.postService.getPage(this.currentPage - 1, this.pageSize).subscribe(
			res => {
        this.posts = res.body.content as Post[];
        this.totalSize = Number(res.headers.get('Total-pages'));
     
        this.posts.forEach(element => {
          this.image = element.imageDTO as Img;
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

            this.culturalOfferService.getOne(element.culturalOfferId).subscribe(
              res => {
                const co = res.body as CulturalOffer;
                element.culturalOfferName = co.name;
              }, error => {
                console.log(error.error);               
              });
       });
      

			}, error => {
        console.log(error.console.error);
        
      }
    );
  }
  
  changePage(newPage: number) {
		this.postService.getPage(newPage - 1, this.pageSize).subscribe(
			res => {
				this.posts = res.body as Post[];
        this.totalSize = Number(res.headers.get('Total-pages'));
			}
		);
	}

}
