import { Component, OnInit, Output } from '@angular/core';
import { CulturalOffer } from '../../model/CulturalOffer';
import { Img } from '../../model/Image';
import { CulturalOfferDetailsService } from '../../services/cultural-offer-details/cultural-offer-details.service';
import { ImageService } from '../../services/image/image.service';
import { DomSanitizer } from '@angular/platform-browser';
import { SearchDetails } from '../../model/SearchDetails';
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
  searchDetails: SearchDetails={'city': '', 'content': ''};
  filtered:boolean = false;

  constructor(private culturalOfferDetailsService: CulturalOfferDetailsService,
    private imageService: ImageService, private sanitizer: DomSanitizer,  public dialog: MatDialog) {
    this.pageSize = 3;
		this.currentPage = 1;
		this.totalSize = 1;
  }

  changePageFiltered(newPage: number){
    this.culturalOfferDetailsService.searchCombined(newPage-1, this.pageSize, this.searchDetails.content, this.searchDetails.city).subscribe(
			res => {
				this.culturalOfferList = res.body.content as CulturalOffer[];
        this.totalSize = Number(res.body.totalElements);
        this.loadImage();
        }, error => {
          console.log(error.error);
          
        }
      );
  }
  changePage(newPage: number) {
    if(this.filtered){
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

  ngOnInit() {
    this.culturalOfferDetailsService.getPage(this.currentPage - 1, this.pageSize).subscribe(
			res => {
        this.culturalOfferList = res.body.content as CulturalOffer[];
        this.totalSize = Number(res.body.totalElements);
      
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
        console.log(error.error);
        
      }
    );
    
  }
  loadImage(){
    this.culturalOfferList.forEach(element => {
      this.images = element.imageDTO as Img[];
      if(this.images.length == 0){
        return;
      }
      this.imageService.getImage(this.images[0]?.id).subscribe(
        res => {
          let base64String = btoa(String.fromCharCode(...new Uint8Array(res.body)));
          let objectURL = 'data:image/jpg;base64,' + base64String;           
          this.base64image  = this.sanitizer.bypassSecurityTrustUrl(objectURL); 
          element.base64image =  this.base64image;            
        }, error => {
          console.log(error.error);
        });
     });
  }
  refreshClick(){
    this.filtered = false;
    window.location.reload();
  }
  searchClicked(){
    this.currentPage = 1;
    const dialogRef = this.dialog.open(SearchDetailsComponent);
    const sub = dialogRef.componentInstance.done.subscribe(() => {
      this.searchDetails = dialogRef.componentInstance.searchDetails;
    });
    dialogRef.afterClosed().subscribe(() => {
      sub.unsubscribe();

      this.culturalOfferDetailsService.searchCombined(this.currentPage-1, this.pageSize, this.searchDetails.content, this.searchDetails.city).subscribe(
        res => {
          this.culturalOfferList = res.body.content as CulturalOffer[];
          this.totalSize = Number(res.body.totalElements);
          this.loadImage();
          this.filtered = true;
        }, error => {
          console.log(error.error);
          
        }
      )}
    )
  }
}
      
