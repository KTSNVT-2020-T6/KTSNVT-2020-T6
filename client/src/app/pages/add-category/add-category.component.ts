import { Component, OnInit } from '@angular/core';
import { Category } from '../model/Category';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { CategoryService} from '../services/category/category.service';
import { MatDialog ,MatDialogRef} from '@angular/material/dialog';

@Component({
  selector: 'app-add-category',
  templateUrl: './add-category.component.html',
  styleUrls: ['./add-category.component.scss']
})
export class AddCategoryComponent implements OnInit {

  category!: Category;
  categoryForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
		private categoryService: CategoryService,
    private toastr: ToastrService,
    public dialogRef: MatDialogRef<AddCategoryComponent>    
  ) {
    this.createForm();
  }

  ngOnInit() {
    this.category = {id:0,description:'',name:''};
  }
  createForm() {
    this.categoryForm = this.fb.group({
      'name': ['',Validators.required],
      'description': ['',Validators.required]
       });
  }
  
  addCategory() {
    this.category = this.categoryForm.value;
    if(this.category.name === '' || this.category.name === null )
    {
      return;
    }
    else if(this.category.description === '' || this.category.description === null ){
      return;
    }
    else{
      this.categoryService.add(this.category as Category).subscribe(
        result => {
          this.dialogRef.close();
          this.toastr.success("Category successfully added");
          window.location.reload();
        },
        error => {
          this.toastr.error("Name already exists!");
        }
      );
      this.categoryForm.reset();
    }
   
  }
 
}
