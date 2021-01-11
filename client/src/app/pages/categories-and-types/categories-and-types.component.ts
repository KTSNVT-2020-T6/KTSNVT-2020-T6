import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { EditCategoryComponent } from '../edit-category/edit-category.component';
import { EditTypeComponent } from '../edit-type/edit-type/edit-type.component';
import { Category } from '../model/Category';
import { Type } from '../model/Type';
import { CategoryService } from '../services/category/category.service';
import { TypeService } from '../services/type/type.service';



@Component({
  selector: 'app-categories-and-types',
  templateUrl: './categories-and-types.component.html',
  styleUrls: ['./categories-and-types.component.css']
})
export class CategoriesAndTypesComponent implements OnInit {

  categories!: Array<Category>;
  types!: Array<Type>;
  res = [] as any;

  constructor(
    public dialog: MatDialog,
    private catService: CategoryService,
    private typeService: TypeService,
    private route : ActivatedRoute,
    private router : Router,
    private toastr: ToastrService
  ) { }

  ngOnInit(): void {
    this.catService.getAll().subscribe(
      res => {
        this.categories = res.body as Array<Category>;
        this.categories.forEach(element => {
          this.getTypes(element.id);
          
        });
      }
    )
  }

  getTypes(catId: number){
    this.typeService.getTypesOfCategory(catId).subscribe(
      res => {
        this.res[catId] = res.body ;
      }
    )
  }
  editType(typeId: number){
    const dialogRef = this.dialog.open(EditTypeComponent);
    dialogRef.componentInstance.typeId = typeId;
    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });
  }
  deleteType(typeId: number){

  }

  editCategory(catId: number){
    const dialogRef2 = this.dialog.open(EditCategoryComponent);
    dialogRef2.componentInstance.catId = catId;
    dialogRef2.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });
  }

  deleteCategory(catId: number){
    
  }
}
