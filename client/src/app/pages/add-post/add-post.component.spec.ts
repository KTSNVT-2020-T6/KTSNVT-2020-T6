import { ComponentFixture, TestBed } from '@angular/core/testing';
import {  ActivatedRoute, Router, RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { fakeAsync, tick } from '@angular/core/testing';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { AuthenticationService } from '../services/authentication/authentication.service';
import { MatCardModule } from '@angular/material/card';
import { BrowserModule, By } from '@angular/platform-browser';
import { Observable, of } from 'rxjs';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { User } from '../model/User';
import { MatDialogRef } from '@angular/material/dialog';
import { AddPostComponent } from './add-post.component';
import { PostService } from '../services/post/post.service';
import { CulturalOfferDetailsService } from '../services/cultural-offer-details/cultural-offer-details.service';
import { ImageService } from '../services/image/image.service';
import { ActivatedRouteStub } from 'src/app/testing/router-stubs';

class MatDialogRefMock {
    close(value = '') {

    }
}

describe('AddPostComponent', () => {
  let component: AddPostComponent;
  let fixture: ComponentFixture<AddPostComponent>;
  let postService: PostService;
  let coService: CulturalOfferDetailsService;
  let imageService: ImageService;
  let route : any;
  let router : any;
  let toastr: any;
  let dialogRef: any;

 beforeEach(() => {
 
    let coServiceMock ={
      getOne: jasmine.createSpy('getOne').and.returnValue(of({body: {id:1}}))
    }
    let postServiceMock = {
        addPost: jasmine.createSpy('addPost').and.returnValue(of({}))
    }
    let imageServiceMock = {
        add: jasmine.createSpy('add').and.returnValue(of({}))
    }
    let routerMock= {
        navigate: jasmine.createSpy('navigate')
    }
    let activatedRouteStub: ActivatedRouteStub = new ActivatedRouteStub();
    activatedRouteStub.testParams = {id: 1};


    const toastrMocked = {
        success: jasmine.createSpy('success'),
        error: jasmine.createSpy('error')
      };

    TestBed.configureTestingModule({
       declarations: [ AddPostComponent ],
       imports: [ FormsModule, ReactiveFormsModule, RouterModule, ToastrModule.forRoot(), MatCardModule, BrowserModule, BrowserAnimationsModule],
       providers:    [ 
        { provide: CulturalOfferDetailsService, useValue: coServiceMock },
        { provide: ImageService, useValue: imageServiceMock },
        { provide: PostService, useValue: postServiceMock },
        { provide: Router, useValue: routerMock },
        { provide: ToastrService, useValue: toastrMocked },
        { provide: MatDialogRef, useClass: MatDialogRefMock},
        { provide: ActivatedRoute, useValue: activatedRouteStub},
    ]
    });

    fixture = TestBed.createComponent(AddPostComponent);
    component = fixture.componentInstance;
    coService = TestBed.inject(CulturalOfferDetailsService);
    postService = TestBed.inject(PostService);
    imageService = TestBed.inject(ImageService);
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
    expect(coService.getOne).toHaveBeenCalled();
    expect(component.postForm).toBeDefined();
    expect(component.postForm.invalid).toBeTruthy();
  });

  it('should set input in reactive form', fakeAsync(() => {
    fixture.detectChanges();  
    fixture.whenStable().then(() => {
        expect(fixture.debugElement.query(By.css('#textPost')).nativeElement.value).toEqual('');
 
        let text = fixture.debugElement.query(By.css('#textPost')).nativeElement;
        text.value = 'Building with very poor horizontal and vertical isolation....';
        

        text.dispatchEvent(new Event('input')); 
        
        let controlText = component.postForm.controls['text'];
        

        expect(controlText.value).toEqual('Building with very poor horizontal and vertical isolation....');

      });

    
  }));

  it('should add post', fakeAsync(() => {  
    component.ngOnInit();
    spyOn(dialogRef, 'close');
    expect(component.postForm.valid).toBeFalsy();
    component.postForm.controls['text'].setValue("Building with very poor horizontal and vertical isolation....");
       
    expect(component.postForm.valid).toBeTruthy();
    component.addPost(); 

    expect(postService.addPost).toHaveBeenCalledTimes(1);
    expect(toastr.success).toHaveBeenCalledTimes(1);
    expect(dialogRef.close).toHaveBeenCalledTimes(1);
    }));

    it('should add post with image', fakeAsync(() => {  
        component.ngOnInit();
        spyOn(dialogRef, 'close');
        expect(component.postForm.valid).toBeFalsy();
        component.postForm.controls['text'].setValue("Building with very poor horizontal and vertical isolation....");
        component.postForm.controls['image'].setValue("D:\Control Panel.{21EC2020-3AEA-1069-A2DD-08002B30309D}\New folder\Private\Instagram-prebaceno OKTOBAR 2020\projekat.jpg");

        component.saveImage();

        expect(imageService.add).toHaveBeenCalledTimes(1);
        expect(component.post.imageDTO).toBeDefined();
        expect(component.postForm.valid).toBeTruthy();
        component.addPost(); 
    
        expect(postService.addPost).toHaveBeenCalledTimes(1);
        expect(toastr.success).toHaveBeenCalledTimes(1);
        expect(dialogRef.close).toHaveBeenCalledTimes(1);
    }));

    it('should be invalid form when text is empty', () => {
        expect(component.postForm.valid).toBeFalsy();
        component.postForm.controls['text'].setValue("");

        expect(component.postForm.invalid).toBeTruthy();
        fixture.detectChanges();

        const submitButton = fixture.debugElement.query(By.css('#submitAddingPost')).nativeElement;
        expect(submitButton.disabled).toBeTruthy();
    });

    it('should close the dialog', fakeAsync(() => {
        spyOn(dialogRef, 'close');   
        component.cancel();

        expect(dialogRef.close).toHaveBeenCalledTimes(1);

    }));
   
});

