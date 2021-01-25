import { TestBed, getTestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { fakeAsync, tick} from '@angular/core/testing';
import { HttpClient } from '@angular/common/http';

import { User } from "../../model/User";
import { UserService } from '../user/user.service';
import { RegisteredUserService } from '../registered-user/registered-user.service';
import { VerificationService } from './verification.service';

describe('VerificationService', () => {
  let injector;
  let verificationService: VerificationService;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;

  beforeEach(() => {

    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
       providers:    [VerificationService]
    });

    injector = getTestBed();
    verificationService = TestBed.inject(VerificationService);
    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
  });
  
  afterEach(() => {
    httpMock.verify();
  });

  
  it('verify() should query url and verify user', () => {
    verificationService.verify('verificationtokenvalue').subscribe(res => { });
    const req = httpMock.expectOne('https://localhost:8443/api/verification/verificationtokenvalue');
    expect(req.request.method).toBe('GET');
    req.flush({});
  });
  
  it("should throw  error",()=> {
    let error:string = '';
    verificationService.verify('verificationtokenvalue').subscribe(null,e => {
      error = e.statusText;
    });
    const req = httpMock.expectOne('https://localhost:8443/api/verification/verificationtokenvalue');
    expect(req.request.method).toBe('GET');
    req.flush("Error on server",{
      status: 404,
      statusText: 'Error on server'
    });
   
    expect(error.toString().indexOf("Error on server") >= 0).toBeTruthy();
  });
  
});