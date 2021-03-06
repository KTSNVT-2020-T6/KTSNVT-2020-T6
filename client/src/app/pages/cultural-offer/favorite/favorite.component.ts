import { Component, OnInit } from '@angular/core';
import { CulturalOffer } from '../../../core/model/CulturalOffer';
import { Img } from '../../../core/model/Image';
import { CulturalOfferDetailsService } from '../../../core/services/cultural-offer-details/cultural-offer-details.service';
import { ImageService } from '../../../core/services/image/image.service';
import { DomSanitizer } from '@angular/platform-browser';
import { Toast, ToastrService } from 'ngx-toastr';
import { Subject } from 'rxjs';

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
              private imageService: ImageService, private sanitizer: DomSanitizer,
              private toastr: ToastrService) {
  }

  ngOnInit(): void {
    this.images = [];
    this.culturalOffers  = [];
    this.culturalOfferDetailsService.getFavorite().subscribe(
            res => {
        this.culturalOffers = res.body as CulturalOffer[];
        this.culturalOffers.forEach(element => {
        this.images = element.imageDTO as Img[];
        if (this.images.length === 0){
          return;
        }
        this.imageService.getImage(this.images[0]?.id).subscribe(
          response => {
            const base64String = btoa(String.fromCharCode(...new Uint8Array(response.body)));
            const objectURL = 'data:image/jpg;base64,' + base64String;
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

  unsubscribe(co: CulturalOffer): void{
    this.culturalOfferDetailsService.unsubscribe(co.id).subscribe(
      res => {
        this.toastr.success('Unsubscribed from ' + co.name);
        const idx = this.culturalOffers.indexOf(co);
        this.culturalOffers.splice(idx, 1);

      }, error => {
        this.toastr.error('Something went wrong');
        console.log(error);
      }
    );
  }

}
