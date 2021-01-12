import { Component, OnInit } from '@angular/core';
import { CulturalOffer } from '../model/CulturalOffer';
import { Img } from '../model/Image';
import { CulturalOfferDetailsService } from '../services/cultural-offer-details/cultural-offer-details.service';
import { ImageService } from '../services/image/image.service';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-favorite',
  templateUrl: './favorite.component.html',
  styleUrls: ['./favorite.component.scss']
})
export class FavoriteComponent implements OnInit {
  culturalOffers!: CulturalOffer[];
  images!: Img[];
  image!: any;
  base64image: any;
  
  constructor(private culturalOfferDetailsService: CulturalOfferDetailsService,
    private imageService: ImageService, private sanitizer: DomSanitizer) {
   
  }
 
  ngOnInit() {
    this.culturalOfferDetailsService.getFavorite().subscribe(
			res => {
        this.culturalOffers = res.body.content as CulturalOffer[];
        this.culturalOffers.forEach(element => {
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
        console.log(error.error);
        
      }
    );
    
  }
  unsubscribe(id:any){

  }

}
