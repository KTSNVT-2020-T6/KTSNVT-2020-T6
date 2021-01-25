import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, flush, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { Router } from '@angular/router';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { of } from 'rxjs';
import {async, fakeAsync, tick} from '@angular/core/testing';

import { CategoryService } from '../../../core/services/category/category.service';
import { TypeService } from '../../../core/services/type/type.service';

import { CategoriesAndTypesComponent } from './categories-and-types.component';
import { By } from '@angular/platform-browser';
import { MaterialModule } from '../../material-module';
class MdDialogMock {
    open() {
      return {
        afterClosed: jasmine.createSpy('afterClosed').and.returnValue(of(true))
      };
    }
  };

describe('CategoriesAndTypesComponent', () => {
  let component: CategoriesAndTypesComponent;
  let fixture: ComponentFixture<CategoriesAndTypesComponent>;
  let dialog: MdDialogMock;
  let router: any;
  let toastr: any;
  let catService: any;
  let typeService: any;


  beforeEach(() => {
    let dialogMock = {
        open: jasmine.createSpy('open').and.callThrough(),
        afterClosed: jasmine.createSpy('afterClosed').and.callThrough(),
    }
    let routerMock = {
        navigate: jasmine.createSpy('navigate')
    };
    const toastrMocked = {
        success: jasmine.createSpy('success'),
        error: jasmine.createSpy('error')
    };
    let catServiceMock = {
        getAll: jasmine.createSpy('getAll')
        .and.returnValue(of({body: [{}, {}] })), 
        delete: jasmine.createSpy('delete')
        .and.returnValue(of()),
    };
    let typeServiceMock = {
        getTypesOfCategory: jasmine.createSpy('getTypesOfCategory')
        .and.returnValue(of({body: [{}, {}] })), 
        delete: jasmine.createSpy('delete')
        .and.returnValue(of()),
    };
    TestBed.configureTestingModule({
        declarations: [ CategoriesAndTypesComponent],
        imports: [ToastrModule.forRoot(), FormsModule, ReactiveFormsModule, MatDialogModule,
         MatFormFieldModule,MaterialModule
         ],
        providers:[ 
            { provide: CategoryService, useValue: catServiceMock },
            { provide: TypeService, useValue: typeServiceMock },
            { provide: Router, useValue: routerMock },
            { provide: MatDialog, useClass: MdDialogMock},
            { provide: ToastrService, useValue: toastrMocked }]
     })
    fixture = TestBed.createComponent(CategoriesAndTypesComponent);
    component = fixture.componentInstance;
    catService = TestBed.inject(CategoryService);
    typeService = TestBed.inject(TypeService);
    router = TestBed.inject(Router);
    dialog = TestBed.get(MatDialog);
    toastr = TestBed.inject(ToastrService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  it('should fetch the categories and types on init', async(() => {
    component.ngOnInit();
   
    expect(catService.getAll).toHaveBeenCalled();
    expect(component.categories.length).toEqual(2);
    fixture.whenStable()
      .then(() => {
          component.categories.forEach(element => {
            component.getTypes(element.id);
            expect(typeService.getTypesOfCategory).toHaveBeenCalled();
          })
        
      });
  }));

  it('should open dialog for edit type on click  ', fakeAsync(() => {
    expect(component).toBeTruthy();
    let dialogRefSpyObj = jasmine.createSpyObj({ afterClosed : of({}), close: null });
    dialogRefSpyObj.componentInstance = { typeId: '' };
    spyOn(TestBed.get(MatDialog), 'open').and.returnValue(dialogRefSpyObj);
    component.editType(1);
    expect(dialog.open).toHaveBeenCalled();
    flush();
    
  }));

  it('should open dialog for edit category on click  ', fakeAsync(() => {
    expect(component).toBeTruthy();
    let dialogRefSpyObj = jasmine.createSpyObj({ afterClosed : of({}), close: null });
    dialogRefSpyObj.componentInstance = { catId: '' };
    spyOn(TestBed.get(MatDialog), 'open').and.returnValue(dialogRefSpyObj);
    component.editCategory(1);
    expect(dialog.open).toHaveBeenCalled();
    flush();
    
  }));
  it('should open dialog for new type on click  ', fakeAsync(() => {
    expect(component).toBeTruthy();
    spyOn(dialog, 'open').and.callThrough();
    component.newType();
    expect(dialog.open).toHaveBeenCalled();
    flush();
    
  }));
  it('should open dialog for new category on click  ', fakeAsync(() => {
    expect(component).toBeTruthy();
    spyOn(dialog, 'open').and.callThrough();
    component.newCategory();
    expect(dialog.open).toHaveBeenCalled();
    flush();
    
  }));
  it('should open dialog for delete category on click for delete and delete', fakeAsync(() => {
    expect(component).toBeTruthy();
    spyOn(dialog, 'open').and.callThrough();
    component.confirmDialog(1);
    expect(dialog.open).toHaveBeenCalled();
    component.deleteCategory(1);
    expect(catService.delete).toHaveBeenCalled();
    flush();
  }));
  it('should open dialog for delete type on click for delete and delete', fakeAsync(() => {
    expect(component).toBeTruthy();
    spyOn(dialog, 'open').and.callThrough();
    component.confirmDialogT(1);
    expect(dialog.open).toHaveBeenCalled();
    component.deleteType(1);
    expect(typeService.delete).toHaveBeenCalled();
    flush();
  }));
});
