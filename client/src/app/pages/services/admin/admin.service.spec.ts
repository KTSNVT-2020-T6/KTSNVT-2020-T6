import { TestBed, getTestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { fakeAsync, tick} from '@angular/core/testing';
import { HttpClient } from '@angular/common/http';

import { User } from "../../model/User";
import { UserService } from '../../services/user/user.service';
import { AdminService } from '../../services/admin/admin.service';

describe('AdminService', () => {
  let injector;
  let adminService: AdminService;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;

  beforeEach(() => {

    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
       providers:    [AdminService]
    });

    injector = getTestBed();
    adminService = TestBed.inject(AdminService);
    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
  });
  
  afterEach(() => {
    httpMock.verify();
  });

  it('editAdmin() should query url and edit an admin', fakeAsync(() => {
    let user: User = {  
      id: 1,
      firstName: 'Jovan',
      lastName: 'Jovic',
      email: 'jova@gmail.com',
      password: 'asdf',
      active:  true,
      verified: true,
      idImageDTO: 1,
      src: '' 
    };

    const mockUser: User = 
    {
      id: 1,
      firstName: 'Jovan',
      lastName: 'Jovic',
      email: 'jova@gmail.com',
      password: 'asdf',
      active:  true,
      verified: true,
      idImageDTO: 1 ,
      src: ''      
    };
    
    adminService.editAdmin(user).subscribe(res => user = res);
    
    const req = httpMock.expectOne('http://localhost:8080/api/admin/1');
    expect(req.request.method).toBe('PUT');
    req.flush(mockUser);
    tick();
    console.log(user);
    expect(user).toBeDefined();
    expect(user.id).toEqual(1);
    expect(user.firstName).toEqual('Jovan');
    expect(user.lastName).toEqual('Jovic');
    expect(user.email).toEqual('jova@gmail.com');
    expect(user.password).toEqual('asdf');
    expect(user.idImageDTO).toEqual(1);
  }));

   it('delete() should query url and delete an admin', () => {
      adminService.delete(1).subscribe(res => { });
      const req = httpMock.expectOne('http://localhost:8080/api/admin/1');
      expect(req.request.method).toBe('DELETE');
      req.flush({});

      expect('OK').toBeDefined();
  });

});