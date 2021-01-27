import { NgModule } from '@angular/core';
import { MaterialModule } from '../material-module';
import { AddTypeComponent } from './add-type/add-type.component';
import { EditTypeComponent } from './edit-type/edit-type.component';
import { AddCategoryComponent } from './add-category/add-category.component';
import { EditCategoryComponent } from './edit-category/edit-category.component';
import { CategoriesAndTypesComponent } from './categories-and-types/categories-and-types.component';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [AddTypeComponent, EditTypeComponent, AddCategoryComponent, EditCategoryComponent, CategoriesAndTypesComponent],
  imports: [ MaterialModule, SharedModule],
  exports: [AddTypeComponent, EditTypeComponent, AddCategoryComponent, EditCategoryComponent, CategoriesAndTypesComponent],
  providers: []
})
export class CategoriesTypesModule { }
