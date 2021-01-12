import { Component, Input, OnInit } from '@angular/core';
import { CulturalOffer } from '../model/CulturalOffer';
import { SearchDetailsComponent } from '../search-details/search-details.component';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { MatDialog } from '@angular/material/dialog';
import { filter } from 'rxjs/operators';
import { SearchDetails } from '../model/SearchDetails';
import { CulturalOfferDetailsComponent } from '../cultural-offer-details/cultural-offer-details.component';
import { CulturalOfferDetailsService } from '../services/cultural-offer-details/cultural-offer-details.service';
import { EventListenerFocusTrapInertStrategy } from '@angular/cdk/a11y';

@Component({
  selector: 'app-cultural-offer-list',
  templateUrl: './cultural-offer-list.component.html',
  styleUrls: ['./cultural-offer-list.component.scss']
})
export class CulturalOfferListComponent implements OnInit {
@Input() culturalOfferList!: CulturalOffer[];
  content!: string;
  searchDetails: SearchDetails={'city': '', 'content': ''};

  constructor( private fb: FormBuilder,
    private coService: CulturalOfferDetailsService,
    public dialog: MatDialog,
    private route : ActivatedRoute,
    private router: Router,
		private toastr: ToastrService) { }

  ngOnInit(): void {
  }

 
  searchClicked(){
    const dialogRef = this.dialog.open(SearchDetailsComponent);
    const sub = dialogRef.componentInstance.done.subscribe(() => {
      this.searchDetails = dialogRef.componentInstance.searchDetails;
    });
    dialogRef.afterClosed().subscribe(() => {
      sub.unsubscribe();
      if(this.searchDetails.city === '')
      {
          this.coService.searchContent(this.searchDetails.content).subscribe(
            res => {
              this.culturalOfferList = res.body as CulturalOffer[];
            }
          );
      }
      else if(this.searchDetails.content === '')
      {
        this.coService.searchCity(this.searchDetails.city).subscribe(
          res => {
            this.culturalOfferList = res.body as CulturalOffer[];
          }
        );
      }
      else if(this.searchDetails.city !== '' && this.searchDetails.content !== ''){
        this.coService.searchCombined(this.searchDetails.content, this.searchDetails.city).subscribe(
          res => {
            this.culturalOfferList = res.body as CulturalOffer[];
          }
        );
      }
    });
  }

}
