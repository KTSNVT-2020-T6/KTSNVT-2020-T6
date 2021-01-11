import { Component, OnInit } from '@angular/core';
import {Category} from '../model/Category';
import { Type } from '../model/Type';
import { Img } from '../model/Image';
import { CulturalOffer } from '../model/CulturalOffer';
import { FormGroup,FormControl, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { CulturalOfferDetailsService} from '../services/cultural-offer-details/cultural-offer-details.service';
import { CategoryService } from '../services/category/category.service';
import { TypeService } from '../services/type/type.service';
import { MatDialog ,MatDialogRef} from '@angular/material/dialog';
@Component({
  selector: 'app-add-cultural-offer',
  templateUrl: './add-cultural-offer.component.html',
  styleUrls: ['./add-cultural-offer.component.scss']
})
export class AddCulturalOfferComponent implements OnInit {

  categories: Category[] = [];
  types: Type[] = [];
  images: Img[] = [];
  culturalOffer!: CulturalOffer;
  coForm!: FormGroup;
  type!: Type;
  selectFormControl = new FormControl('', Validators.required);
  todayDate!: Date;
  constructor(
		private fb: FormBuilder,
		private router: Router,
		private coService: CulturalOfferDetailsService,
		private categoryService: CategoryService,
		private typeService: TypeService,
		private route: ActivatedRoute,
		private toastr: ToastrService,
		public dialogRef: MatDialogRef<AddCulturalOfferComponent>
	) {
	this.createForm();
	this.todayDate = new Date();
  }
  ngOnInit(): void {
	this.categoryService.getAll().subscribe(
		res => {
			this.categories = res.body as Category[];
		}
	);
  }
  createForm() {
	this.coForm = this.fb.group({
		'name': ['',Validators.required],
		'description': ['',Validators.required],
		'city': ['',Validators.required],
		'date':['',Validators.required],
		'lon': [''],
		'lat': [''],
		'typeDTO': ['',Validators.required]
   });
  }
  onSelectionCategory(id:any) {
	this.typeService.getTypesOfCategory(id).subscribe(
		res => {
			this.types = res.body as Type[];
		},
		error => {
		  this.toastr.error("Server error!");
		}
		
	);
  }  
  addCulturalOffer(){
	this.culturalOffer = this.coForm.value;
	if(this.culturalOffer.name === '' || this.culturalOffer.name === null )
    {
      return;
    }
	else if(this.culturalOffer.description === '' || this.culturalOffer.description === null ){
      return;
	}
	else if(this.culturalOffer.typeDTO === null || JSON.stringify(this.culturalOffer.typeDTO) === JSON.stringify("")){
		return;
	  }
	else if(this.culturalOffer.city === '' || this.culturalOffer.city === null ){
	return;
	}
	else if(this.culturalOffer.date === null ){
	return;
	}
    else{
		this.coService.add(this.culturalOffer as CulturalOffer).subscribe(
			result => {
				this.dialogRef.close();
				this.toastr.success("Cultural offer successfully added");
				window.location.reload();
			}
		);
		this.coForm.reset();
		}
	

  }

}
