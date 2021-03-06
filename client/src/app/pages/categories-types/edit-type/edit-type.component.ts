import { Component, OnInit, Input} from '@angular/core';
import { Category } from '../../../core/model/Category';
import { Type} from '../../../core/model/Type';
import { TypeService } from '../../../core/services/type/type.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { CategoryService } from '../../../core/services/category/category.service';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-edit-type',
  templateUrl: './edit-type.component.html',
  styleUrls: ['./edit-type.component.scss']
})
export class EditTypeComponent implements OnInit {

  selected: any;
  typeId: any;
  type!: Type;
  categories: Category[] = [];
  category!: Category | undefined;
  typeForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private typeService: TypeService,
    private categoryService: CategoryService,
    private toastr: ToastrService,
    public dialogRef: MatDialogRef<EditTypeComponent>
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
          name: [this.type.name],
          description: [this.type.description],
           });

      }
    );
    this.categoryService.getAll().subscribe(
      res => {
        this.categories = res.body as Category[];
      }
    );
  }
  createForm(): void{
    this.typeForm = this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required]
       });
    }
    onSelection(event: any): void{
    this.category = event;
    }

    editType(): void{
    this.type = this.typeForm.value;
    this.type.categoryDTO = this.category;
    this.typeService.update(this.type as Type, this.typeId).subscribe(
      result => {
        this.toastr.success('Succesfully edited!');
        this.dialogRef.close();
        this.windowReload();
      },
      error => {
        this.dialogRef.close();
        this.toastr.error('Cannot edit type!');
      }
    );
    this.typeForm.reset();
    }

    cancelClicked(): void{
      this.dialogRef.close();

    }

    windowReload(): void{
      window.location.reload();
    }

}
