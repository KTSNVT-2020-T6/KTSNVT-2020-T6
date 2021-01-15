import { Component, OnInit } from '@angular/core';
import {Category} from '../model/Category';
import {Type} from '../model/Type';
import {TypeService} from '../services/type/type.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { CategoryService } from '../services/category/category.service';
import { MatDialog ,MatDialogRef} from '@angular/material/dialog';

@Component({
  selector: 'app-add-type',
  templateUrl: './add-type.component.html',
  styleUrls: ['./add-type.component.scss']
})
export class AddTypeComponent implements OnInit {
 
  categories: Category[] = [];
  type!: Type;
  typeForm!: FormGroup;

  constructor(
		private fb: FormBuilder,
		private router: Router,
		private typeService: TypeService,
		private categoryService: CategoryService,
		private route: ActivatedRoute,
		private toastr: ToastrService,
		public dialogRef: MatDialogRef<AddTypeComponent>
	) {
	this.createForm();
  }
  ngOnInit() {
    this.categoryService.getAll().subscribe(
		res => {
			this.categories = res.body as Category[];
		}, error => {
			this.toastr.error("There's no categories on server!");
		  }
    );
  }

  createForm() {
	this.typeForm = this.fb.group({
		'name': ['',Validators.required],
		'description': ['',Validators.required],
		'categoryDTO': ['', Validators.required]
     });
  }

  addType(){
	this.type = this.typeForm.value;
	//this.type.categoryDTO = this.category
	if(this.type.name === '' || this.type.name === null )
    {
      return;
    }
    else if(this.type.description === '' || this.type.description === null ){
      return;
	}
	else if(this.type.categoryDTO === null || JSON.stringify(this.type.categoryDTO) === JSON.stringify("")){
		return;
	  }
    else{
		this.typeService.add(this.type as Type).subscribe(
			result => {
				this.dialogRef.close();
				this.toastr.success("Type successfully added");
				window.location.reload();
			},
			error => {
				this.toastr.error("Name already exists!");
		}
		);
		this.typeForm.reset();
	}
  }
}
