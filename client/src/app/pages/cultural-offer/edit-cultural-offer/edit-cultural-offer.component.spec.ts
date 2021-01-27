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
import { EditCulturalOfferComponent } from './edit-cultural-offer.component';
import { TypeService } from '../../../core/services/type/type.service';

export class MatDialogRefMock {
    close(value = ''): void {

    }
}

describe('EditCulturalOfferComponent', () => {
  let component: EditCulturalOfferComponent;
  let fixture: ComponentFixture<EditCulturalOfferComponent>;
  let typeService: any;
  let culturalOfferService: any;
  let imageService: any;
  let router: any;
  let dialogRef: any;
  let toastr: any;


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


  beforeEach(() => {
    const typeServiceMock = {
      getAll: jasmine.createSpy('getAll')
          .and.returnValue(of({body: [{}, {}] })),
    };

    const culturalOfferServiceMock = {
        edit: jasmine.createSpy('edit')
            .and.returnValue(of({body: {}})),
      };

    const imageServiceMock = {
    add: jasmine.createSpy('add')
        .and.returnValue(of(1)),
    };

    const routerMock = {
        navigate: jasmine.createSpy('navigate')
    };

    const toastrMocked = {
        success: jasmine.createSpy('success'),
        error: jasmine.createSpy('error')
      };

    TestBed.configureTestingModule({
       declarations: [ EditCulturalOfferComponent ],
       imports: [ToastrModule.forRoot(), ReactiveFormsModule, FormsModule, MatDialogModule],
       providers:    [ {provide: TypeService, useValue: typeServiceMock },
                       {provide: CulturalOfferDetailsService, useValue: culturalOfferServiceMock },
                       {provide: ImageService, useValue: imageServiceMock },
                       { provide: MatDialogRef, useClass: MatDialogRefMock},
                       { provide: Router, useValue: routerMock },
                       { provide: ToastrService, useValue: toastrMocked },
                       { provide: MAT_DIALOG_DATA, useValue: mockCulturalOffer }]
       });

    fixture = TestBed.createComponent(EditCulturalOfferComponent);
    component = fixture.componentInstance;
    typeService = TestBed.inject(TypeService);
    culturalOfferService = TestBed.inject(CulturalOfferDetailsService);
    imageService = TestBed.inject(ImageService);
    router = TestBed.inject(Router);
    toastr = TestBed.inject(ToastrService);
    dialogRef = TestBed.inject(MatDialogRef);

  });

  it('should create component', fakeAsync(() => {
    expect(component).toBeTruthy();
  }));

  it('should fetch all types of cultural offer on init', waitForAsync(() => {
    component.ngOnInit();

    expect(typeService.getAll).toHaveBeenCalled();

    fixture.whenStable()
      .then(() => {
        expect(component.types.length).toBe(2);
        fixture.detectChanges();
        const elements: DebugElement[] =
          fixture.debugElement.queryAll(By.css('mat-option'));
        expect(elements.length).toBe(2);
      });
  }));

  it('should set form values in constructor', waitForAsync(() => {

    fixture.whenStable()
      .then(() => {
        expect(component.form).toBeDefined();
        console.log(component.form.value);
        expect(component.form.value.name).toEqual('co name');
        expect(component.form.value.description).toEqual('desc');
        expect(component.form.value.image).toEqual('');
        expect(component.form.value.date).toEqual(mockCulturalOffer.date);

        fixture.detectChanges();
        const nameInput = fixture.debugElement.query(By.css('#editNameCulturalOffer')).nativeElement;
        const descInput = fixture.debugElement.query(By.css('#editDescriptionCulturalOffer')).nativeElement;

        expect(nameInput.value).toEqual('co name');
        expect(descInput.value).toEqual('desc');
      });
  }));

  it('should close dialog on cancel', waitForAsync(() => {
    spyOn(dialogRef, 'close');
    component.cancel();
    expect(dialogRef.close).toHaveBeenCalled();
  }));

  it('should save image on upload', waitForAsync(() => {
    component.co.imageDTO = [];
    component.upload(0, {name: 'image'});

    expect(imageService.add).toHaveBeenCalledTimes(1);
    expect(component.progressInfos.length).toBe(1);
    expect(component.co.imageDTO.length).toBe(1);
    expect(component.co.imageDTO[0].id).toEqual(1);
  }));

  it('should set place coordinates and city on place selected', waitForAsync(() => {
    const mockPlace = {
        properties: {
            lat: 45.50,
            lon: 20.23,
            address_line1: 'Novi Sad',
            address_line2: 'Bulevar Oslobodjenja 129'
        }
    };

    component.placeSelected(mockPlace);

    expect(component.placeLat).toEqual(45.50);
    expect(component.placeLon).toEqual(20.23);
    expect(component.city).toEqual('Novi Sad Bulevar Oslobodjenja 129');
  }));

  it('should set type on selection', waitForAsync(() => {
    component.onSelection(component.types[0]);

    expect(component.co.typeDTO).toEqual(component.types[0]);
 }));

  it('should set input in reactive form', fakeAsync(() => {
    fixture.detectChanges();
    fixture.whenStable().then(() => {
        expect(fixture.debugElement.query(By.css('#editNameCulturalOffer')).nativeElement.value).toEqual('co name');
        expect(fixture.debugElement.query(By.css('#editDescriptionCulturalOffer')).nativeElement.value).toEqual('desc');

        const coName = fixture.debugElement.query(By.css('#editNameCulturalOffer')).nativeElement;
        coName.value = 'novo ime';
        const coDesc = fixture.debugElement.query(By.css('#editDescriptionCulturalOffer')).nativeElement;
        coDesc.value = 'novi opis';

        coName.dispatchEvent(new Event('input'));
        coDesc.dispatchEvent(new Event('input'));

        const controlName = component.form.controls.name;
        const controlDesc = component.form.controls.description;

        expect(controlName.value).toEqual('novo ime');
        expect(controlDesc.value).toEqual('novi opis');
      });
  }));


  it('should save updates', fakeAsync(() => {
    spyOn(dialogRef, 'close');
    component.saveChanges();
    tick(15000);

    expect(culturalOfferService.edit).toHaveBeenCalled();
    expect(toastr.success).toHaveBeenCalled();
    expect(dialogRef.close).toHaveBeenCalled();
    expect(component.form.invalid).toBeTruthy();

}));

  it('should not save updates if name input is empty', fakeAsync(() => {
    spyOn(dialogRef, 'close');
    component.form.controls.name.setValue('');

    fixture.detectChanges();
    component.saveChanges();

    expect(culturalOfferService.edit).not.toHaveBeenCalled();
    expect(toastr.success).not.toHaveBeenCalledWith('Cultural offer information saved!');
    expect(dialogRef.close).not.toHaveBeenCalled();

}));

  it('should not save updates if description input is empty', fakeAsync(() => {
    spyOn(dialogRef, 'close');
    component.form.controls.description.setValue('');

    fixture.detectChanges();
    component.saveChanges();

    expect(culturalOfferService.edit).not.toHaveBeenCalled();
    expect(toastr.success).not.toHaveBeenCalled();
    expect(dialogRef.close).not.toHaveBeenCalled();

}));

});
