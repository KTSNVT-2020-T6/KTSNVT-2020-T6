import { Component, Input, OnInit } from '@angular/core';
import { CulturalOffer } from '../model/CulturalOffer';

@Component({
  selector: 'app-cultural-offer-list',
  templateUrl: './cultural-offer-list.component.html',
  styleUrls: ['./cultural-offer-list.component.scss']
})
export class CulturalOfferListComponent implements OnInit {
@Input() culturalOfferList!: CulturalOffer[];

  constructor() { }

  ngOnInit(): void {
  }

}
