import { ComponentFixture, flush, TestBed, waitForAsync } from '@angular/core/testing';
import {  Router, RouterModule } from '@angular/router';
import { FormBuilder, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { async, fakeAsync, tick } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import {  Observable, of } from 'rxjs';
import { UserService } from '../../../core/services/user/user.service';
import { RegisteredUserService } from '../../../core/services/registered-user/registered-user.service';
import { AdminService } from '../../../core/services/admin/admin.service';
import { ImageService } from '../../../core/services/image/image.service';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { MatDialog, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { AuthenticationService } from '../../../core/services/authentication/authentication.service';
import { MatCardModule } from '@angular/material/card';
import { EditProfileComponent } from '../../user/edit-profile/edit-profile.component';
import { ConfirmationComponent } from '../../shared/confirmation/confirmation.component';
import { AddCategoryComponent } from './add-category.component';
import { CategoryService } from '../../../core/services/category/category.service';
import { HttpClientModule } from '@angular/common/http';
import { MaterialModule } from '../../material-module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
export class MatDialogRefMock {
    close(value = ''): void {

    }
}
class MdDialogMock {
  open(): any {
    return {
      afterClosed: jasmine.createSpy('afterClosed').and.returnValue(of(true))
    };
  }
}
describe('AddCategoryComponent', () => {
  let component: AddCategoryComponent;
  let fixture: ComponentFixture<AddCategoryComponent>;
  let categoryService: any;
  let dialog: MatDialog;
  let dialogRef: any;
  let toastr: any;
  beforeEach(() => {

    const dialogMock = {
      open: jasmine.createSpy('open').and.callThrough(),
      afterClosed: jasmine.createSpy('afterClosed').and.callThrough(),
    };

    const categoryServiceMock = {
      add: jasmine.createSpy('add').and.returnValue(of({}))
    };
    const toastrMocked = {
      success: jasmine.createSpy('success'),
      error: jasmine.createSpy('error')
    };
    TestBed.configureTestingModule({
       declarations: [ AddCategoryComponent ],
       imports: [ MaterialModule, ReactiveFormsModule, FormsModule, BrowserAnimationsModule,
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
    dialog = TestBed.inject(MatDialog);
    dialogRef = TestBed.inject(MatDialogRef);
    toastr = TestBed.inject(ToastrService);
  });
  it('should create commponent', fakeAsync(() => {
    expect(component).toBeTruthy();
  }));
  it('form invalid when empty', () => {
    expect(component.categoryForm.valid).toBeFalsy();
  });
  it('should map on reactive form for new category', waitForAsync(() => {
    fixture.detectChanges();  // ngOnInit will be called
    fixture.whenStable().then(() => {
        expect(fixture.debugElement.query(By.css('#catName')).nativeElement.value).toEqual('');
        expect(fixture.debugElement.query(By.css('#catDescription')).nativeElement.value).toEqual('');

        const catName = fixture.debugElement.query(By.css('#catName')).nativeElement;
        catName.value = 'Category 1';
        const catDescription = fixture.debugElement.query(By.css('#catDescription')).nativeElement;
        catDescription.value = 'This is the description for category 1';
        // bind data from HTML components to the into the reacive form for category
        catName.dispatchEvent(new Event('input'));
        catDescription.dispatchEvent(new Event('input'));
        const controlName = component.categoryForm.controls.name;
        const controlDescription = component.categoryForm.controls.description;
        // expect that data from HTML components are copied into the reacive form for category
        expect(controlName.value).toEqual('Category 1');
        expect(controlDescription.value).toEqual('This is the description for category 1');

      });

  }));
  it('should save new category', fakeAsync(() => {
    component.ngOnInit();
    spyOn(dialogRef, 'close');
    spyOn(component, 'windowReload').and.callFake(() => {});


    expect(component.categoryForm.valid).toBeFalsy();
    component.categoryForm.controls.name.setValue('Category1');
    component.categoryForm.controls.description.setValue('Description of Category1');
    expect(component.categoryForm.valid).toBeTruthy();
    component.addCategory();
    tick(15000);
    expect(categoryService.add).toHaveBeenCalledTimes(1);
    expect(toastr.success).toHaveBeenCalledTimes(1);
    expect(dialogRef.close).toHaveBeenCalledTimes(1);
    expect(component.windowReload).toHaveBeenCalled();
    flush();
  }));

});
