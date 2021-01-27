import { Component, OnInit, Output } from '@angular/core';
import { CulturalOffer } from '../../../../core/model/CulturalOffer';
import { Img } from '../../../../core/model/Image';
import { CulturalOfferDetailsService } from '../../../../core/services/cultural-offer-details/cultural-offer-details.service';
import { ImageService } from '../../../../core/services/image/image.service';
import { DomSanitizer } from '@angular/platform-browser';
import { SearchDetails } from '../../../../core/model/SearchDetails';
import { MatDialog } from '@angular/material/dialog';
import { SearchDetailsComponent } from '../../search-details/search-details.component';

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
  content!: string;
  searchDetails: SearchDetails = {city: '', content: ''};
  filtered = false;

  constructor(private culturalOfferDetailsService: CulturalOfferDetailsService,
              private imageService: ImageService, private sanitizer: DomSanitizer,  public dialog: MatDialog) {
    this.pageSize = 3;
    this.currentPage = 1;
    this.totalSize = 1;
  }

  changePageFiltered(newPage: number): void {
    this.culturalOfferDetailsService.searchCombined(
        newPage - 1, this.pageSize, this.searchDetails.content, this.searchDetails.city).subscribe(
            res => {
                this.culturalOfferList = res.body.content as CulturalOffer[];
                this.totalSize = Number(res.body.totalElements);
                this.loadImage();
        }, error => {
          console.log(error.error);

        }
      );
  }
  changePage(newPage: number): void {
    if (this.filtered){
      this.changePageFiltered(newPage);
    }else{
      this.culturalOfferDetailsService.getPage(newPage - 1, this.pageSize).subscribe(
        res => {
          this.culturalOfferList = res.body.content as CulturalOffer[];
          this.totalSize = Number(res.body.totalElements);
          this.loadImage();
          }, error => {
            console.log(error.error);

          }
        );
    }

    }

  ngOnInit(): void {
    this.culturalOfferDetailsService.getPage(this.currentPage - 1, this.pageSize).subscribe(
            res => {
        this.culturalOfferList = res.body.content as CulturalOffer[];
        this.totalSize = Number(res.body.totalElements);

        this.culturalOfferList.forEach(element => {
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
  loadImage(): void {
    this.culturalOfferList.forEach(element => {
      this.images = element.imageDTO as Img[];
      if (this.images.length === 0){
        return;
      }
      this.imageService.getImage(this.images[0]?.id).subscribe(
        res => {
          const base64String = btoa(String.fromCharCode(...new Uint8Array(res.body)));
          const objectURL = 'data:image/jpg;base64,' + base64String;
          this.base64image  = this.sanitizer.bypassSecurityTrustUrl(objectURL);
          element.base64image =  this.base64image;
        }, error => {
          console.log(error.error);
        });
     });
  }
  refreshClick(): void{
    this.filtered = false;
    window.location.reload();
  }
  searchClicked(): void {
    this.currentPage = 1;
    const dialogRef = this.dialog.open(SearchDetailsComponent);
    if (dialogRef.componentInstance === undefined) {
      return;
    }
    const sub = dialogRef.componentInstance.done.subscribe(() => {
      this.searchDetails = dialogRef.componentInstance.searchDetails;
    });
    dialogRef.afterClosed().subscribe(() => {
      sub.unsubscribe();
      this.culturalOfferDetailsService.searchCombined(
          this.currentPage - 1, this.pageSize, this.searchDetails.content, this.searchDetails.city).subscribe(
        res => {
          this.culturalOfferList = res.body.content as CulturalOffer[];
          this.totalSize = Number(res.body.totalElements);
          this.loadImage();
          this.filtered = true;
        }, error => {
          console.log(error.error);
        }
      ); }
    );
  }
}

