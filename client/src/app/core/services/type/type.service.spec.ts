import { TestBed, getTestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import {fakeAsync, tick} from '@angular/core/testing';
import { HttpClient } from '@angular/common/http';
import { Category } from '../../model/Category';
import { TypeService } from './type.service';
import { Type } from '../../model/Type';

describe('TypeService', () => {
    let injector;
    let typeService: TypeService;
    let httpMock: HttpTestingController;
    let httpClient: HttpClient;

    beforeEach(() => {

        TestBed.configureTestingModule({
            imports: [HttpClientTestingModule],
            providers:    [TypeService ]
        });
    
        injector = getTestBed();
        typeService = TestBed.inject(TypeService);
        httpClient = TestBed.inject(HttpClient);
        httpMock = TestBed.inject(HttpTestingController);
      });
      afterEach(() => {
        httpMock.verify();
      });

      it('getAll() should return some types',fakeAsync(() => {
        let types: Type[] = [];
        
        const mockTypes: Type[] = [
            {
                id: 1,
                name: "theatar",
                description: "Srpsko narodno pozoriste",
                categoryDTO: {
                    id: 1,
                    name: "institution",
                    description: "institution in serbia"
                }
            },
            {
                id: 2,
                name: "museum",
                description: "Muzej Nikole Tesle",
                categoryDTO: {
                    id: 1,
                    name: "institution",
                    description: "institution in serbia"
                }
            }];
        
        typeService.getAll().subscribe(data => {
          types = data.body;        
        });
        
        const req = httpMock.expectOne('https://localhost:8443/api/type');
        expect(req.request.method).toBe('GET');
        req.flush(mockTypes);
    
        tick();
    
        expect(types.length).toEqual(2);
        expect(types[0].id).toEqual(1);
        expect(types[0].name).toEqual('theatar');
        expect(types[0].description).toEqual('Srpsko narodno pozoriste');
        expect(types[0].categoryDTO?.id).toEqual(1);
        expect(types[0].categoryDTO?.name).toEqual('institution');
        expect(types[0].categoryDTO?.description).toEqual('institution in serbia');
    
        expect(types[1].id).toEqual(2);
        expect(types[1].name).toEqual('museum');
        expect(types[1].description).toEqual('Muzej Nikole Tesle');
        expect(types[1].categoryDTO?.id).toEqual(1);
        expect(types[1].categoryDTO?.name).toEqual('institution');
        expect(types[1].categoryDTO?.description).toEqual('institution in serbia');
       
     }));

     it('getTypesOfCategory() should return some types',fakeAsync(() => {
        let types: Type[] = [];
        
        const mockTypes: Type[] = [
            {
                id: 1,
                name: "theatar",
                description: "Srpsko narodno pozoriste",
                categoryDTO: {
                    id: 1,
                    name: "institution",
                    description: "institution in serbia"
                }
            },
            {
                id: 2,
                name: "museum",
                description: "Muzej Nikole Tesle",
                categoryDTO: {
                    id: 1,
                    name: "institution",
                    description: "institution in serbia"
                }
            }];
        
        typeService.getTypesOfCategory(1).subscribe(data => {
          types = data.body;        
        });
        
        const req = httpMock.expectOne('https://localhost:8443/api/type/category/1');
        expect(req.request.method).toBe('GET');
        req.flush(mockTypes);
    
        tick();
    
        expect(types.length).toEqual(2);
        expect(types[0].id).toEqual(1);
        expect(types[0].name).toEqual('theatar');
        expect(types[0].description).toEqual('Srpsko narodno pozoriste');
        expect(types[0].categoryDTO?.id).toEqual(1);
        expect(types[0].categoryDTO?.name).toEqual('institution');
        expect(types[0].categoryDTO?.description).toEqual('institution in serbia');
    
        expect(types[1].id).toEqual(2);
        expect(types[1].name).toEqual('museum');
        expect(types[1].description).toEqual('Muzej Nikole Tesle');
        expect(types[1].categoryDTO?.id).toEqual(1);
        expect(types[1].categoryDTO?.name).toEqual('institution');
        expect(types[1].categoryDTO?.description).toEqual('institution in serbia');
       
     }));

     it('add()  should query url and save a type',fakeAsync(() => {
      let newType: Type = {
        name: "theatar",
        description: "Srpsko narodno pozoriste",
        categoryDTO: {
            id: 1,
            name: "institution",
            description: "institution in serbia"
        }
      }
      
      const mockType: Type = 
      {
        id: 1,
        name: "theatar",
        description: "Srpsko narodno pozoriste",
        categoryDTO: {
            id: 1,
            name: "institution",
            description: "institution in serbia"
        }
    };
      
    typeService.add(newType).subscribe(res => newType = res);
      
      
    const req = httpMock.expectOne('https://localhost:8443/api/type');
    expect(req.request.method).toBe('POST');
    req.flush(mockType);
  
    tick();
  
    expect(newType).toBeDefined();
     
    expect(newType.id).toEqual(1);
    expect(newType.name).toEqual('theatar');
    expect(newType.description).toEqual('Srpsko narodno pozoriste');
    expect(newType.categoryDTO?.id).toEqual(1);
    expect(newType.categoryDTO?.name).toEqual('institution');
    expect(newType.categoryDTO?.description).toEqual('institution in serbia');
   }));

   it('getType() should query url and get type',fakeAsync(() => {
    let type: Type = {};
    
    const mockCategory: Type = 
        {
            id: 1,
            name: "theatar",
            description: "Srpsko narodno pozoriste",
            categoryDTO: {
                id: 1,
                name: "institution",
                description: "institution in serbia"
            }
        };
    
    typeService.getType(1).subscribe(res => type = res.body);
    
    
    const req = httpMock.expectOne('https://localhost:8443/api/type/1');
    expect(req.request.method).toBe('GET');
    req.flush(mockCategory);

    tick();

    expect(type).toBeDefined();
     
    expect(type.id).toEqual(1);
    expect(type.name).toEqual('theatar');
    expect(type.description).toEqual('Srpsko narodno pozoriste');
    expect(type.categoryDTO?.id).toEqual(1);
    expect(type.categoryDTO?.name).toEqual('institution');
    expect(type.categoryDTO?.description).toEqual('institution in serbia');
    }));

    it('update() should query url and edit a type', fakeAsync(() => {
        let type: Type = {
            name: "theatar",
            description: "Srpsko narodno pozoriste",
            categoryDTO: {
                id: 1,
                name: "institution",
                description: "institution in serbia"
            }
        }
        const mockCategory: Type = 
        {
            id: 1,
            name: "theatar",
            description: "Srpsko narodno pozoriste",
            categoryDTO: {
                id: 1,
                name: "institution",
                description: "institution in serbia"
            }
        };
      
        typeService.update(type, 1).subscribe(res => type = res
        );
      
        const req = httpMock.expectOne('https://localhost:8443/api/type/1');
        expect(req.request.method).toBe('PUT');
        req.flush(mockCategory);
      
        tick();
        expect(type.id).toEqual(1);
        expect(type.name).toEqual('theatar');
        expect(type.description).toEqual('Srpsko narodno pozoriste');
        expect(type.categoryDTO?.id).toEqual(1);
        expect(type.categoryDTO?.name).toEqual('institution');
        expect(type.categoryDTO?.description).toEqual('institution in serbia');
    }));
  
     it('delete() should query url and delete a type', () => {
      typeService.delete(1).subscribe(res => { });
      
      const req = httpMock.expectOne('https://localhost:8443/api/type/1');
      expect(req.request.method).toBe('DELETE');
      req.flush({});
    });

    it("should throw error on get all types",()=> {

        let error:string = '';
        typeService.getAll().subscribe(null,e => {
          error = e.statusText;
        });
        const req = httpMock.expectOne('https://localhost:8443/api/type');
        expect(req.request.method).toBe('GET');
        req.flush("Error on server",{
          status: 404,
          statusText: 'Error on server'
        });
    });

    it("should throw error on get types of category",()=> {

        let error:string = '';
        typeService.getTypesOfCategory(1).subscribe(null,e => {
          error = e.statusText;
        });
        const req = httpMock.expectOne('https://localhost:8443/api/type/category/1');
        expect(req.request.method).toBe('GET');
        req.flush("Error on server",{
          status: 404,
          statusText: 'Error on server'
        });
    });

    it("should throw error on add type",()=> {
        let newType: Type = {
            name: "theatar",
            description: "Srpsko narodno pozoriste",
            categoryDTO: {
                id: 1,
                name: "institution",
                description: "institution in serbia"
            }
          }
        let error:string = '';
        typeService.add(newType).subscribe(null,e => {
          error = e.statusText;
        });
        const req = httpMock.expectOne('https://localhost:8443/api/type');
        expect(req.request.method).toBe('POST');
        req.flush("Error on server",{
          status: 404,
          statusText: 'Error on server'
        });
    });

    it("should throw error on get type",()=> {

        let error:string = '';
        typeService.getType(1).subscribe(null,e => {
          error = e.statusText;
        });
        const req = httpMock.expectOne('https://localhost:8443/api/type/1');
        expect(req.request.method).toBe('GET');
        req.flush("Error on server",{
          status: 404,
          statusText: 'Error on server'
        });
    });

    it("should throw error on update type",()=> {
        let type: Type = {
            id:1,
            name: "theatar",
            description: "Srpsko narodno pozoriste",
            categoryDTO: {
                id: 1,
                name: "institution",
                description: "institution in serbia"
            }
        }
        let error:string = '';
        typeService.update(type, 1).subscribe(null,e => {
          error = e.statusText;
        });
        const req = httpMock.expectOne('https://localhost:8443/api/type/1');
        expect(req.request.method).toBe('PUT');
        req.flush("Error on server",{
          status: 404,
          statusText: 'Error on server'
        });
    });

    
    it("should throw error on delete type",()=> {
        let error:string = '';
        typeService.delete(1).subscribe(null,e => {
          error = e.statusText;
        });
        const req = httpMock.expectOne('https://localhost:8443/api/type/1');
        expect(req.request.method).toBe('DELETE');
        req.flush("Error on server",{
          status: 404,
          statusText: 'Error on server'
        });
    });
    
})
