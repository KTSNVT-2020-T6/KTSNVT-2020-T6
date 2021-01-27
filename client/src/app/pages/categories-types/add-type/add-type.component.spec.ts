import { DebugElement } from '@angular/core';
import { ComponentFixture, fakeAsync, TestBed, waitForAsync } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { By } from '@angular/platform-browser';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { of } from 'rxjs';
import {async, tick} from '@angular/core/testing';
import { Category } from '../../../core/model/Category';
import { CategoryService } from '../../../core/services/category/category.service';
import { TypeService } from '../../../core/services/type/type.service';

import { AddTypeComponent } from './add-type.component';
import { Type } from '../../../core/model/Type';
import { MaterialModule } from '../../material-module';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
export class MatDialogRefMock {
    close(value = ''): void {
    }
}

describe('AddTypeComponent', () => {
  let component: AddTypeComponent;
  let fixture: ComponentFixture<AddTypeComponent>;
  let typeService: any;
  let categoryService: any;
  let toastr: any;
  let dialogRef: any;

  const mockCategory: Category = {
    id: 1,
    name: 'category number 1',
    description: 'this is a category no 1'
  };
  const mockType: Type = {
    id: 1,
    name: 'type number 1',
    description: 'this is a type no 1',
    categoryDTO: mockCategory
  };


  beforeEach(() => {
    const categoryServiceMock = {
        getAll: jasmine.createSpy('getAll')
            .and.returnValue(of({body: [{}, {}] })),
    };
    const typeServiceMock = {
        add: jasmine.createSpy('add')
            .and.returnValue(of({body: {}})),
    };
    const toastrMocked = {
        success: jasmine.createSpy('success'),
        error: jasmine.createSpy('error')
      };
    TestBed.configureTestingModule({
        declarations: [ AddTypeComponent ],
        imports: [ ToastrModule.forRoot(), NoopAnimationsModule, ReactiveFormsModule, FormsModule, MatDialogModule],
        providers:    [ { provide: TypeService, useValue: typeServiceMock },
                        { provide: CategoryService, useValue: categoryServiceMock },
                        { provide: MatDialogRef, useClass: MatDialogRefMock},
                        { provide: ToastrService, useValue: toastrMocked }]

        });
    fixture = TestBed.createComponent(AddTypeComponent);
    component = fixture.componentInstance;
    typeService = TestBed.inject(TypeService);
    categoryService = TestBed.inject(CategoryService);
    toastr = TestBed.inject(ToastrService);
    dialogRef = TestBed.inject(MatDialogRef);
  });

  it('should create', fakeAsync(() => {
    expect(component).toBeTruthy();
  }));

  it('should fetch all categories on init', waitForAsync(() => {
    component.ngOnInit();

    expect(categoryService.getAll).toHaveBeenCalled();

    fixture.whenStable()
      .then(() => {
        expect(component.categories.length).toBe(2);
        fixture.detectChanges();
        const elements: DebugElement[] =
          fixture.debugElement.queryAll(By.css('mat-option'));
        expect(elements.length).toBe(2);

      });
  }));
  it('form invalid when empty', () => {
    expect(component.typeForm.valid).toBeFalsy();
  });

  it('should set category on selection', waitForAsync(() => {
    component.categories = [{id: 1}, {id: 2}];
    component.onSelection(component.categories[0]);

    expect(component.typeForm.controls.categoryDTO.value).toEqual(component.categories[0]);
 }));

  it('should set input in reactive form', fakeAsync(() => {

    fixture.detectChanges();
    fixture.whenStable().then(() => {
        expect(fixture.debugElement.query(By.css('#typeName')).nativeElement.value).toEqual('');
        expect(fixture.debugElement.query(By.css('#typeDescription')).nativeElement.value).toEqual('');
        expect(fixture.debugElement.query(By.css('#catSelect')).nativeElement.value).toEqual(undefined);

        const typeName = fixture.debugElement.query(By.css('#typeName')).nativeElement;
        typeName.value = 'type';
        const typeDesc = fixture.debugElement.query(By.css('#typeDescription')).nativeElement;
        typeDesc.value = 'type desc';

        component.onSelection(component.categories[0]);
        typeName.dispatchEvent(new Event('input'));
        typeDesc.dispatchEvent(new Event('input'));

        const controlName = component.typeForm.controls.name;
        const controlDesc = component.typeForm.controls.description;
        const controlCat = component.typeForm.controls.categoryDTO;

        expect(controlName.value).toEqual('type');
        expect(controlDesc.value).toEqual('type desc');
        expect(controlCat.value).toEqual(component.categories[0]);
      });
  }));


  it('should save type', fakeAsync(() => {
     component.typeForm.controls.name.setValue(mockType.name);
     component.typeForm.controls.description.setValue(mockType.description);
     component.typeForm.controls.categoryDTO.setValue(mockType.categoryDTO);
     fixture.detectChanges();
     spyOn(dialogRef, 'close');
     spyOn(component, 'windowReload').and.callFake(() => {});
     component.addType();
     tick(15000);

     expect(typeService.add).toHaveBeenCalled();
     expect(toastr.success).toHaveBeenCalled();
     expect(dialogRef.close).toHaveBeenCalled();
     expect(component.windowReload).toHaveBeenCalled();
     expect(component.typeForm.invalid).toBeTruthy();
  }));

  it('should not save type without name', fakeAsync(() => {
    component.typeForm.controls.name.setValue('');
    component.typeForm.controls.description.setValue(mockType.description);
    component.typeForm.controls.categoryDTO.setValue(mockType.categoryDTO);

    spyOn(dialogRef, 'close');
    component.addType();
    tick(15000);

    expect(typeService.add).not.toHaveBeenCalled();

  }));

  it('should not save type without description', fakeAsync(() => {
    component.typeForm.controls.name.setValue(mockType.name);
    component.typeForm.controls.description.setValue('');
    component.typeForm.controls.categoryDTO.setValue(mockType.categoryDTO);

    spyOn(dialogRef, 'close');
    component.addType();
    tick(15000);

    expect(typeService.add).not.toHaveBeenCalled();

  }));

  it('should not save type without category', fakeAsync(() => {
    component.typeForm.controls.name.setValue(mockType.name);
    component.typeForm.controls.description.setValue(mockType.description);
    component.typeForm.controls.categoryDTO.setValue('');

    spyOn(dialogRef, 'close');
    component.addType();
    tick(15000);

    expect(typeService.add).not.toHaveBeenCalled();

  }));
});
