import { Component, OnInit } from '@angular/core';
import {Category} from '../model/Category';
import { Type } from '../model/Type';
import { Image } from '../model/Image';
import { CulturalOffer } from '../model/CulturalOffer';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { CulturalOfferDetailsService} from '../services/cultural-offer-details/cultural-offer-details.service';


@Component({
  selector: 'app-add-cultural-offer',
  templateUrl: './add-cultural-offer.component.html',
  styleUrls: ['./add-cultural-offer.component.scss']
})
export class AddCulturalOfferComponent implements OnInit {

  categories: Category[] = [];
  types: Type[] = [];
  images: Image[] = [];
  culturalOffer!: CulturalOffer;
  coForm!: FormGroup;

  constructor(
			private fb: FormBuilder,
			private router: Router,
			private coService: CulturalOfferDetailsService,
			private route: ActivatedRoute,
			private toastr: ToastrService
		) {
		this.createForm();
  }
  ngOnInit(): void {
  }
  createForm() {
		this.coForm = this.fb.group({
			'name': [''],
			'description': [''],
			'city': [''],
			'lon': [''],
			'lat': [''],
   });
  }
  onSelectionCategory(event:any) {
	
  }
  
  onSelectionType(event:any) {
	
  }
  
  addCulturalOffer():void{
    
		// this.type = this.typeForm.value;
		// this.typeService.add(this.type as Type).subscribe(
		// 	result => {
		// 		this.toastr.success(result);
		// 		this.router.navigate(['home']);
		// 	}
		// );
		// this.typeForm.reset();
		// //this.router.navigate(['home']);
  }

}
