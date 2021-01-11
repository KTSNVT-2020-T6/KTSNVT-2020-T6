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
  selected : any;
  typeId: any;
  type!:Type;
  categories: Category[] = [];
  category!: Category | undefined;
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
    this.typeService.getType(this.typeId).subscribe(
      res => {
        this.type = res.body as Type;
        this.category = this.type.categoryDTO;
        this.selected = this.category;
        console.log(this.type.id);
        this.typeForm = this.fb.group({
          'name': [this.type.name],
          'description': [this.type.description],
           });
          
      }
    ) 
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
    this.typeService.update(this.type as Type, this.typeId).subscribe(
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

    cancelClicked(){
      this.typeForm = this.fb.group({
        'name': [this.type.name],
        'description': [this.type.description],
         });
        this.category = this.type.categoryDTO;
  
    }

}
