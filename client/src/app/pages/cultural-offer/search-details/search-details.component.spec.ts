import { ComponentFixture, TestBed } from '@angular/core/testing';
import {  ActivatedRoute, Router, RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { fakeAsync, tick } from '@angular/core/testing';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { BrowserModule, By } from '@angular/platform-browser';
import { Observable, of } from 'rxjs';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatDialogRef } from '@angular/material/dialog';
import { SearchDetailsComponent } from './search-details.component';
import { ActivatedRouteStub } from 'src/app/testing/router-stubs';

class MatDialogRefMock {
    close(value = ''): void{

    }
}

describe('SearchDetailsComponent', () => {
  let component: SearchDetailsComponent;
  let fixture: ComponentFixture<SearchDetailsComponent>;
  let route: any;
  let router: any;
  let toastr: any;
  let dialogRef: any;

  const routerMock = {
    navigate: jasmine.createSpy('navigate')
};
  const activatedRouteStub: ActivatedRouteStub = new ActivatedRouteStub();
  activatedRouteStub.testParamss = {id: 1};


  const toastrMocked = {
    success: jasmine.createSpy('success'),
    error: jasmine.createSpy('error')
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
       declarations: [ SearchDetailsComponent ],
       imports: [ FormsModule, ReactiveFormsModule, RouterModule, ToastrModule.forRoot()],
       providers: [
        { provide: Router, useValue: routerMock },
        { provide: ToastrService, useValue: toastrMocked },
        { provide: MatDialogRef, useClass: MatDialogRefMock},
        { provide: ActivatedRoute, useValue: activatedRouteStub},
       ]
    });

    fixture = TestBed.createComponent(SearchDetailsComponent);
    component = fixture.componentInstance;
    router = TestBed.inject(Router);
    toastr = TestBed.inject(ToastrService);
    dialogRef = TestBed.inject(MatDialogRef);
    route = TestBed.inject(ActivatedRoute);
  });

  it('should create component', fakeAsync(() => {
    expect(component).toBeTruthy();
  }));

  it('should be initialized', () => {
    component.ngOnInit();
    expect(component.searchForm).toBeDefined();
  });

  it('should set input in reactive form', fakeAsync(() => {
    fixture.detectChanges();
    fixture.whenStable().then(() => {
        expect(fixture.debugElement.query(By.css('#searchContent')).nativeElement.value).toEqual('');
        expect(fixture.debugElement.query(By.css('#searchCity')).nativeElement.value).toEqual('');

        const text = fixture.debugElement.query(By.css('#searchContent')).nativeElement;
        text.value = 'Mee';


        text.dispatchEvent(new Event('input'));

        const controlText = component.searchForm.controls.content;


        expect(controlText.value).toEqual('Mee');

      });

  }));

  it('should search', fakeAsync(() => {
    component.ngOnInit();
    spyOn(dialogRef, 'close');
    component.searchForm.controls.content.setValue('Mee');
    component.searchForm.controls.city.setValue('');

    expect(component.searchForm.valid).toBeTruthy();


    spyOn(component.done, 'emit');
    component.search();
    expect(component.done.emit).toHaveBeenCalled();
    expect(dialogRef.close).toHaveBeenCalled();

    }));




});
