import { Component, OnInit } from '@angular/core';
import {Category} from '../../../core/model/Category';
import {Type} from '../../../core/model/Type';
import {TypeService} from '../../../core/services/type/type.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { CategoryService } from '../../../core/services/category/category.service';
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
		private typeService: TypeService,
		private categoryService: CategoryService,
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

  onSelection(event: any){
	this.typeForm.controls['categoryDTO'].setValue(event);

  }

  addType(){
	this.type = this.typeForm.value;
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
				this.windowReload();
			},
			error => {
				this.toastr.error("Name already exists!");
		}
		);
		this.typeForm.reset();
	}
  }
  windowReload(){
	window.location.reload();
  }
}
