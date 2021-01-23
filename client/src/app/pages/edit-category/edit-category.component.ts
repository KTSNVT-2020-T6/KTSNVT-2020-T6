import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { ActivatedRoute, Route } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Category } from '../model/Category';
import { CategoryService } from '../services/category/category.service';

@Component({
  selector: 'app-edit-category',
  templateUrl: './edit-category.component.html',
  styleUrls: ['./edit-category.component.scss']
})
export class EditCategoryComponent implements OnInit {
  catId: any;
  categoryForm!: FormGroup;
  category!: Category;

  constructor(
    private fb: FormBuilder,
    private categoryService: CategoryService,
		//private route: ActivatedRoute,
    private toastr: ToastrService,
    public dialogRef: MatDialogRef<EditCategoryComponent>
  ) { 
    this.createForm();

  }

  ngOnInit(): void {
    this.categoryService.getCategory(this.catId).subscribe(
      res => {
        this.category = res.body as Category;
        this.categoryForm = this.fb.group({
          'name': [this.category.name],
          'description': [this.category.description],
           });
      }
    );
  }
  createForm() {
    this.categoryForm = this.fb.group({
      'name': [''],
      'description': ['']
       });
  }
  editCategory(){
    this.category = this.categoryForm.value;
    this.categoryService.update(this.category as Category, this.catId).subscribe(
      result => {
        this.toastr.success("Successful!");
        this.dialogRef.close();
        window.location.reload();
      }, error => {
        this.dialogRef.close()
        this.toastr.error("Cannot edit category!");
      }
    );
    this.categoryForm.reset();
    }
  cancelClicked(){
    this.dialogRef.close();
  }
    
}
