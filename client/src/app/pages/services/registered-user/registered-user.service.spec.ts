import { TestBed, getTestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { fakeAsync, tick} from '@angular/core/testing';
import { HttpClient } from '@angular/common/http';

import { User } from "../../model/User";
import { UserService } from '../../services/user/user.service';
import { RegisteredUserService } from '../../services/registered-user/registered-user.service';

describe('UserService', () => {
  let injector;
  let registeredUserService: RegisteredUserService;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;

  beforeEach(() => {

    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
       providers:    [RegisteredUserService]
    });

    injector = getTestBed();
    registeredUserService = TestBed.inject(RegisteredUserService);
    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
  });
  
  afterEach(() => {
    httpMock.verify();
  });

  it('editUser() should query url and edit an registered user', fakeAsync(() => {
    let user: User = {  
      id: 1,
      firstName: 'Stefan',
      lastName: 'Stefic',
      email: 'stefa@gmail.com',
      password: 'asdf',
      active:  true,
      verified: true,
      idImageDTO: 1,
      src: '' 
    };

    const mockUser: User = 
    {
      id: 1,
      firstName: 'Stefan',
      lastName: 'Stefic',
      email: 'stefa@gmail.com',
      password: 'asdf',
      active:  true,
      verified: true,
      idImageDTO: 1,
      src: ''     
    };
    
    registeredUserService.editUser(user).subscribe(res => user = res);
    
    const req = httpMock.expectOne('http://localhost:8080/api/registered_user/1');
    expect(req.request.method).toBe('PUT');
    req.flush(mockUser);
    tick();
 
    expect(user).toBeDefined();
    expect(user.id).toEqual(1);
    expect(user.firstName).toEqual('Stefan');
    expect(user.lastName).toEqual('Stefic');
    expect(user.email).toEqual('stefa@gmail.com');
    expect(user.password).toEqual('asdf');
    expect(user.idImageDTO).toEqual(1);
  }));

  it('delete() should query url and delete an registered user', () => {
    registeredUserService.delete(1).subscribe(res => { });
    const req = httpMock.expectOne('http://localhost:8080/api/registered_user/1');
    expect(req.request.method).toBe('DELETE');
    req.flush({});

    expect('OK').toBeDefined();
  });
  
  it('getNumberOfSubscribed() should query url and get the number of subscribed users', fakeAsync(() => {
    let num = 2;
    let mockNum = 2;
    
    registeredUserService.getNumberOfSubscribed(1).subscribe(res => num = res.body);
    const req = httpMock.expectOne('http://localhost:8080/api/registered_user/interested/1');
    expect(req.request.method).toBe('GET');
    req.flush(mockNum);

    tick();
    expect(num).toEqual(2);
    
  }));

  it('announceChange() should emit the event RegenerateData', fakeAsync(() => {
    let counter: number = 0;

    registeredUserService.RegenerateData$.subscribe(() =>  counter++);
    registeredUserService.announceChange();
    tick();

    expect(counter).toBe(1);
  }));
});