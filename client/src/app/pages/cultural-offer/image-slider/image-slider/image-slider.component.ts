import { Component, OnInit,Input, OnChanges,SimpleChanges } from '@angular/core';
import { CulturalOffer } from '../../../../core/model/CulturalOffer';
import { Img } from '../../../../core/model/Image';
import { ImageService } from '../../../../core/services/image/image.service';
import { DomSanitizer } from '@angular/platform-browser';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-image-slider',
  templateUrl: './image-slider.component.html',
  styleUrls: ['./image-slider.component.scss']
})
export class ImageSliderComponent implements OnInit, OnChanges {
  @Input() culturalOffer!:CulturalOffer;
  @Input() imageDTO!: Img[];
  images!: any[];
  slides:any[] = [];
  
  constructor(private imageService:ImageService,
     private sanitizer: DomSanitizer,
     private toastr: ToastrService) {
  }
  ngOnChanges(changes: SimpleChanges) {
    this.slides = [];
    this.images = changes.imageDTO.currentValue;
    if(this.images !== undefined){
      this.images.forEach(element => {
        this.imageService.getImage(element.id).subscribe(
          res => {
            let base64String = btoa(String.fromCharCode(...new Uint8Array(res.body)));
            let objectURL = 'data:image/jpg;base64,' + base64String;           
            this.slides.push(this.sanitizer.bypassSecurityTrustUrl(objectURL));
    
          }, error => {
            this.toastr.error("The data is not valid!");
          });
      });
    }
    
    
  }
  ngOnInit() { 
    
  }
  
}
