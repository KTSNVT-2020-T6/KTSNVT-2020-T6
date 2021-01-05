import { Component, OnInit,Input, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router,ActivatedRoute } from '@angular/router';
import { CulturalOffer } from '../model/CulturalOffer';
import { CulturalOfferDetailsService } from '../services/cultural-offer-details/cultural-offer-details.service';

@Component({
  selector: 'app-cultural-offer-details',
  templateUrl: './cultural-offer-details.component.html',
  styleUrls: ['./cultural-offer-details.component.scss']
})
export class CulturalOfferDetailsComponent implements OnInit {
  culturalOffer!: CulturalOffer;
  id: any = '';
  
  constructor(
    private coService: CulturalOfferDetailsService,
    private route : ActivatedRoute) {
  }

  ngOnInit() {
    this.id = this.route.snapshot.paramMap.get('id');
    this.coService.getOne(this.id).subscribe(
      res => {
        this.culturalOffer = res.body as CulturalOffer;
			}
    );


  }
  

}
