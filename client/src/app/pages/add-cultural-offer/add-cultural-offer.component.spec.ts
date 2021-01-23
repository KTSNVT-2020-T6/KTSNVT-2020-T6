import {ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core'
import {async, fakeAsync, tick} from '@angular/core/testing';



import { of } from 'rxjs';
import { CulturalOfferDetailsService } from '../services/cultural-offer-details/cultural-offer-details.service';
import { ImageService } from '../services/image/image.service';
import { MatDialog, MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CulturalOffer } from '../model/CulturalOffer';
import { Category } from '../model/Category';
import { Type } from '../model/Type';
import { AddCulturalOfferComponent } from './add-cultural-offer.component';
import { TypeService } from '../services/type/type.service';
import { CategoryService } from '../services/category/category.service';
import { BrowserAnimationsModule, NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from '../material-module';
import { MatSelectModule } from '@angular/material/select';

export class MatDialogRefMock {
    close(value = '') {
    }
}

describe('AddCulturalOfferComponent', () => {
  let component: AddCulturalOfferComponent;
  let fixture: ComponentFixture<AddCulturalOfferComponent>;
  let typeService: any;
  let culturalOfferService: any;
  let imageService: any;
  let router: any;
  let dialogRef: any;
  let toastr: any;
  let categoryService: any;


  const mockCategory: Category = {
    id: 1,
    name: 'category number 1',
    description: 'this is a category no 1'
  }
  const mockType: Type = {
    id: 1,
    name: 'type number 1',
    description: 'this is a type no 1',
    categoryDTO: mockCategory
  }
  const mockCulturalOffer: CulturalOffer = {
    id: 1,
    averageRate: 5,
    description: 'desc',
    name: 'co name',
    city: 'belgrade',
    date: new Date,
    lat: 45.41,
    lon: 21.16,
    typeDTO: mockType
  };


  beforeEach(() => {
    let typeServiceMock = {
      getAll: jasmine.createSpy('getAll')
          .and.returnValue(of({body: [{}, {}] })), 
    getTypesOfCategory: jasmine.createSpy('getTypesOfCategory')
        .and.returnValue(of({body: [{}, {}] })), 
    };

    let culturalOfferServiceMock = {
        edit: jasmine.createSpy('edit')
            .and.returnValue(of({body: {}})),
        add: jasmine.createSpy('add')
            .and.returnValue(of({body: {}})), 
      };

    
    let categoryServiceMock = {
        getAll: jasmine.createSpy('getAll')
        .and.returnValue(of({body: [{}, {}] })), 
    };

    let routerMock= {
        navigate: jasmine.createSpy('navigate')
    }

    const toastrMocked = {
        success: jasmine.createSpy('success'),
        error: jasmine.createSpy('error')
      };

    TestBed.configureTestingModule({
       declarations: [ AddCulturalOfferComponent ],
       imports: [ToastrModule.forRoot(), NoopAnimationsModule,ReactiveFormsModule, FormsModule, MatDialogModule],
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

  
  it('should fetch all categories on init', async(() => {
    component.ngOnInit();

    expect(categoryService.getAll).toHaveBeenCalled();

    fixture.whenStable()
      .then(() => {
        expect(component.categories.length).toBe(2); 
        fixture.detectChanges();        
        let elements: DebugElement[] = 
          fixture.debugElement.queryAll(By.css('mat-option'));
        expect(elements.length).toBe(2); 
      });
  }));

  
  it('should set place coordinates and city on place selected', async(() => {
    let mockPlace = {
        properties: {
            lat: 45.50,
            lon: 20.23,
            address_line1: 'Novi Sad',
            address_line2: 'Bulevar Oslobodjenja 129'
        }
    }
    component.placeSelected(mockPlace);
    
    expect(component.placeLat).toEqual(45.50); 
    expect(component.placeLon).toEqual(20.23);  
    expect(component.city).toEqual('Novi Sad Bulevar Oslobodjenja 129');
  }));

  it('should set types on selection', async(() => {   
    component.onSelectionCategory(1);
    expect(typeService.getTypesOfCategory).toHaveBeenCalled();
    expect(component.types.length).toEqual(2);
 }));

  it('should set input in reactive form', fakeAsync(() => {
    fixture.detectChanges();  
    fixture.whenStable().then(() => {
        expect(fixture.debugElement.query(By.css('#coName')).nativeElement.value).toEqual('');
        expect(fixture.debugElement.query(By.css('#coDescription')).nativeElement.value).toEqual('');
        
        let coName = fixture.debugElement.query(By.css('#coName')).nativeElement;
        coName.value = 'novo ime';
        let coDesc = fixture.debugElement.query(By.css('#coDescription')).nativeElement;
        coDesc.value = 'novi opis';
        coName.dispatchEvent(new Event('input')); 
        coDesc.dispatchEvent(new Event('input'));
        
        let controlName = component.coForm.controls['name'];
        let controlDesc = component.coForm.controls['description'];
        
        expect(controlName.value).toEqual('novo ime');
        expect(controlDesc.value).toEqual('novi opis');
      });   
  }));

  /*
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
    component.form.controls['name'].setValue("");

    fixture.detectChanges();
    component.saveChanges();

    expect(culturalOfferService.edit).not.toHaveBeenCalled(); 
    expect(toastr.success).not.toHaveBeenCalledWith('Cultural offer information saved!');
    expect(dialogRef.close).not.toHaveBeenCalled();
 
}));

it('should not save updates if description input is empty', fakeAsync(() => { 
    spyOn(dialogRef, 'close');
    component.form.controls['description'].setValue("");

    fixture.detectChanges();
    component.saveChanges();

    expect(culturalOfferService.edit).not.toHaveBeenCalled(); 
    expect(toastr.success).not.toHaveBeenCalled();
    expect(dialogRef.close).not.toHaveBeenCalled();
 
}));
*/

});
