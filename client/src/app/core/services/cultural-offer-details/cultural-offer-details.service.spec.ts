import { TestBed, getTestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { fakeAsync, tick} from '@angular/core/testing';
import { HttpClient } from '@angular/common/http';

import { CulturalOfferDetailsService } from './cultural-offer-details.service';
import { CulturalOffer } from '../../model/CulturalOffer';
import { Type } from '../../model/Type';
import { Category } from '../../model/Category';

describe('CulturalOfferDetailsService', () => {
  let injector;
  let culturalOfferDetailsService: CulturalOfferDetailsService;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;

  beforeEach(() => {

    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
       providers:    [CulturalOfferDetailsService]
    });

    injector = getTestBed();
    culturalOfferDetailsService = TestBed.inject(CulturalOfferDetailsService);
    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('getOne() should query url and get cultural offer by id', fakeAsync(() => {
    let culturalOffer!: CulturalOffer;

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

    culturalOfferDetailsService.getOne(1).subscribe(res => culturalOffer = res.body);

    const req = httpMock.expectOne('https://localhost:8443/api/cultural-offer/1');
    expect(req.request.method).toBe('GET');
    req.flush(mockCulturalOffer);
    tick();

    expect(culturalOffer).toBeDefined();
    expect(culturalOffer.id).toEqual(1);
    expect(culturalOffer.name).toEqual('co name');
    expect(culturalOffer.description).toEqual('desc');
    expect(culturalOffer.city).toEqual('belgrade');
    expect(culturalOffer.lat).toEqual(45.41);
    expect(culturalOffer.lon).toEqual(21.16);
    expect(culturalOffer.typeDTO).toEqual(mockType);
    expect(culturalOffer.averageRate).toEqual(5);
  }));

  it('getPage() should query url and get cultural offer page', fakeAsync(() => {
    let culturalOffers!: CulturalOffer[];

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
    const mockCulturalOffers: CulturalOffer[] = [
        {
            id: 1,
            averageRate: 5,
            description: 'desc',
            name: 'co name',
            city: 'belgrade',
            date: new Date(),
            lat: 45.41,
            lon: 21.16,
            typeDTO: mockType
        },
        {
            id: 2,
            averageRate: 3,
            description: 'desc2',
            name: 'co name2',
            city: 'belgrade',
            date: new Date(),
            lat: 44.41,
            lon: 20.16,
            typeDTO: mockType
        }
    ];

    culturalOfferDetailsService.getPage(0, 2).subscribe(res => culturalOffers = res.body);

    const req = httpMock.expectOne('https://localhost:8443/api/cultural-offer/?page=0&size=2');
    expect(req.request.method).toBe('GET');
    req.flush(mockCulturalOffers);
    tick();

    expect(culturalOffers.length).toEqual(2, 'should contain given amount of cultural offers');
    expect(culturalOffers[0].id).toEqual(1);
    expect(culturalOffers[0].name).toEqual('co name');
    expect(culturalOffers[0].description).toEqual('desc');
    expect(culturalOffers[0].city).toEqual('belgrade');
    expect(culturalOffers[0].lat).toEqual(45.41);
    expect(culturalOffers[0].lon).toEqual(21.16);
    expect(culturalOffers[0].typeDTO).toEqual(mockType);
    expect(culturalOffers[0].averageRate).toEqual(5);

    expect(culturalOffers[1].id).toEqual(2);
    expect(culturalOffers[1].name).toEqual('co name2');
    expect(culturalOffers[1].description).toEqual('desc2');
    expect(culturalOffers[1].city).toEqual('belgrade');
    expect(culturalOffers[1].lat).toEqual(44.41);
    expect(culturalOffers[1].lon).toEqual(20.16);
    expect(culturalOffers[1].typeDTO).toEqual(mockType);
    expect(culturalOffers[1].averageRate).toEqual(3);
  }));

  it('getFavorite() should query url and get favorite cultural offers for current user', fakeAsync(() => {
    let culturalOffers!: CulturalOffer[];

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
    const mockCulturalOffers: CulturalOffer[] = [
        {
            id: 1,
            averageRate: 5,
            description: 'desc',
            name: 'co name',
            city: 'belgrade',
            date: new Date(),
            lat: 45.41,
            lon: 21.16,
            typeDTO: mockType
        },
        {
            id: 2,
            averageRate: 3,
            description: 'desc2',
            name: 'co name2',
            city: 'belgrade',
            date: new Date(),
            lat: 44.41,
            lon: 20.16,
            typeDTO: mockType
        }
    ];

    culturalOfferDetailsService.getFavorite().subscribe(res => culturalOffers = res.body);

    const req = httpMock.expectOne('https://localhost:8443/api/cultural-offer/find/subscriptions');
    expect(req.request.method).toBe('GET');
    req.flush(mockCulturalOffers);
    tick();

    expect(culturalOffers.length).toEqual(2, 'should contain given amount of cultural offers');
    expect(culturalOffers[0].id).toEqual(1);
    expect(culturalOffers[0].name).toEqual('co name');
    expect(culturalOffers[0].description).toEqual('desc');
    expect(culturalOffers[0].city).toEqual('belgrade');
    expect(culturalOffers[0].lat).toEqual(45.41);
    expect(culturalOffers[0].lon).toEqual(21.16);
    expect(culturalOffers[0].typeDTO).toEqual(mockType);
    expect(culturalOffers[0].averageRate).toEqual(5);

    expect(culturalOffers[1].id).toEqual(2);
    expect(culturalOffers[1].name).toEqual('co name2');
    expect(culturalOffers[1].description).toEqual('desc2');
    expect(culturalOffers[1].city).toEqual('belgrade');
    expect(culturalOffers[1].lat).toEqual(44.41);
    expect(culturalOffers[1].lon).toEqual(20.16);
    expect(culturalOffers[1].typeDTO).toEqual(mockType);
    expect(culturalOffers[1].averageRate).toEqual(3);
  }));

  it('searchCombined() should query url and get search result page', fakeAsync(() => {
    let culturalOffers!: CulturalOffer[];

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
    const mockCulturalOffers: CulturalOffer[] = [
        {
            id: 1,
            averageRate: 5,
            description: 'desc',
            name: 'co name',
            city: 'belgrade',
            date: new Date(),
            lat: 45.41,
            lon: 21.16,
            typeDTO: mockType
        },
        {
            id: 2,
            averageRate: 3,
            description: 'desc2',
            name: 'co name2',
            city: 'belgrade',
            date: new Date(),
            lat: 44.41,
            lon: 20.16,
            typeDTO: mockType
        }
    ];

    culturalOfferDetailsService.searchCombined(0, 2, 'co', 'belg').subscribe(res => culturalOffers = res.body);

    const req = httpMock.expectOne('https://localhost:8443/api/cultural-offer/combined/co_belg?page=0&size=2');
    expect(req.request.method).toBe('GET');
    req.flush(mockCulturalOffers);
    tick();

    expect(culturalOffers.length).toEqual(2, 'should contain given amount of cultural offers');
    expect(culturalOffers[0].id).toEqual(1);
    expect(culturalOffers[0].name).toEqual('co name');
    expect(culturalOffers[0].description).toEqual('desc');
    expect(culturalOffers[0].city).toEqual('belgrade');
    expect(culturalOffers[0].lat).toEqual(45.41);
    expect(culturalOffers[0].lon).toEqual(21.16);
    expect(culturalOffers[0].typeDTO).toEqual(mockType);
    expect(culturalOffers[0].averageRate).toEqual(5);

    expect(culturalOffers[1].id).toEqual(2);
    expect(culturalOffers[1].name).toEqual('co name2');
    expect(culturalOffers[1].description).toEqual('desc2');
    expect(culturalOffers[1].city).toEqual('belgrade');
    expect(culturalOffers[1].lat).toEqual(44.41);
    expect(culturalOffers[1].lon).toEqual(20.16);
    expect(culturalOffers[1].typeDTO).toEqual(mockType);
    expect(culturalOffers[1].averageRate).toEqual(3);
  }));

  it('add() should query url and create new cultural offer', fakeAsync(() => {
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

    let culturalOffer: CulturalOffer = {
        averageRate: 5,
        description: 'desc',
        name: 'co name',
        city: 'belgrade',
        date: new Date(),
        lat: 45.41,
        lon: 21.16,
        typeDTO: mockType
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

    culturalOfferDetailsService.add(culturalOffer).subscribe(res => culturalOffer = res);

    const req = httpMock.expectOne('https://localhost:8443/api/cultural-offer');
    expect(req.request.method).toBe('POST');
    req.flush(mockCulturalOffer);
    tick();

    expect(culturalOffer).toBeDefined();
    expect(culturalOffer.id).toEqual(1);
    expect(culturalOffer.name).toEqual('co name');
    expect(culturalOffer.description).toEqual('desc');
    expect(culturalOffer.city).toEqual('belgrade');
    expect(culturalOffer.lat).toEqual(45.41);
    expect(culturalOffer.lon).toEqual(21.16);
    expect(culturalOffer.typeDTO).toEqual(mockType);
    expect(culturalOffer.averageRate).toEqual(5);

  }));

  it('edit() should query url and edit cultural offer', fakeAsync(() => {
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

    let culturalOffer: CulturalOffer = {
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

    culturalOfferDetailsService.edit(culturalOffer).subscribe(res => culturalOffer = res);

    const req = httpMock.expectOne('https://localhost:8443/api/cultural-offer/1');
    expect(req.request.method).toBe('PUT');
    req.flush(mockCulturalOffer);
    tick();

    expect(culturalOffer).toBeDefined();
    expect(culturalOffer.id).toEqual(1);
    expect(culturalOffer.name).toEqual('co name');
    expect(culturalOffer.description).toEqual('desc');
    expect(culturalOffer.city).toEqual('belgrade');
    expect(culturalOffer.lat).toEqual(45.41);
    expect(culturalOffer.lon).toEqual(21.16);
    expect(culturalOffer.typeDTO).toEqual(mockType);
    expect(culturalOffer.averageRate).toEqual(5);

  }));

  it('delete() should query url and delete cultural offer', fakeAsync(() => {
    culturalOfferDetailsService.delete(1).subscribe(res => {});

    const req = httpMock.expectOne('https://localhost:8443/api/cultural-offer/1');
    expect(req.request.method).toBe('DELETE');
    req.flush({});
  }));

  it('subscribeUser() should query url and subscribe current user to cultural offer', fakeAsync(() => {
    culturalOfferDetailsService.subscribeUser(1).subscribe(res => {});

    const req = httpMock.expectOne('https://localhost:8443/api/cultural-offer/subscribe/1');
    expect(req.request.method).toBe('PUT');
    req.flush({});
  }));

  it('unsubscribe() should query url and unsubscribe current user from cultural offer', fakeAsync(() => {
    culturalOfferDetailsService.unsubscribe(1).subscribe(res => {});

    const req = httpMock.expectOne('https://localhost:8443/api/cultural-offer/unsubscribe/1');
    expect(req.request.method).toBe('PUT');
    req.flush({});
  }));


  it('should throw get one error', () => {
    let errorr = '';
    culturalOfferDetailsService.getOne(1).subscribe({error: (e) => errorr = e.statusText});
    const req = httpMock.expectOne('https://localhost:8443/api/cultural-offer/1');
    expect(req.request.method).toBe('GET');
    req.flush('Bad request', {
      status: 400,
      statusText: 'Bad request'
    });

    expect(errorr.toString().indexOf('Bad request') >= 0).toBeTruthy();
  });

  it('should throw get page error', () => {
    let errorr = '';
    culturalOfferDetailsService.getPage(0, 2).subscribe({error: (e) => errorr = e.statusText});
    const req = httpMock.expectOne('https://localhost:8443/api/cultural-offer/?page=0&size=2');
    expect(req.request.method).toBe('GET');
    req.flush('Bad request', {
      status: 400,
      statusText: 'Bad request'
    });

    expect(errorr.toString().indexOf('Bad request') >= 0).toBeTruthy();
  });

  it('should throw get favorites error', () => {
    let errorr = '';
    culturalOfferDetailsService.getFavorite().subscribe({error: (e) => errorr = e.statusText});
    const req = httpMock.expectOne('https://localhost:8443/api/cultural-offer/find/subscriptions');
    expect(req.request.method).toBe('GET');
    req.flush('Bad request', {
      status: 400,
      statusText: 'Bad request'
    });

    expect(errorr.toString().indexOf('Bad request') >= 0).toBeTruthy();
  });

  it('should throw get search combined error', () => {
    let errorr = '';
    culturalOfferDetailsService.searchCombined(0, 2, 'lala', 'la').subscribe({error: (e) => errorr = e.statusText});
    const req = httpMock.expectOne('https://localhost:8443/api/cultural-offer/combined/lala_la?page=0&size=2');
    expect(req.request.method).toBe('GET');
    req.flush('Bad request', {
      status: 400,
      statusText: 'Bad request'
    });

    expect(errorr.toString().indexOf('Bad request') >= 0).toBeTruthy();
  });

  it('should throw add error', () => {
    const culturalOffer: CulturalOffer = {
        averageRate: 5,
        description: 'desc',
        name: 'co name',
        city: 'belgrade',
        date: new Date(),
        lat: 45.41,
        lon: 21.16,
        typeDTO: undefined
    };
    let errorr = '';
    culturalOfferDetailsService.add(culturalOffer).subscribe({error: (e) => errorr = e.statusText});
    const req = httpMock.expectOne('https://localhost:8443/api/cultural-offer');
    expect(req.request.method).toBe('POST');
    req.flush('Bad request', {
      status: 400,
      statusText: 'Bad request'
    });

    expect(errorr.toString().indexOf('Bad request') >= 0).toBeTruthy();
  });

  it('should throw edit error', () => {
    const culturalOffer: CulturalOffer = {
        id: 1,
        averageRate: 5,
        description: 'desc',
        name: 'co name',
        city: 'belgrade',
        date: new Date(),
        lat: 45.41,
        lon: 21.16,
        typeDTO: undefined
    };
    let errorr = '';
    culturalOfferDetailsService.edit(culturalOffer).subscribe({error: (e) => errorr = e.statusText});
    const req = httpMock.expectOne('https://localhost:8443/api/cultural-offer/1');
    expect(req.request.method).toBe('PUT');
    req.flush('Bad request', {
      status: 400,
      statusText: 'Bad request'
    });

    expect(errorr.toString().indexOf('Bad request') >= 0).toBeTruthy();
  });

  it('should throw delete error', () => {
    let errorr = '';
    culturalOfferDetailsService.delete(1).subscribe({error: (e) => errorr = e.statusText});
    const req = httpMock.expectOne('https://localhost:8443/api/cultural-offer/1');
    expect(req.request.method).toBe('DELETE');
    req.flush('Bad request', {
      status: 400,
      statusText: 'Bad request'
    });

    expect(errorr.toString().indexOf('Bad request') >= 0).toBeTruthy();
  });

  it('should throw subscribe user error', () => {
    let errorr = '';
    culturalOfferDetailsService.subscribeUser(1).subscribe({error: (e) => errorr = e.statusText});
    const req = httpMock.expectOne('https://localhost:8443/api/cultural-offer/subscribe/1');
    expect(req.request.method).toBe('PUT');
    req.flush('Bad request', {
      status: 400,
      statusText: 'Bad request'
    });

    expect(errorr.toString().indexOf('Bad request') >= 0).toBeTruthy();
  });

  it('should throw unsubscribe user error', () => {
    let errorr = '';
    culturalOfferDetailsService.unsubscribe(1).subscribe({error: (e) => errorr = e.statusText});
    const req = httpMock.expectOne('https://localhost:8443/api/cultural-offer/unsubscribe/1');
    expect(req.request.method).toBe('PUT');
    req.flush('Bad request', {
      status: 400,
      statusText: 'Bad request'
    });

    expect(errorr.toString().indexOf('Bad request') >= 0).toBeTruthy();
  });

});
