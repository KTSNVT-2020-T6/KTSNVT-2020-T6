import { ComponentFixture, TestBed } from '@angular/core/testing';
import {  ActivatedRoute, Router, RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { fakeAsync, tick } from '@angular/core/testing';
import { PaginationComponent } from './pagination.component';
import { PaginationService } from '../services/pagination/pagination.service';
import { SimpleChange, SimpleChanges } from '@angular/core';


describe('PaginationComponent', () => {
  let component: PaginationComponent;
  let fixture: ComponentFixture<PaginationComponent>;
  let paginationService: PaginationService;

 beforeEach(() => {
 
    let paginationServiceMock ={
        getNoPages: jasmine.createSpy('getNoPages').and.returnValue(5)
    }
    
    TestBed.configureTestingModule({
       declarations: [ PaginationComponent ],
       imports: [ ],
       providers:    [ 
        { provide: PaginationService, useValue: paginationServiceMock },
    ]
    });

    fixture = TestBed.createComponent(PaginationComponent);
    component = fixture.componentInstance;
    paginationService = TestBed.inject(PaginationService);
  }); 

  it('should create component', fakeAsync(() => {
    expect(component).toBeTruthy();
  }));
  
  it('should be initialized', () => {
    component.ngOnInit();
    expect(paginationService.getNoPages).toHaveBeenCalled();
  });

  it('should be selected', () => {
    component.ngOnInit();
    const previousValue = '';
    const currentValue = 10;
    const changesObj: SimpleChanges = { totalItems : new SimpleChange(previousValue, currentValue, false),
      };
    component.ngOnChanges(changesObj);
    let page = 2;
    spyOn(component.pageSelected, 'emit');
    component.selected(page);
    expect(paginationService.getNoPages).toHaveBeenCalled();
    expect(component.pageSelected.emit).toHaveBeenCalled();
    expect(component.activePage).toBe(2);
  });

  it('should detect changes', fakeAsync(() => {
    const previousValue = '';
    const currentValue = 1;
    const changesObj: SimpleChanges = { totalItems : new SimpleChange(previousValue, currentValue, false),
      };
    spyOn(component, 'ngOnChanges').and.callThrough();
    component.ngOnChanges(changesObj);
   
    fixture.detectChanges();
    tick();
    expect(component.ngOnChanges).toHaveBeenCalled();
    expect(paginationService.getNoPages).toHaveBeenCalled();
   
  }));



   
});
