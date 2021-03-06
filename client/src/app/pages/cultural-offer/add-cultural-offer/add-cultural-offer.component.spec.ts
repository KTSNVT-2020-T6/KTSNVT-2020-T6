import {ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { Router } from '@angular/router';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';
import {async, fakeAsync, tick} from '@angular/core/testing';
import { of } from 'rxjs';
import { CulturalOfferDetailsService } from '../../../core/services/cultural-offer-details/cultural-offer-details.service';
import { ImageService } from '../../../core/services/image/image.service';
import { MatDialog, MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CulturalOffer } from '../../../core/model/CulturalOffer';
import { Category } from '../../../core/model/Category';
import { Type } from '../../../core/model/Type';
import { AddCulturalOfferComponent } from './add-cultural-offer.component';
import { TypeService } from '../../../core/services/type/type.service';
import { CategoryService } from '../../../core/services/category/category.service';
import { BrowserAnimationsModule, NoopAnimationsModule } from '@angular/platform-browser/animations';

export class MatDialogRefMock {
    close(value = ''): void {
    }
}

describe('AddCulturalOfferComponent', () => {
  let component: AddCulturalOfferComponent;
  let fixture: ComponentFixture<AddCulturalOfferComponent>;
  let typeService: any;
  let culturalOfferService: any;
  let router: any;
  let dialogRef: any;
  let toastr: any;
  let categoryService: any;

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
  const mockCulturalOffer: CulturalOffer = {
    id: 1,
    averageRate: 5,
    description: 'desc',
    name: 'co name',
    city: 'belgrade',
    date: new Date(),
    lat: 45.41,
    lon: 21.16,
    typeDTO: mockType
  };

  const mockPlace = {
    properties: {
        lat: 45.50,
        lon: 20.23,
        address_line1: 'Novi Sad',
        address_line2: 'Bulevar Oslobodjenja 129'
    }
};


  beforeEach(() => {
    const typeServiceMock = {
      getAll: jasmine.createSpy('getAll')
          .and.returnValue(of({body: [{}, {}] })),
    getTypesOfCategory: jasmine.createSpy('getTypesOfCategory')
        .and.returnValue(of({body: [{}, {}] })),
    };

    const culturalOfferServiceMock = {
        edit: jasmine.createSpy('edit')
            .and.returnValue(of({body: {}})),
        add: jasmine.createSpy('add')
            .and.returnValue(of({body: {}})),
      };


    const categoryServiceMock = {
        getAll: jasmine.createSpy('getAll')
        .and.returnValue(of({body: [{}, {}] })),
    };

    const routerMock = {
        navigate: jasmine.createSpy('navigate')
    };

    const toastrMocked = {
        success: jasmine.createSpy('success'),
        error: jasmine.createSpy('error')
      };



    TestBed.configureTestingModule({
       declarations: [ AddCulturalOfferComponent ],
       imports: [ToastrModule.forRoot(), NoopAnimationsModule, ReactiveFormsModule, FormsModule, MatDialogModule],
       providers:    [ {provide: TypeService, useValue: typeServiceMock },
                       {provide: CulturalOfferDetailsService, useValue: culturalOfferServiceMock },
                       {provide: CategoryService, useValue: categoryServiceMock},
                       { provide: MatDialogRef, useClass: MatDialogRefMock},
                       { provide: Router, useValue: routerMock },
                       { provide: ToastrService, useValue: toastrMocked }]

       });

    fixture = TestBed.createComponent(AddCulturalOfferComponent);
    component = fixture.componentInstance;
    typeService = TestBed.inject(TypeService);
    culturalOfferService = TestBed.inject(CulturalOfferDetailsService);
    categoryService = TestBed.inject(CategoryService);
    router = TestBed.inject(Router);
    toastr = TestBed.inject(ToastrService);
    dialogRef = TestBed.inject(MatDialogRef);

  });

  it('should create component', fakeAsync(() => {
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


  it('should set place coordinates and city on place selected', waitForAsync(() => {

    component.placeSelected(mockPlace);

    expect(component.placeLat).toEqual(45.50);
    expect(component.placeLon).toEqual(20.23);
    expect(component.city).toEqual('Novi Sad Bulevar Oslobodjenja 129');
  }));

  it('should set types on selection', waitForAsync(() => {
    component.onSelectionCategory(1);
    expect(typeService.getTypesOfCategory).toHaveBeenCalled();
    expect(component.types.length).toEqual(2);
 }));

  it('should set input in reactive form', fakeAsync(() => {
    fixture.detectChanges();
    fixture.whenStable().then(() => {
        expect(fixture.debugElement.query(By.css('#coName')).nativeElement.value).toEqual('');
        expect(fixture.debugElement.query(By.css('#coDescription')).nativeElement.value).toEqual('');

        const coName = fixture.debugElement.query(By.css('#coName')).nativeElement;
        coName.value = 'novo ime';
        const coDesc = fixture.debugElement.query(By.css('#coDescription')).nativeElement;
        coDesc.value = 'novi opis';
        coName.dispatchEvent(new Event('input'));
        coDesc.dispatchEvent(new Event('input'));

        const controlName = component.coForm.controls.name;
        const controlDesc = component.coForm.controls.description;

        expect(controlName.value).toEqual('novo ime');
        expect(controlDesc.value).toEqual('novi opis');
      });
  }));


  it('should save cultural offer', fakeAsync(() => {
    spyOn(dialogRef, 'close');
    spyOn(component, 'windowReload').and.callFake(() => {});
    component.coForm.controls.name.setValue('cultural offer');
    component.coForm.controls.description.setValue('desc');
    component.coForm.controls.date.setValue(new Date());
    component.coForm.controls.typeDTO.setValue(mockType);
    component.placeLat = mockPlace.properties.lat;
    component.placeLon = mockPlace.properties.lon;
    component.city = 'City';
    fixture.detectChanges();

    component.addCulturalOffer();
    tick(15000);

    expect(culturalOfferService.add).toHaveBeenCalled();
    expect(toastr.success).toHaveBeenCalled();
    expect(dialogRef.close).toHaveBeenCalled();
    expect(component.windowReload).toHaveBeenCalled();

}));

  it('should not save if name input is empty', fakeAsync(() => {
    spyOn(dialogRef, 'close');
    component.coForm.controls.name.setValue('');

    fixture.detectChanges();
    component.addCulturalOffer();

    expect(culturalOfferService.edit).not.toHaveBeenCalled();
    expect(toastr.success).not.toHaveBeenCalledWith('Cultural offer information saved!');
    expect(dialogRef.close).not.toHaveBeenCalled();

}));

  it('should not save if description input is empty', fakeAsync(() => {
    spyOn(dialogRef, 'close');
    component.coForm.controls.description.setValue('');

    fixture.detectChanges();
    component.addCulturalOffer();

    expect(culturalOfferService.edit).not.toHaveBeenCalled();
    expect(toastr.success).not.toHaveBeenCalled();
    expect(dialogRef.close).not.toHaveBeenCalled();

}));


});
