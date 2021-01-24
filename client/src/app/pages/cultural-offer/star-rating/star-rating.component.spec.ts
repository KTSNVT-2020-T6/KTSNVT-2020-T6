import { ComponentFixture, TestBed } from '@angular/core/testing';
import {  Router, RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { fakeAsync, tick } from '@angular/core/testing';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { MatCardModule } from '@angular/material/card';
import { StarRatingComponent } from './star-rating.component';
import { BrowserModule, By } from '@angular/platform-browser';
import { Observable, of } from 'rxjs';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatDialogRef } from '@angular/material/dialog';

class MatDialogRefMock {
    close(value = '') {

    }
}

describe('StarRatingComponent', () => {
  let component: StarRatingComponent;
  let fixture: ComponentFixture<StarRatingComponent>;

 beforeEach(() => {
    TestBed.configureTestingModule({
       declarations: [ StarRatingComponent ],
       imports: [BrowserModule, BrowserAnimationsModule],
       providers: []
    });

    fixture = TestBed.createComponent(StarRatingComponent);
    component = fixture.componentInstance;

  }); 

  it('should create component', fakeAsync(() => {
    expect(component).toBeTruthy();
  }));

  it('should emit output on click', fakeAsync(() => {
    spyOn(component.ratingClick, 'emit');
    component.onClick(1);
    expect(component.ratingClick.emit).toHaveBeenCalled();
    expect(component.rating).toEqual(1);
    
  }));

  it('should call onClick method when star 5 is clicked', fakeAsync(() => {
    spyOn(component.ratingClick, 'emit');
    const rateFive = fixture.debugElement.query(By.css('#star5')).nativeElement;
    rateFive.click();
    expect(component.ratingClick.emit).toHaveBeenCalled();
    expect(component.rating).toEqual(5);
    
  }));

  it('should call onClick method when star 4 is clicked', fakeAsync(() => {
    spyOn(component.ratingClick, 'emit');
    const rateFive = fixture.debugElement.query(By.css('#star4')).nativeElement;
    rateFive.click();
    expect(component.ratingClick.emit).toHaveBeenCalled();
    expect(component.rating).toEqual(4);
    
  }));

  it('should call onClick method when star 3 is clicked', fakeAsync(() => {
    spyOn(component.ratingClick, 'emit');
    const rateFive = fixture.debugElement.query(By.css('#star3')).nativeElement;
    rateFive.click();
    expect(component.ratingClick.emit).toHaveBeenCalled();
    expect(component.rating).toEqual(3);
    
  }));

  it('should call onClick method when star 2 is clicked', fakeAsync(() => {
    spyOn(component.ratingClick, 'emit');
    const rateFive = fixture.debugElement.query(By.css('#star2')).nativeElement;
    rateFive.click();
    expect(component.ratingClick.emit).toHaveBeenCalled();
    expect(component.rating).toEqual(2);
    
  }));

  it('should call onClick method when star 1 is clicked', fakeAsync(() => {
    spyOn(component.ratingClick, 'emit');
    const rateFive = fixture.debugElement.query(By.css('#star1')).nativeElement;
    rateFive.click();
    expect(component.ratingClick.emit).toHaveBeenCalled();
    expect(component.rating).toEqual(1);
    
  }));
   
});


