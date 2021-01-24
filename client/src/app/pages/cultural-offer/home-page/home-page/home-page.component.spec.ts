import {ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core'
import {async, fakeAsync, tick} from '@angular/core/testing';



import { of } from 'rxjs';
import { JwtHelperService } from '@auth0/angular-jwt';
import { MatDialog } from '@angular/material/dialog';
import { ToastrModule } from 'ngx-toastr';
import { HomePageComponent } from './home-page.component';
import { CulturalOfferDetailsService } from '../../../../core/services/cultural-offer-details/cultural-offer-details.service';
import { ImageService } from '../../../../core/services/image/image.service';

class MdDialogMock {
    open() {
      return {
        afterClosed: jasmine.createSpy('afterClosed').and.returnValue(of(true))
      };
    }
  };

describe('HomePageComponent', () => {
  let component: HomePageComponent;
  let fixture: ComponentFixture<HomePageComponent>;
  let culturalOfferService: any;
  let imageService: any;
  let dialog: MdDialogMock;
  let jwtHelper: any;

  beforeEach(() => {
    let dialogMock = {
        open: jasmine.createSpy('open').and.callThrough(),
        afterClosed: jasmine.createSpy('afterClosed').and.callThrough(),
    }

    let culturalOfferServiceMock = {
        getPage: jasmine.createSpy('getPage')
          .and.returnValue(of({body: {content: [{imageDTO: [{id:1}, {id:2}]}, {imageDTO: [{id:3}, {id:4}]}], totalElements: 2 } })),
        getOne: jasmine.createSpy('getOne')
            .and.returnValue(of({body: {}})), 
        searchCombined: jasmine.createSpy('searchCombined')
            .and.returnValue(of({body: {content: [{imageDTO: [{id:1},{id:2}]}, {imageDTO: [{id:3}, {id:4}]}], totalElements: 2 }}))
      };

    let imageServiceMock = {
    getImage: jasmine.createSpy('getImage')
        .and.returnValue(of({body: ''})), 
    };

    const jwtServiceMocked = {
        decodeToken: jasmine.createSpy('decodeToken').and.returnValue({role: 'ROLE_ADMIN'})
      }

    TestBed.configureTestingModule({
       declarations: [ HomePageComponent ],
       imports: [ToastrModule.forRoot()],
       providers:    [ {provide: CulturalOfferDetailsService, useValue: culturalOfferServiceMock },
                       {provide: ImageService, useValue: imageServiceMock },
                       { provide: MatDialog, useClass: MdDialogMock},
                       { provide: JwtHelperService, useValue: jwtServiceMocked}]
       });

    fixture = TestBed.createComponent(HomePageComponent);
    component    = fixture.componentInstance;
    culturalOfferService = TestBed.inject(CulturalOfferDetailsService);
    imageService = TestBed.inject(ImageService);
    dialog = TestBed.get(MatDialog);
    jwtHelper = TestBed.inject(JwtHelperService);

  });

  it('should create component', fakeAsync(() => {
    expect(component).toBeTruthy();
  }));

  it('should fetch the cultural offer list page on init', async(() => {
    component.ngOnInit();

    expect(culturalOfferService.getPage).toHaveBeenCalled();


    fixture.whenStable()
      .then(() => {
        expect(component.culturalOfferList.length).toBe(2); 
        fixture.detectChanges(); 
      });
  }));

  it('should load images of cultural offer', async(() => {
    component.ngOnInit();
    component.loadImage();
    expect(imageService.getImage).toHaveBeenCalledTimes(4);
    
  }));


  it('should fetch the cultural offers on changePage', async(() => {
    component.changePage(2);

    expect(culturalOfferService.getPage).toHaveBeenCalled();

    fixture.whenStable()
      .then(() => {
        expect(component.culturalOfferList.length).toBe(2); 
        fixture.detectChanges(); 
      });
  }));

  it('should fetch the cultural offers on changePage', async(() => {
    component.ngOnInit();
    component.changePageFiltered(2);

    expect(culturalOfferService.searchCombined).toHaveBeenCalled();

  }));

  // it ('should open search dialog', () => {
  //   spyOn(dialog, 'open').and.callThrough();
  //   component.searchClicked();
  //   //treba da ga prespojisa SearchDetailsComponent ali to ne odradi        
  //   expect(dialog.open).toHaveBeenCalled();
  //   expect(component.searchDetails).toBeDefined();
  //   expect(culturalOfferService.searchCombined).toHaveBeenCalled();
  // }); 

});
