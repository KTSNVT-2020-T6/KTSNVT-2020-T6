// import { ComponentFixture, TestBed } from '@angular/core/testing';

// import { EditCategoryComponent } from './edit-category.component';

// describe('EditCategoryComponent', () => {
//   let component: EditCategoryComponent;
//   let fixture: ComponentFixture<EditCategoryComponent>;

//   beforeEach(async () => {
//     await TestBed.configureTestingModule({
//       declarations: [ EditCategoryComponent ]
//     })
//     .compileComponents();
//   });

//   beforeEach(() => {
//     fixture = TestBed.createComponent(EditCategoryComponent);
//     component = fixture.componentInstance;
//     fixture.detectChanges();
//   });

//   it('should create', () => {
//     expect(component).toBeTruthy();
//   });
// });


/*
import { ComponentFixture, flush, TestBed } from '@angular/core/testing';
import {  ActivatedRoute, Router, RouterModule } from '@angular/router';
import { FormBuilder, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { async,fakeAsync, tick } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import {  Observable, of } from 'rxjs';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { MatDialog, MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { EditCategoryComponent } from './edit-category.component';
import { CategoryService } from '../services/category/category.service';
import { Category } from '../model/Category';

export class MatDialogRefMock {
    close(value = '') {

    }
}

describe('EditCategoryComponent', () => {
  let component: EditCategoryComponent;
  let fixture: ComponentFixture<EditCategoryComponent>;
  let categoryService: any;
  let dialogRef: any;
 // let router: any;
  let toastr: any;

  const mockCategory: Category = {
    id: 1,
    name: 'category number 1',
    description: 'this is a category no 1'
  }
  
  //FormBuilder    

 beforeEach(() => {
  /*
    let dialogMock = {
      open: jasmine.createSpy('open').and.callThrough(),
      afterClosed: jasmine.createSpy('afterClosed').and.callThrough(),
    }
    */
    /*
    let categoryServiceMock = {
      update: jasmine.createSpy('update')
        .and.returnValue(of({ subscribe: () => {} })),
      getCategory: jasmine.createSpy('getCategory')
        .and.returnValue(of(mockCategory))
    };

    let routerMock= {
        navigate: jasmine.createSpy('navigate')
    }

    const toastrMocked = {
        success: jasmine.createSpy('success'),
        error: jasmine.createSpy('error')
      };
  
    TestBed.configureTestingModule({
       declarations: [ EditCategoryComponent ],
       imports: [ToastrModule.forRoot(), ReactiveFormsModule, FormsModule, MatDialogModule],
       providers:    [ 
        { provide: CategoryService, useValue: categoryServiceMock },
        { provide: MatDialogRef, useClass: MatDialogRefMock},
      //  { provide: ActivatedRoute, useValue: routerMock },
        { provide: ToastrService, useValue: toastrMocked }
       // { provide: MatDialog, useClass: MatDialogRefMock},
       ]
    });

    fixture = TestBed.createComponent(EditCategoryComponent);
    component = fixture.componentInstance;
    categoryService = TestBed.inject(CategoryService);
    //router = TestBed.inject(Router);
    toastr = TestBed.inject(ToastrService);
   // dialogRef = TestBed.inject(MatDialogRef);
   dialogRef = TestBed.inject(MatDialogRef);
  });
  it('should create commponent', fakeAsync(() => {
    expect(component).toBeTruthy();
  }));

  it('should set form values on init', async(() => {
    component.ngOnInit();
    expect(categoryService.getCategory).toHaveBeenCalled();
    component.category = mockCategory;
    fixture.whenStable()
      .then(() => {
        expect(component.categoryForm).toBeDefined();
        console.log(component.categoryForm.value);
        expect(component.categoryForm.value.name).toEqual('category number 1'); 
        expect(component.categoryForm.value.description).toEqual('this is a category no 1');
        fixture.detectChanges();        
        let nameInput = fixture.debugElement.query(By.css('#catEditName')).nativeElement;
        let descInput = fixture.debugElement.query(By.css('#catEdtiDescription')).nativeElement;
        
        expect(nameInput.value).toEqual('category number 1');
        expect(descInput.value).toEqual('this is a category no 1');
      });
  }));
/*
  it('form invalid when empty', () => {
    expect(component.categoryForm.valid).toBeFalsy();
  });
  */

  /*
    it('should close dialog on cancel', async(() => {
        spyOn(dialogRef, 'close');
        component.cancelClicked();
        expect(dialogRef.close).toHaveBeenCalled();   
  }));

  it('should map on reactive form for edit category', async(() => {
    fixture.detectChanges();  // ngOnInit will be called
    fixture.whenStable().then(() => {
        expect(fixture.debugElement.query(By.css('#catEditName')).nativeElement.value).toEqual('');
        expect(fixture.debugElement.query(By.css('#catEdtiDescription')).nativeElement.value).toEqual('');
       
        let catName = fixture.debugElement.query(By.css('#catEditName')).nativeElement;
        catName.value = 'Category 1';
        let catDescription = fixture.debugElement.query(By.css('#catEdtiDescription')).nativeElement;
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
  it('should save updates', fakeAsync(() => { 
    spyOn(dialogRef, 'close'); 
    component.editCategory();
    tick(15000);

    expect(categoryService.update).toHaveBeenCalled(); 
    expect(toastr.success).toHaveBeenCalled();
    expect(dialogRef.close).toHaveBeenCalled();
    expect(component.categoryForm.invalid).toBeTruthy();
 
}));
  
});
*/
