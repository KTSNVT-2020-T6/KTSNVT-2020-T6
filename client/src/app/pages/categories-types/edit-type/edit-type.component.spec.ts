import { ComponentFixture, fakeAsync, TestBed, waitForAsync } from '@angular/core/testing';
import { EditTypeComponent } from './edit-type.component';
import { DebugElement } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { By } from '@angular/platform-browser';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { of } from 'rxjs';
import {async, tick} from '@angular/core/testing';
import { Category } from '../../../core/model/Category';
import { Type } from '../../../core/model/Type';
import { TypeService } from '../../../core/services/type/type.service';
import { CategoryService } from '../../../core/services/category/category.service';

export class MatDialogRefMock {
    close(value = ''): void{

    }
}

describe('EditTypeComponent', () => {
  let component: EditTypeComponent;
  let fixture: ComponentFixture<EditTypeComponent>;
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
        update: jasmine.createSpy('update')
            .and.returnValue(of({body: {}})),
        getType: jasmine.createSpy('getType')
            .and.returnValue(of({body: mockType})),
    };
    const toastrMocked = {
        success: jasmine.createSpy('success'),
        error: jasmine.createSpy('error')
      };
    TestBed.configureTestingModule({
        declarations: [ EditTypeComponent ],
        imports: [ToastrModule.forRoot(), ReactiveFormsModule, FormsModule, MatDialogModule],
        providers:    [ { provide: TypeService, useValue: typeServiceMock },
                        { provide: CategoryService, useValue: categoryServiceMock },
                        { provide: MatDialogRef, useClass: MatDialogRefMock},
                        { provide: ToastrService, useValue: toastrMocked }]

        });
    fixture = TestBed.createComponent(EditTypeComponent);
    component = fixture.componentInstance;
    typeService = TestBed.inject(TypeService);
    categoryService = TestBed.inject(CategoryService);
    toastr = TestBed.inject(ToastrService);
    dialogRef = TestBed.inject(MatDialogRef);
  });

  it('should create', fakeAsync(() => {
    expect(component).toBeTruthy();
  }));

  it('should fetch  all categories and set form values on init', waitForAsync(() => {
    component.ngOnInit();
    expect(typeService.getType).toHaveBeenCalled();
    fixture.whenStable()
      .then(() => {
        expect(component.typeForm.value.name).toEqual(mockType.name);
        expect(component.typeForm.value.description).toEqual(mockType.description);
      });

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



  it('should set category on selection', waitForAsync(() => {
    component.onSelection(component.categories[0]);
    expect(component.category).toEqual(component.categories[0]);
 }));

  it('should set input in reactive form', fakeAsync(() => {

    fixture.detectChanges();
    fixture.whenStable().then(() => {
        expect(fixture.debugElement.query(By.css('#typeEditName')).nativeElement.value).toEqual(mockType.name);
        expect(fixture.debugElement.query(By.css('#typeEditDescritpion')).nativeElement.value).toEqual(mockType.description);

        const typeName = fixture.debugElement.query(By.css('#typeEditName')).nativeElement;
        typeName.value = 'type';
        const typeDesc = fixture.debugElement.query(By.css('#typeEditDescritpion')).nativeElement;
        typeDesc.value = 'type desc';

        typeName.dispatchEvent(new Event('input'));
        typeDesc.dispatchEvent(new Event('input'));

        const controlName = component.typeForm.controls.name;
        const controlDesc = component.typeForm.controls.description;

        expect(controlName.value).toEqual('type');
        expect(controlDesc.value).toEqual('type desc');
      });
  }));

  it('should save type changes', fakeAsync(() => {
    spyOn(dialogRef, 'close');
    spyOn(component, 'windowReload').and.callFake(() => {});
    component.editType();
    tick(15000);

    expect(typeService.update).toHaveBeenCalled();
    expect(toastr.success).toHaveBeenCalled();
    expect(dialogRef.close).toHaveBeenCalled();
    expect(component.windowReload).toHaveBeenCalled();

  }));

  it('should close dialog on cancel', waitForAsync(() => {
    spyOn(dialogRef, 'close');
    component.cancelClicked();
    expect(dialogRef.close).toHaveBeenCalled();
  }));

});
