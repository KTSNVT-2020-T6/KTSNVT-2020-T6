import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
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
    console.log("editovacu type");
  }
  deleteType(typeId: number){

  }

  editCategory(catId: number){

  }

  deleteCategory(catId: number){
    
  }
}
