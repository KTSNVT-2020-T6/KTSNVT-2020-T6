
import { TestBed, getTestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import {fakeAsync, tick} from '@angular/core/testing';
import { HttpClient } from '@angular/common/http';
import { CategoryService } from './category.service';
import { Category } from '../../model/Category';

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
             name: 'institution',
             description: 'institution in serbia'
            },
           {
            id: 2,
            name: 'manifestation',
            description: 'manifestation in serbia'            
           }];
        
        categoryService.getAll().subscribe(data => {
          categories = data.body;        
        });
        
        const req = httpMock.expectOne('http://localhost:8080/api/category');
        expect(req.request.method).toBe('GET');
        req.flush(mockCategories);
    
        tick();
    
        expect(categories.length).toEqual(2, 'should contain given amount of students');
        expect(categories[0].id).toEqual(1);
        expect(categories[0].name).toEqual('institution');
        expect(categories[0].description).toEqual('institution in serbia');
    
        expect(categories[1].id).toEqual(2);
        expect(categories[1].name).toEqual('manifestation');
        expect(categories[1].description).toEqual('manifestation in serbia');
       
     }));

     it('add()  should query url and save a category',fakeAsync(() => {
      let newCategory: Category = {
        id: 1,
        name: 'institution',
        description: 'institution in serbia'
      }
      
      const mockCategory: Category = 
          {
           id: 1,
           name: 'institution',
           description: 'institution in serbia'
          };
      
      categoryService.add(newCategory).subscribe(data => newCategory = data);
      
      
      const req = httpMock.expectOne('http://localhost:8080/api/category');
      expect(req.request.method).toBe('POST');
      req.flush(mockCategory);
  
      tick();
  
      expect(newCategory).toBeDefined();
      expect(newCategory.id).toEqual(1);
      expect(newCategory.name).toEqual('institution');
      expect(newCategory.description).toEqual('institution in serbia');
   }));

   it('getCategory() should query url and get category',fakeAsync(() => {
    let category: Category = { 
        id: 1,
        name: 'institution',
        description: 'institution in serbia'
    };
    
    const mockCategory: Category = 
        {
         id: 1,
         name: 'institution',
         description: 'institution in serbia'
        };
    
    categoryService.getCategory(1).subscribe(data => category = data.body);
    
    
    const req = httpMock.expectOne('http://localhost:8080/api/category/1');
    expect(req.request.method).toBe('GET');
    req.flush(mockCategory);

    tick();

    expect(category).toBeDefined();
    expect(category.id).toEqual(1);
    expect(category.name).toEqual('institution');
    expect(category.description).toEqual('institution in serbia');
    }));

    it('update() should query url and edit a category', fakeAsync(() => {
      let category: Category = {
          id: 1,
          name: 'institution',
          description: 'institution in serbia'
         };
      
  
      const mockCategory: Category = 
      {
        id: 1,
        name: 'institution',
        description: 'institution in serbia'           
      };
      
      categoryService.update(category, 1).subscribe(res => category = res
      );
      
      const req = httpMock.expectOne('http://localhost:8080/api/category/1');
      expect(req.request.method).toBe('PUT');
      req.flush(mockCategory);
      
      tick();
      expect(category).toBeDefined();
      expect(category.id).toEqual(1);
      expect(category.name).toEqual('institution');
      expect(category.description).toEqual('institution in serbia');
    }));
  
     it('delete() should query url and delete a category', () => {
      categoryService.delete(1).subscribe(res => { });
      
      const req = httpMock.expectOne('http://localhost:8080/api/category/1');
      expect(req.request.method).toBe('DELETE');
      req.flush({});
    });
})
