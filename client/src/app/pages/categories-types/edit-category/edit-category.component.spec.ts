
import { async, ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { By } from '@angular/platform-browser';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { of } from 'rxjs';
import { Category } from '../../../core/model/Category';
import { CategoryService } from '../../../core/services/category/category.service';
import { VerificationPageComponent } from '../../auth/verification-page/verification-page.component';

import { EditCategoryComponent } from './edit-category.component';

export class MatDialogRefMock {
  close(value = '') {
  }
}

describe('EditCategoryComponent', () => {
  let component: EditCategoryComponent;
  let fixture: ComponentFixture<EditCategoryComponent>;
  let toastr: any;
  let dialogRef: any;
  let categoryService: any;

  const mockCategory: Category = {
    id: 1,
    name: 'category number 1',
    description: 'this is a category no 1'
  }


  beforeEach(() => {

    const toastrMocked = {
      success: jasmine.createSpy('success'),
      error: jasmine.createSpy('error')
    };

    let categoryServiceMock = {
      getCategory: jasmine.createSpy('getCategory')
         .and.returnValue( of({ body:mockCategory})),
      update: jasmine.createSpy('update')
        .and.returnValue(of({body: {}}))
    };
    TestBed.configureTestingModule({
      declarations: [ EditCategoryComponent ],
      imports: [ToastrModule.forRoot(), ReactiveFormsModule, FormsModule, MatDialogModule],
      providers:    [ {provide: CategoryService, useValue: categoryServiceMock },
                      { provide: MatDialogRef, useClass: MatDialogRefMock},
                      { provide: ToastrService, useValue: toastrMocked }
                     ]
      });
    fixture = TestBed.createComponent(EditCategoryComponent);
    component = fixture.componentInstance;
    categoryService = TestBed.inject(CategoryService);
    toastr = TestBed.inject(ToastrService);
    dialogRef = TestBed.inject(MatDialogRef);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should set form values on init', async(() => {
    component.ngOnInit();
    expect(categoryService.getCategory).toHaveBeenCalled();
    fixture.whenStable().then(() => {
        let catEditName = fixture.debugElement.query(By.css('#catEditName')).nativeElement;
        catEditName.value = mockCategory.name;
        let catEdtiDescription = fixture.debugElement.query(By.css('#catEdtiDescription')).nativeElement;
        catEdtiDescription.value = mockCategory.description;

        catEditName.dispatchEvent(new Event('input')); 
        catEdtiDescription.dispatchEvent(new Event('input'));

        let controlName = component.categoryForm.controls['name'];
        let controlDesc = component.categoryForm.controls['description'];
       
        expect(controlName.value).toEqual('category number 1');
        expect(controlDesc.value).toEqual('this is a category no 1');
      });   
  }));

  it('should close dialog on cancel', async(() => {
    spyOn(dialogRef, 'close');
    component.cancelClicked();
    expect(dialogRef.close).toHaveBeenCalled();   
  }));

  it('should set input in reactive form', fakeAsync(() => {
    fixture.detectChanges(); 
    
    fixture.whenStable().then(() => {

      expect(fixture.debugElement.query(By.css('#catEditName')).nativeElement.value).toEqual('category number 1');
      expect(fixture.debugElement.query(By.css('#catEdtiDescription')).nativeElement.value).toEqual('this is a category no 1');
      
        let catEditName = fixture.debugElement.query(By.css('#catEditName')).nativeElement;
        catEditName.value = 'new name';
        let catEdtiDescription = fixture.debugElement.query(By.css('#catEdtiDescription')).nativeElement;
        catEdtiDescription.value = 'new desc';

        catEditName.dispatchEvent(new Event('input')); 
        catEdtiDescription.dispatchEvent(new Event('input'));

        let controlName = component.categoryForm.controls['name'];
        let controlDesc = component.categoryForm.controls['description'];
       
        expect(controlName.value).toEqual('new name');
        expect(controlDesc.value).toEqual('new desc');
      });
      expect(true).toEqual(true);   
  }));

  it('should save edited category', fakeAsync(() =>{
  
    spyOn(dialogRef, 'close'); 
    spyOn(component, "windowReload").and.callFake(function(){});
    component.editCategory();
    tick(15000);

    expect(categoryService.update).toHaveBeenCalled(); 
    expect(toastr.success).toHaveBeenCalled();
    expect(dialogRef.close).toHaveBeenCalled();
    expect(component.windowReload).toHaveBeenCalled();
    
  }));

it('should not save updates if name input is empty', fakeAsync(() => { 
  spyOn(dialogRef, 'close');
  component.categoryForm.controls['name'].setValue("");
  spyOn(component, "windowReload").and.callFake(function(){});
  fixture.detectChanges();
  component.editCategory();

  expect(categoryService.update).not.toHaveBeenCalled();
  expect(dialogRef.close).not.toHaveBeenCalled();
  expect(component.windowReload).not.toHaveBeenCalled();

}));

});