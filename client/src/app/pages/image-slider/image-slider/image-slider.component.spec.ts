import { ComponentFixture, discardPeriodicTasks, flush, TestBed } from '@angular/core/testing';
import {  Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { fakeAsync, tick } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { Observable, of } from 'rxjs';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { MatDialog, MatDialogModule, MatDialogRef } from '@angular/material/dialog';

import { MatCardModule } from '@angular/material/card';
import { ImageSliderComponent } from './image-slider.component';
import { ImageService } from '../../services/image/image.service';
import { Img } from '../../model/Image';
import { CulturalOffer } from '../../model/CulturalOffer';
import { Category } from '../../model/Category';
import { Type } from '../../model/Type';
import { SimpleChange, SimpleChanges } from '@angular/core';
import { MaterialModule } from '../../material-module';
import { CarouselModule, MDBBootstrapModule } from 'angular-bootstrap-md';
import { MatCarouselModule } from '@ngmodule/material-carousel';
 
describe('ImageSliderComponent', () => {
  let component: ImageSliderComponent;
  let fixture: ComponentFixture<ImageSliderComponent>;
  let imageService: ImageService;
  let imageDTO: Img[];
  let culturalOffer :CulturalOffer;

 beforeEach(() => {
    let imageServiceMock ={
        getImage: jasmine.createSpy('getImage').and.returnValue(of(
            ''
        ))
    }
    
    TestBed.configureTestingModule({
       declarations: [ ImageSliderComponent],
       imports: [ToastrModule.forRoot()],
       providers:    [   { provide: ImageService, useValue: imageServiceMock } ]
    });

    fixture = TestBed.createComponent(ImageSliderComponent);
    component = fixture.componentInstance;
    imageService = TestBed.inject(ImageService);

    const category: Category = {
        id: 1,
        name: 'category number 1',
        description: 'this is a category no 1'
    }
    const type: Type = {
        id: 1,
        name: 'type number 1',
        description: 'this is a type no 1',
        categoryDTO: category
    }
    culturalOffer = {
        id: 1,
        averageRate: 5,
        description: 'desc',
        name: 'co name',
        city: 'belgrade',
        date: new Date,
        lat: 45.41,
        lon: 21.16,
        typeDTO: type
    };
    imageDTO = [{id:1, description:'Van Gog', relativePath:'/r/r/rr/r'},
    {id:2, description:'Pol Gogen', relativePath:'/f/f/f/f/f/f'}];
  }); 
  it('should create commponent', fakeAsync(() => {
    expect(component).toBeTruthy();
  }));
  it('should fetch images', fakeAsync(() => {
    const previousValue ='';
    const currentValue = imageDTO;
    const changesObj: SimpleChanges = {
        imageDTO: new SimpleChange(previousValue, currentValue, false),
      };
    spyOn(component, 'ngOnChanges').and.callThrough();
    component.ngOnChanges(changesObj);
   
    fixture.detectChanges();
    tick();
    expect(component.ngOnChanges).toHaveBeenCalled();
    expect(imageService.getImage).toHaveBeenCalledTimes(2);
   
  }));

});