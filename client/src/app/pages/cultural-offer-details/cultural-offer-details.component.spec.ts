import {ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core'
import {async, fakeAsync, tick} from '@angular/core/testing';

import { CulturalOfferDetailsComponent } from './cultural-offer-details.component';
import { CulturalOfferDetailsService } from '../services/cultural-offer-details/cultural-offer-details.service';
import { CulturalOffer } from '../model/CulturalOffer';

import { of } from 'rxjs';
import { ActivatedRouteStub } from 'src/app/testing/router-stubs';

describe('CulturalOfferDetailsComponent', () => {
  let component: CulturalOfferDetailsComponent;
  let fixture: ComponentFixture<CulturalOfferDetailsComponent>;
  let culturalOfferService: any;
  let router: any;
  
  beforeEach(() => {
    let culturalOfferServiceMock = {

      getCulturalOffer: jasmine.createSpy('getCulturalOffer')
        .and.returnValue(of({body: {}})), 

      getNumberOfSubscribed: jasmine.createSpy('getNumberOfSubscribed')
        .and.returnValue(of({body: []})),

      rateClicked: jasmine.createSpy('rateClicked'),

      getRole: jasmine.createSpy('getRole'),

      addPost: jasmine.createSpy('addPost'),

      deleteCulturalOffer: jasmine.createSpy('deleteCulturalOffer')
        .and.returnValue(of()),

      onFileSelect: jasmine.createSpy('onFileSelect')
        .and.returnValue(of({body: [{}, {}, {}]})),

      edit: jasmine.createSpy('edit'),
      
      addNewComment: jasmine.createSpy('addNewComment')
        .and.returnValue(of({body: [{}, {}, {}]})),

      confirmDialog: jasmine.createSpy('confirmDialog'),

      checkSubscription: jasmine.createSpy('checkSubscription'),

      subscribeUser: jasmine.createSpy('subscribeUser')
        .and.returnValue(of({body: [{}, {}, {}]})),
      unsubscribe: jasmine.createSpy('unsubscribe')
        .and.returnValue(of({body: [{}, {}, {}]})),
      RegenerateData$: {
        subscribe: jasmine.createSpy('subscribe')
      }
    };
    let routerMock = {
      navigate: jasmine.createSpy('navigate')
    };
    let activatedRouteStub: ActivatedRouteStub = new ActivatedRouteStub();
    activatedRouteStub.testParams = { id: 1 }; // we edit a student with id 1. Its id is in route url

    TestBed.configureTestingModule({
       declarations: [ CulturalOfferDetailsComponent ],
       providers:    [ {provide: CulturalOfferDetailsService, useValue: culturalOfferServiceMock },
                       { provide: Router, useValue: routerMock } ]
    });

    fixture = TestBed.createComponent(CulturalOfferDetailsComponent);
    component    = fixture.componentInstance;
    culturalOfferService = TestBed.inject(CulturalOfferDetailsService);
    router = TestBed.inject(Router);
  });
  
});
