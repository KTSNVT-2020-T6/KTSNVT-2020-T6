import { ComponentFixture, flush, TestBed } from '@angular/core/testing';
import {  Router, RouterModule } from '@angular/router';
import { FormBuilder, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { async,fakeAsync, tick } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import {  Observable, of } from 'rxjs';
import { UserService } from '../services/user/user.service';
import { RegisteredUserService } from '../services/registered-user/registered-user.service';
import { AdminService } from '../services/admin/admin.service';
import { ImageService } from '../services/image/image.service';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { MatDialog, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { AuthenticationService } from '../services/authentication/authentication.service';
import { MatCardModule } from '@angular/material/card';
import { EditProfileComponent } from '../edit-profile/edit-profile.component';
import { ConfirmationComponent } from '../confirmation/confirmation.component';
import { AddCategoryComponent } from './add-category.component';
import { CategoryService } from '../services/category/category.service';
import { HttpClientModule } from '@angular/common/http';
import { MaterialModule } from '../material-module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
export class MatDialogRefMock {
    close(value = '') {

    }
}
class MdDialogMock {
  open() {
    return {
      afterClosed: jasmine.createSpy('afterClosed').and.returnValue(of(true))
    };
  }
};
describe('AddCategoryComponent', () => {
  let component: AddCategoryComponent;
  let fixture: ComponentFixture<AddCategoryComponent>;
  let categoryService: any;
  let dialog: MdDialogMock;
  let dialogRef: any;
  let toastr: any;
 beforeEach(() => {
    
    let dialogMock = {
      open: jasmine.createSpy('open').and.callThrough(),
      afterClosed: jasmine.createSpy('afterClosed').and.callThrough(),
    }
    
    let categoryServiceMock = {
      add: jasmine.createSpy('add').and.returnValue(of())
    };
    const toastrMocked = {
      success: jasmine.createSpy('success'),
      error: jasmine.createSpy('error')
    };
    TestBed.configureTestingModule({
       declarations: [ AddCategoryComponent ],
       imports: [ MaterialModule,ReactiveFormsModule,FormsModule,BrowserAnimationsModule,
         MatDialogModule, HttpClientModule, RouterModule, ToastrModule.forRoot(),
         MatCardModule, MatDialogModule],
       providers:    [ 
        { provide: CategoryService, useValue: categoryServiceMock },
        { provide: MatDialogRef, useClass: MatDialogRefMock},
        { provide: ToastrService, useValue: toastrMocked },
        { provide: MatDialog, useClass: MdDialogMock}
       ]
    });

    fixture = TestBed.createComponent(AddCategoryComponent);
    component = fixture.componentInstance;
    categoryService = TestBed.inject(CategoryService);
    dialog = TestBed.get(MatDialog);
    dialogRef = TestBed.inject(MatDialogRef);
    toastr = TestBed.inject(ToastrService);
  });
  it('should create commponent', fakeAsync(() => {
    expect(component).toBeTruthy();
  }));
  it('form invalid when empty', () => {
    expect(component.categoryForm.valid).toBeFalsy();
  });
  it('should map on reactive form for new category', async(() => {
    fixture.detectChanges();  // ngOnInit will be called
    fixture.whenStable().then(() => {
        expect(fixture.debugElement.query(By.css('#catName')).nativeElement.value).toEqual('');
        expect(fixture.debugElement.query(By.css('#catDescription')).nativeElement.value).toEqual('');
       
        let catName = fixture.debugElement.query(By.css('#catName')).nativeElement;
        catName.value = 'Category 1';
        let catDescription = fixture.debugElement.query(By.css('#catDescription')).nativeElement;
        catDescription.value = 'This is the description for category 1';
        // bind data from HTML components to the into the reacive form for category
        catName.dispatchEvent(new Event('input')); 
        catDescription.dispatchEvent(new Event('input'));
        let controlName = component.categoryForm.controls['name'];
        let controlDescription = component.categoryForm.controls['description'];
        // expect that data from HTML components are copied into the reacive form for category 
        expect(controlName.value).toEqual('Category 1');
        expect(controlDescription.value).toEqual('This is the description for category 1');

      });

  }));
  it('should save new category', fakeAsync(() => {
    component.ngOnInit();
    spyOn(dialogRef, 'close');
    expect(component.categoryForm.valid).toBeFalsy();
    component.categoryForm.controls['name'].setValue("Category1");
    component.categoryForm.controls['description'].setValue("Description of Category1");
    expect(component.categoryForm.valid).toBeTruthy();
    component.addCategory(); 

    expect(categoryService.add).toHaveBeenCalledTimes(1);
    //expect(toastr.success).toHaveBeenCalledTimes(1);
    //expect(dialogRef.close).toHaveBeenCalledTimes(1);
    flush();
  }));
  
});