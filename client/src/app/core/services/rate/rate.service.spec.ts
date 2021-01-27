import { TestBed, getTestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import {fakeAsync, tick} from '@angular/core/testing';
import { HttpClient } from '@angular/common/http';
import { RateService } from './rate.service';
import { Rate } from '../../model/Rate';

describe('TypeService', () => {
    let injector;
    let rateService: RateService;
    let httpMock: HttpTestingController;
    let httpClient: HttpClient;

    beforeEach(() => {

        TestBed.configureTestingModule({
            imports: [HttpClientTestingModule],
            providers:    [RateService ]
        });

        injector = getTestBed();
        rateService = TestBed.inject(RateService);
        httpClient = TestBed.inject(HttpClient);
        httpMock = TestBed.inject(HttpTestingController);
      });
    afterEach(() => {
        httpMock.verify();
      });

    it('createOrEditRate should return some types', fakeAsync(() => {
        let newRate: Rate = {
            id: 1,
            number: 4,
            registredUserId: 1,
            culturalOfferId: 1
        };

        const mockRate: Rate = {
            id: 1,
            number: 4,
            registredUserId: 1,
            culturalOfferId: 1
        };

        rateService.createOrEditRate(newRate).subscribe(data => {
          newRate = data;
        });

        const req = httpMock.expectOne('https://localhost:8443/api/rate/check');
        expect(req.request.method).toBe('POST');
        req.flush(mockRate);

        tick();
        expect(newRate.id).toEqual(1);
        expect(newRate.number).toEqual(4);
        expect(newRate.registredUserId).toEqual(1);
        expect(newRate.culturalOfferId).toEqual(1);
     }));

    it('should throw  error', () => {
      const newRate: Rate = {
        id: 1,
      number: 4,
        registredUserId: 1,
        culturalOfferId: 1
      };

      const mockRate: Rate = {
          id: 1,
        number: 4,
          registredUserId: 1,
          culturalOfferId: 1
      };
      let errorr = '';
      rateService.createOrEditRate(newRate).subscribe({error: (e) => errorr = e.statusText});
      const req = httpMock.expectOne('https://localhost:8443/api/rate/check');
      expect(req.request.method).toBe('POST');
      req.flush('Error on server', {
        status: 404,
        statusText: 'Error on server'
      });

      expect(errorr.toString().indexOf('Error on server') >= 0).toBeTruthy();
    });

});
