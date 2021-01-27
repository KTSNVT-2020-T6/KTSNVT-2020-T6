import { Component, OnInit } from '@angular/core';
import { Category } from '../../../core/model/Category';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { CategoryService} from '../../../core/services/category/category.service';
import { MatDialog , MatDialogRef} from '@angular/material/dialog';

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

  ngOnInit(): void {
    this.category = {id: 0, description: '', name: ''};
  }
  createForm(): void {
    this.categoryForm = this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required]
       });
  }

  addCategory(): void {
    this.category = this.categoryForm.value;
    if (this.categoryForm.invalid)
    {
      return;
    }
    else{
      this.categoryService.add(this.category as Category).subscribe(
        result => {
          this.toastr.success('Category successfully added');
          this.categoryForm.reset();
          this.dialogRef.close();
          this.windowReload();
        },
        error => {
          this.toastr.error('Name already exists!');
        }
      );

    }

  }
  windowReload(): void{
    window.location.reload();
  }

}
