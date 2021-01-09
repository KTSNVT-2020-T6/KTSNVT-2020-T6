import { Component, OnInit } from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {AddTypeComponent} from '../../add-type/add-type.component';
import {AddCulturalOfferComponent} from '../../add-cultural-offer/add-cultural-offer.component';
import {AddCategoryComponent} from '../../add-category/add-category.component';
import {EditTypeComponent} from '../../edit-type/edit-type/edit-type.component';
import { CulturalOffer } from '../../model/CulturalOffer';
import { Img } from '../../model/Image';
import { CulturalOfferDetailsService } from '../../services/cultural-offer-details/cultural-offer-details.service';
import { ImageService } from '../../services/image/image.service';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss']
})
export class HomePageComponent implements OnInit {

  pageSize: number;
	currentPage: number;
	totalSize: number;
  culturalOfferList!: CulturalOffer[];
  images!: Img[];
  image!: any;
  base64image: any;


  constructor(private culturalOfferDetailsService: CulturalOfferDetailsService,
    private imageService: ImageService, private sanitizer: DomSanitizer) {
    this.pageSize = 5;
		this.currentPage = 1;
		this.totalSize = 1;
  }

  changePage(newPage: number) {
		this.culturalOfferDetailsService.getPage(newPage - 1, this.pageSize).subscribe(
			res => {
				this.culturalOfferList = res.body as CulturalOffer[];
				this.totalSize = Number(res.headers.get('Total-pages'));
			}
		);
	}

  ngOnInit() {
    this.culturalOfferDetailsService.getPage(this.currentPage - 1, this.pageSize).subscribe(
			res => {
        this.culturalOfferList = res.body.content as CulturalOffer[];
        this.totalSize = Number(res.headers.get('Total-pages'));

        this.culturalOfferList.forEach(element => {
        this.images = element.imageDTO as Img[];
        if(this.images.length == 0){
          return;
        }
        this.imageService.getImage(this.images[0]?.id).subscribe(
          res => {
            let base64String = btoa(String.fromCharCode(...new Uint8Array(res.body)));
            let objectURL = 'data:image/jpg;base64,' + base64String;           
            this.base64image = this.sanitizer.bypassSecurityTrustUrl(objectURL);
            element.base64image = this.base64image;
    
          }, error => {
            console.log(error.error);
            
          });
       });
      

			}, error => {
        console.log(error.console.error);
        
      }
    );
    
  }


}