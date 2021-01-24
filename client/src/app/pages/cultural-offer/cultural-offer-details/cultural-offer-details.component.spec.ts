import {ComponentFixture, flush, TestBed } from '@angular/core/testing';
import { ActivatedRoute, Router } from '@angular/router';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core'
import {async, fakeAsync, tick} from '@angular/core/testing';

import { CulturalOfferDetailsComponent } from './cultural-offer-details.component';
import { CulturalOfferDetailsService } from '../../../core/services/cultural-offer-details/cultural-offer-details.service';
import { CulturalOffer } from '../../../core/model/CulturalOffer';

import { forkJoin, from, Observable,of } from 'rxjs';
import { ActivatedRouteStub } from 'src/app/testing/router-stubs';
import { Category } from '../../../core/model/Category';
import { Type } from '../../../core/model/Type';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatDialogModule} from '@angular/material/dialog';
import { HttpClientModule } from '@angular/common/http';
import { Toast, ToastContainerModule, ToastInjector, ToastrModule, ToastrService } from 'ngx-toastr';
import { RegisteredUserService } from '../../../core/services/registered-user/registered-user.service';
import { UserService } from '../../../core/services/user/user.service';
import { CommentService } from '../../../core/services/comment/comment.service';
import { RateService } from '../../../core/services/rate/rate.service';
import { ImageService } from '../../../core/services/image/image.service';
import { ImageSliderComponent } from '../image-slider/image-slider/image-slider.component';
import { MatFormFieldModule } from '@angular/material/form-field';;
import { Location } from '@angular/common';
import { User } from '../../../core/model/User';
import { StarRatingComponent} from '../star-rating/star-rating.component';
import { by, element } from 'protractor';

class MdDialogMock {
  open() {
    return {
      afterClosed: jasmine.createSpy('afterClosed').and.returnValue(of(true))
    };
  }
};
describe('CulturalOfferDetailsComponent', () => {
  let component: CulturalOfferDetailsComponent;
  let fixture: ComponentFixture<CulturalOfferDetailsComponent>;
  let culturalOfferService: any;
  let regUserService: any;
  let userService: any;
  let imageService: any;
  let rateService: any;
  let router: any;
  let activatedRoute: any;
  let dialog: MdDialogMock;
  let location: Location;
  let toastr: any;
  
  beforeEach(() => {
    let userMock: User ={
      id: 1,
      firstName: 'Stefan',
      lastName: 'Stefic',
      email: 'stefa@gmail.com',
      password: 'asdf',
      active:  true,
      verified: true,
      idImageDTO: 1,
      src: ''  
    }
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
    let culturalOffer: CulturalOffer = {
      id: 1,
      averageRate: 4.8,
      description: 'desc',
      name: 'co name',
      city: 'belgrade',
      date: new Date,
      lat: 45.41,
      lon: 21.16,
      typeDTO: mockType
    };
    let dialogMock = {
      open: jasmine.createSpy('open').and.callThrough(),
      afterClosed: jasmine.createSpy('afterClosed').and.callThrough(),
    }
    let culturalOfferServiceMock = {
      announceChange: jasmine.createSpy('announceChange'),
      getOne: jasmine.createSpy('getOne')
        .and.returnValue(of({body: culturalOffer})), 
      
      getFavorite: jasmine.createSpy('getFavorite')
        .and.returnValue(of({body:[culturalOffer]})),

      delete: jasmine.createSpy('delete').and.returnValue(of({ subscribe: () => {} })),

      subscribeUser: jasmine.createSpy('subscribeUser')
        .and.returnValue(of({ subscribe: () => {} })),
      unsubscribe: jasmine.createSpy('unsubscribe')
        .and.returnValue(of({ subscribe: () => {} })),

      RegenerateData$: {
        subscribe: jasmine.createSpy('subscribe')
      }
      
    };
    let registeredUserServiceMock = {

        getNumberOfSubscribed: jasmine.createSpy('getNumberOfSubscribed')
          .and.returnValue(of({body:3}))
    };
    let commentServiceMock={
        save: jasmine.createSpy('save')
        .and.returnValue(of({}))
    }
    let userServiceMock={
        getCurrentUser: jasmine.createSpy('getCurrentUser')
        .and.returnValue(of({body: userMock}))
    }
    let rateServiceMock={
        createOrEditRate: jasmine.createSpy('createOrEditRate')
        .and.returnValue(of({}))
    }
    let imageServiceMock={
        add: jasmine.createSpy('add')
        .and.returnValue(of({}))
    }

    let routerMock = {
      navigate: jasmine.createSpy('navigate')
    }; 
    const toastrMocked = {
      success: jasmine.createSpy('success'),
      error: jasmine.createSpy('error')
    };
   
    let activatedRouteStub: ActivatedRouteStub = new ActivatedRouteStub();
    activatedRouteStub.testParams = { id: 1 }; 

    TestBed.configureTestingModule({
       declarations: [ CulturalOfferDetailsComponent,ImageSliderComponent, StarRatingComponent],
       imports: [FormsModule, ReactiveFormsModule, MatDialogModule,
         HttpClientModule, MatFormFieldModule
        ],
       providers:[ 
           { provide: CulturalOfferDetailsService, useValue: culturalOfferServiceMock },
           { provide: RegisteredUserService, useValue: registeredUserServiceMock },
           { provide: UserService, useValue: userServiceMock },
           { provide: CommentService, useValue: commentServiceMock },
           { provide: RateService, useValue: rateServiceMock },
           { provide: ImageService, useValue: imageServiceMock },
           { provide: ActivatedRoute, useValue: activatedRouteStub },
           { provide: Router, useValue: routerMock },
           { provide: MatDialog, useClass: MdDialogMock},
           { provide: ToastrService, useValue: toastrMocked }]
    });
    location = TestBed.get(Location);
    fixture = TestBed.createComponent(CulturalOfferDetailsComponent);
    component = fixture.componentInstance;
    culturalOfferService = TestBed.inject(CulturalOfferDetailsService);
    regUserService = TestBed.inject(RegisteredUserService);
    userService = TestBed.inject(UserService);
    imageService = TestBed.inject(ImageService);
    rateService = TestBed.inject(RateService);
    activatedRoute = TestBed.inject(ActivatedRoute);
    router = TestBed.inject(Router);
    dialog = TestBed.get(MatDialog);
    toastr = TestBed.inject(ToastrService);
   
  });
  
  it('should create commponent', fakeAsync(() => {
    expect(component).toBeTruthy();
  }));

  it('should fetch the cultural offer on init', async(() => {
    component.ngOnInit();
   
    expect(culturalOfferService.RegenerateData$.subscribe).toHaveBeenCalled();
    expect(culturalOfferService.getOne).toHaveBeenCalledWith(1); 
    
    fixture.whenStable()
      .then(() => {
        fixture.detectChanges();       
        let averageRate = fixture.debugElement.query(By.css('.edit #averageRate')).nativeElement;
        expect(averageRate.innerHTML).toBe('4.8'); 
      });
  }));
  it('should fetch the subscription on init', async(() => {
    component.ngOnInit();
   
    expect(culturalOfferService.RegenerateData$.subscribe).toHaveBeenCalled();
    expect(regUserService.getNumberOfSubscribed).toHaveBeenCalledWith(1); 
    
    fixture.whenStable()
      .then(() => {
        fixture.detectChanges();       
        let subsrcibed = fixture.debugElement.query(By.css('#numberOfSubscribed')).nativeElement;
        expect(subsrcibed.innerHTML).toBe('3'); 
      });
  }));
  it('should fetch the number of subscribed on init', fakeAsync(() => {
    component.ngOnInit();
   
    expect(culturalOfferService.RegenerateData$.subscribe).toHaveBeenCalled();
    expect(culturalOfferService.getFavorite).toHaveBeenCalledWith(); 
    
    //check param check does it checked, and dependecy on this attribute will
    //be shown UNSUBSCRIBE or SUBSCRIBE button
    expect(component.checker).toBeTrue();
    
  }));
  it('should open dialog for delete on click for delete ', fakeAsync(() => {
    component.role ="ROLE_ADMIN";
    component.id = 1;
    expect(component).toBeTruthy();
    spyOn(dialog, 'open').and.callThrough();
    component.confirmDialog();
    expect(dialog.open).toHaveBeenCalled();
    flush();
  }));

  it('on open dialog for delete co on click delete should delete co', fakeAsync(() => {
    component.role ="ROLE_ADMIN";
    component.id = 1;
    expect(component).toBeTruthy();
    spyOn(dialog, 'open').and.callThrough();
    component.confirmDialog();
    tick();
    expect(dialog.open).toHaveBeenCalled();
    component.deleteCulturalOffer();
    expect(culturalOfferService.delete).toHaveBeenCalledWith(1);
    fixture.detectChanges();
    tick();
    expect(router.navigate).toHaveBeenCalledWith(['/']);
    flush();
  }));

  it('should open dialog for edit on click  ', fakeAsync(() => {
    expect(component).toBeTruthy();
    spyOn(dialog, 'open').and.callThrough();
    component.edit();
    expect(dialog.open).toHaveBeenCalled();
    flush();
    
  }));
  it('should open dialog for new post on click  ', fakeAsync(() => {
    expect(component).toBeTruthy();
    component.ngOnInit();
    let dialogRefSpyObj = jasmine.createSpyObj({ afterClosed : of({}), close: null });
    dialogRefSpyObj.componentInstance = { culturalOfferId: '' };
    spyOn(TestBed.get(MatDialog), 'open').and.returnValue(dialogRefSpyObj);
    component.addPost();
    expect(dialog.open).toHaveBeenCalled();
    flush();
    
  }));
 
  it('should to rate cultural offer', fakeAsync(() => {
    expect(component).toBeTruthy();
    component.ngOnInit(); //da se namesti id co
    component.role ="ROLE_REGISTERED_USER";
    component.rateClicked(1);
    expect(rateService.createOrEditRate).toHaveBeenCalled();
    tick();
    expect(culturalOfferService.getOne).toHaveBeenCalled();
    tick();
    fixture.detectChanges();
    tick();
    expect(culturalOfferService.announceChange).toHaveBeenCalled();
    flush();
    
  }));

  it('should subscribe user', fakeAsync(() => {
    expect(component).toBeTruthy();
    component.ngOnInit(); //da se namesti id co
    component.role ="ROLE_REGISTERED_USER";
    component.subscribeUser();
    expect(culturalOfferService.subscribeUser).toHaveBeenCalledWith(1);
    expect(toastr.success).toHaveBeenCalledTimes(1);
  }));

  it('should unsubscribe user', fakeAsync(() => {
    expect(component).toBeTruthy();
    component.ngOnInit(); //da se namesti id co
    component.role ="ROLE_REGISTERED_USER";
    component.unsubscribe();
    expect(culturalOfferService.unsubscribe).toHaveBeenCalledWith(1);
    expect(toastr.success).toHaveBeenCalledTimes(1);
    
  })); 
  

});
