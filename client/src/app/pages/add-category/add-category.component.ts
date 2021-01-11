import { Component, OnInit } from '@angular/core';
import { Category } from '../model/Category';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { CategoryService} from '../services/category/category.service';

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
		private router: Router,
		private categoryService: CategoryService,
		private route: ActivatedRoute,
		private toastr: ToastrService
  ) {
    this.createForm();
  }

  ngOnInit() {
  }
  createForm() {
    this.categoryForm = this.fb.group({
      'name': ['',Validators.required],
      'description': ['',Validators.required]
       });
  }
  
  addCategory() {
    this.category = this.categoryForm.value;
    this.categoryService.add(this.category as Category).subscribe(
      result => {
        this.toastr.success(result);
        this.router.navigate(['home']);
      },
      error => {
        this.toastr.error("Name already exists!");
      }
    );
    this.categoryForm.reset();
  }
}
