import { Component, OnInit,Input} from '@angular/core';
import { Category } from '../../model/Category';
import { Type} from '../../model/Type';
import { TypeService } from '../../services/type/type.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { CategoryService } from '../../services/category/category.service';

@Component({
  selector: 'app-edit-type',
  templateUrl: './edit-type.component.html',
  styleUrls: ['./edit-type.component.scss']
})
export class EditTypeComponent implements OnInit {
  //kad se namesti parent komponenta tek se tada moze istestirati 
 // @Input() type!:Type;
  type!:Type;
  categories: Category[] = [];
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
  ngOnInit(): void {
    //this.type = ; 
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
    
    editType(){
    this.type = this.typeForm.value;
    this.type.categoryDTO = this.category;
    this.typeService.update(this.type as Type).subscribe(
      result => {
        this.toastr.success(result);
        this.router.navigate(['home']);
      },
      error => {
        this.toastr.error(error.console.error);
      }
    );
    this.typeForm.reset();
    }

}
