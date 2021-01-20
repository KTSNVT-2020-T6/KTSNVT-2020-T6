// import {ComponentFixture, TestBed } from '@angular/core/testing';
// import { ActivatedRoute, Router } from '@angular/router';
// import { By } from '@angular/platform-browser';
// import { DebugElement } from '@angular/core'
// import {async, fakeAsync, tick} from '@angular/core/testing';

// import { CulturalOfferDetailsComponent } from './cultural-offer-details.component';
// import { CulturalOfferDetailsService } from '../services/cultural-offer-details/cultural-offer-details.service';
// import { CulturalOffer } from '../model/CulturalOffer';

// import { of } from 'rxjs';
// import { ActivatedRouteStub } from 'src/app/testing/router-stubs';
// import { Category } from '../model/Category';
// import { Type } from '../model/Type';
// import { FormsModule, ReactiveFormsModule } from '@angular/forms';
// import { MatDialog } from '@angular/material/dialog';
// import {MatDialogModule} from '@angular/material/dialog';
// import { HttpClientModule } from '@angular/common/http';
// import { Toast, ToastContainerModule, ToastInjector, ToastrModule } from 'ngx-toastr';
// describe('CulturalOfferDetailsComponent', () => {
//   let component: CulturalOfferDetailsComponent;
//   let fixture: ComponentFixture<CulturalOfferDetailsComponent>;
//   let culturalOfferService: any;
//   let router: any;
//   var originalTimeout;
//   let activatedRoute: any;
  
//   beforeEach(() => {
//     originalTimeout = jasmine.DEFAULT_TIMEOUT_INTERVAL;
//     jasmine.DEFAULT_TIMEOUT_INTERVAL = 1000000;
//     const mockCategory: Category = {
//       id: 1,
//       name: 'category number 1',
//       description: 'this is a category no 1'
//       }
//     const mockType: Type = {
//           id: 1,
//           name: 'type number 1',
//           description: 'this is a type no 1',
//           categoryDTO: mockCategory
//     }
//     let culturalOffer: CulturalOffer = {
//       id: 1,
//       averageRate: 4.8,
//       description: 'desc',
//       name: 'co name',
//       city: 'belgrade',
//       date: new Date,
//       lat: 45.41,
//       lon: 21.16,
//       typeDTO: mockType
//     };
//     let culturalOfferServiceMock = {

//       getOne: jasmine.createSpy('getOne')
//         .and.returnValue(of({body: culturalOffer})), 
      
//       getFavorite: jasmine.createSpy('getFavorite')
//         .and.returnValue(of({body: [{},{},{}]})),

//       delete: jasmine.createSpy('delete')
//         .and.returnValue(of({})),

//       subscribeUser: jasmine.createSpy('subscribeUser')
//         .and.returnValue(of()),
//       unsubscribe: jasmine.createSpy('unsubscribe')
//         .and.returnValue(of()),

//       RegenerateData$: {
//         subscribe: jasmine.createSpy('subscribe')
//       }
//     };
//     let routerMock = {
//       navigate: jasmine.createSpy('navigate')
//     };
//     let activatedRouteStub: ActivatedRouteStub = new ActivatedRouteStub();
//     activatedRouteStub.testParams = { id: 1 }; // we edit a student with id 1. Its id is in route url

//     TestBed.configureTestingModule({
//        declarations: [ CulturalOfferDetailsComponent ],
//        imports: [FormsModule, ReactiveFormsModule, MatDialogModule, HttpClientModule, ToastrModule.forRoot()],
//        providers:    [ {provide: CulturalOfferDetailsService, useValue: culturalOfferServiceMock },
//                       { provide: ActivatedRoute, useValue: activatedRouteStub },
//                        { provide: Router, useValue: routerMock } ]
//     });

//     fixture = TestBed.createComponent(CulturalOfferDetailsComponent);
//     component    = fixture.componentInstance;
//     culturalOfferService = TestBed.inject(CulturalOfferDetailsService);
//     activatedRoute = TestBed.inject(ActivatedRoute);
//     router = TestBed.inject(Router);
//     //location = TestBed.inject(Location);
//   });


//   it('should fetch the cultural offer on init', async(() => {
//     // component.ngOnInit();
//     // // should subscribe on RegenerateData
//     // expect(culturalOfferService.RegenerateData$.subscribe).toHaveBeenCalled();
//     // expect(culturalOfferService.getOne).toHaveBeenCalledWith(1); // we fetch student 1 since route param is 1
   
//     // fixture.detectChanges(); // tell angular that data are fetched
   
//     // fixture.detectChanges(); // detect changes in the HTML components
//     // // assert that values in the HTML components are as expected
//     // fixture.whenStable()
//     //   .then(() => {
//     //     fixture.detectChanges(); // synchronize HTML with component data        
//     //     let averageRate = 
//     //       fixture.debugElement.query(By.css('#averageRate')).nativeElement;
//     //     expect(averageRate.value).toBe(4.8); // header tr plus one tr for each student
//     //   });
//   }));
  
// });
