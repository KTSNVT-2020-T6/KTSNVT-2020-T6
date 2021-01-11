import { Component, OnInit } from '@angular/core';
import {Category} from '../model/Category';
import {Type} from '../model/Type';
import {TypeService} from '../services/type/type.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { CategoryService } from '../services/category/category.service';

@Component({
  selector: 'app-add-type',
  templateUrl: './add-type.component.html',
  styleUrls: ['./add-type.component.scss']
})
export class AddTypeComponent implements OnInit {
 
  categories: Category[] = [];
  type!: Type;
  category!: Category;
  typeForm!: FormGroup;

  constructor(
		private fb: FormBuilder,
		private router: Router,
		private typeService: TypeService,
		private categoryService: CategoryService,
		private route: ActivatedRoute,
		private toastr: ToastrService
	) {
	this.createForm();
  }
  ngOnInit() {
    this.categoryService.getAll().subscribe(
		res => {
			this.categories = res.body as Category[];
		}
    );
  }

  createForm() {
	this.typeForm = this.fb.group({
		'name': [''],
		'description': ['']
     });
	}
  onSelection(event:any) {
	this.category = event;
  }
  
  addType(){
	this.type = this.typeForm.value;
	this.type.categoryDTO = this.category;
	this.typeService.add(this.type as Type).subscribe(
		result => {
			this.toastr.success(result);
			this.router.navigate(['home']);
		},
		error => {
			this.toastr.error("Name already exists!");
      }
	);
	this.typeForm.reset();
  }
}
