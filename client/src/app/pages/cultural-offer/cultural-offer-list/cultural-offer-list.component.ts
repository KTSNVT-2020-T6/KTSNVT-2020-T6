import { Component, Input, OnInit, Output } from '@angular/core';
import { CulturalOffer } from '../../../core/model/CulturalOffer';
import { SearchDetailsComponent } from '../search-details/search-details.component';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { MatDialog } from '@angular/material/dialog';
import { filter } from 'rxjs/operators';
import { SearchDetails } from '../../../core/model/SearchDetails';
import { CulturalOfferDetailsComponent } from '../cultural-offer-details/cultural-offer-details.component';
import { CulturalOfferDetailsService } from '../../../core/services/cultural-offer-details/cultural-offer-details.service';
import { EventListenerFocusTrapInertStrategy } from '@angular/cdk/a11y';
import { ImageService } from '../../../core/services/image/image.service';
import { Img } from '../../../core/model/Image';
import { DomSanitizer } from '@angular/platform-browser';
import { EventEmitter } from '@angular/core';


@Component({
  selector: 'app-cultural-offer-list',
  templateUrl: './cultural-offer-list.component.html',
  styleUrls: ['./cultural-offer-list.component.scss']
})
export class CulturalOfferListComponent implements OnInit {
  @Input() culturalOfferList!: CulturalOffer[];

  constructor( private fb: FormBuilder,
               private coService: CulturalOfferDetailsService,
               private route: ActivatedRoute,
               private router: Router,
               private imageService: ImageService,
               private toastr: ToastrService,
               private sanitizer: DomSanitizer) { }

  ngOnInit(): void {
  }

}
