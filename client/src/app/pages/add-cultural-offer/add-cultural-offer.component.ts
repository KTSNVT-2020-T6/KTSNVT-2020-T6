import { Component, OnInit } from '@angular/core';
import {Category} from '../model/Category';
import { Type } from '../model/Type';
import { Img } from '../model/Image';
import { CulturalOffer } from '../model/CulturalOffer';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { CulturalOfferDetailsService} from '../services/cultural-offer-details/cultural-offer-details.service';
import { CategoryService } from '../services/category/category.service';
import { TypeService } from '../services/type/type.service';

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

  constructor(
		private fb: FormBuilder,
		private router: Router,
		private coService: CulturalOfferDetailsService,
		private categoryService: CategoryService,
		private typeService: TypeService,
		private route: ActivatedRoute,
		private toastr: ToastrService
	) {
	this.createForm();
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
		'lat': ['']
   });
  }
  onSelectionCategory(id:any) {
	//treba ispisati nesto da se mora odabrati kategorija pre nego sto se odabere tip
	//ako je prazno napisi da ne moze da se odabere ta kategorija jer nema tipova
	this.typeService.getTypesOfCategory(id).subscribe(
		res => {
			this.types = res.body as Type[];
		},
		error => {
		  this.toastr.error("Name of cultural offer already exists!");
		}
		
	);
  }
  
  onSelectionType(event:any) {
	this.type = event;
  }
  
  addCulturalOffer(){
	
	this.culturalOffer = this.coForm.value;
    this.culturalOffer.typeDTO = this.type;
	this.coService.add(this.culturalOffer as CulturalOffer).subscribe(
		result => {
			this.toastr.success(result);
			this.router.navigate(['home']);
		}
	);
	this.coForm.reset();

  }

}
