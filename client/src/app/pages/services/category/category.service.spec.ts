
import { TestBed, getTestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import {fakeAsync, tick} from '@angular/core/testing';
import { HttpClient } from '@angular/common/http';
import { CategoryService } from './category.service';
import { Category } from '../../model/Category';
/*
getAll
add
getCategory
update
delete
*/
describe('CategoryService', () => {
    let injector;
    let categoryService: CategoryService;
    let httpMock: HttpTestingController;
    let httpClient: HttpClient;

    beforeEach(() => {

        TestBed.configureTestingModule({
          imports: [HttpClientTestingModule],
           providers:    [CategoryService ]
        });
    
        injector = getTestBed();
        categoryService = TestBed.inject(CategoryService);
        httpClient = TestBed.inject(HttpClient);
        httpMock = TestBed.inject(HttpTestingController);
      });
      afterEach(() => {
        httpMock.verify();
      });
      it('getAll() should return some categories',fakeAsync(() => {
        let categories: Category[] = [];
        
        const mockCategories: Category[] = [
            {
             id: 1,
             name: 'a123',
             description: 'Petar'
            },
           {
            id: 2,
            name: 'a123',
            description: 'Petar'            
           }];
        
        categoryService.getAll().subscribe(data => {
          categories = data.body;        
        });
        
        const req = httpMock.expectOne('api/category');
        expect(req.request.method).toBe('GET');
        req.flush(mockCategories);
    
        tick();
    
        expect(categories.length).toEqual(2, 'should contain given amount of students');
        expect(categories[0].id).toEqual(1);
        expect(categories[0].name).toEqual('a123');
        expect(categories[0].description).toEqual('Petar');
    
        expect(categories[1].id).toEqual(2);
        expect(categories[1].name).toEqual('b456');
        expect(categories[1].description).toEqual('Marko');
       
     }));
})
