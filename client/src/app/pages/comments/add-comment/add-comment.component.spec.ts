import { async, ComponentFixture, flush, TestBed } from '@angular/core/testing';
import {  Router, RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { fakeAsync, tick } from '@angular/core/testing';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { AuthenticationService } from '../../../core/services/authentication/authentication.service';
import { MatCardModule } from '@angular/material/card';
import { AddCommentComponent } from './add-comment.component';
import { BrowserModule, By } from '@angular/platform-browser';
import { Observable, of } from 'rxjs';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { User } from '../../../core/model/User';
import { HttpClientModule } from '@angular/common/http';
import { MaterialModule } from '../../material-module';
import { UserService } from '../../../core/services/user/user.service';
import { ImageService } from '../../../core/services/image/image.service';
import { CommentService } from '../../../core/services/comment/comment.service';

describe('AddCommentComponent', () => {
  let component: AddCommentComponent;
  let fixture: ComponentFixture<AddCommentComponent>;
  let authenticationService: any;
  let router: any;
  let toastr: any;
  let commentService: any;
  let userService: any;
  let imageService: any;

 beforeEach(() => {
  let userMock: User ={
    id: 1,
    firstName: 'Stefan',
    lastName: 'Stefic',
    email: 'stefa@gmail.com',
    password: 'asdf',
    active:  true,
    verified: true,
    idImageDTO: 1,
    src: ''  
  }  
    let commentServiceMock={
      save: jasmine.createSpy('save')
      .and.returnValue(of({body:{}}))
    }
    let userServiceMock={
      getCurrentUser: jasmine.createSpy('getCurrentUser').and.
      returnValue(of({body:userMock})) 
    }
    let imageServiceMock={
      add: jasmine.createSpy('add')
      .and.returnValue(of({} ))
    }
    let authenticationServiceMock ={
      registerAdmin: jasmine.createSpy('registerAdmin').and.returnValue(of(new Observable<User>()))
    }
    let routerMock= {
        navigate: jasmine.createSpy('navigate')
    }

    const toastrMocked = {
        success: jasmine.createSpy('success'),
        error: jasmine.createSpy('error')
      };

    TestBed.configureTestingModule({
       declarations: [ AddCommentComponent ],
       imports: [  FormsModule, ReactiveFormsModule, HttpClientModule,
         RouterModule, ToastrModule.forRoot(), MatCardModule,
          BrowserModule, BrowserAnimationsModule, MaterialModule],
       providers:    [ 
        { provide: AuthenticationService, useValue: authenticationServiceMock },
        { provide: Router, useValue: routerMock },
        { provide: ToastrService, useValue: toastrMocked },
        { provide: UserService, useValue: userServiceMock },
        { provide: ImageService, useValue: imageServiceMock },
        { provide: CommentService, useValue: commentServiceMock },
       ]
    });

    fixture = TestBed.createComponent(AddCommentComponent);
    component = fixture.componentInstance;
    authenticationService = TestBed.inject(AuthenticationService);
    router = TestBed.inject(Router);
    toastr = TestBed.inject(ToastrService);
    userService = TestBed.inject(UserService);
    imageService = TestBed.inject(ImageService);
    commentService = TestBed.inject(CommentService);
  }); 

  it('should create component', fakeAsync(() => {
    expect(component).toBeTruthy();
  }));
  
  it('should be empty form', () => {
    expect(component).toBeTruthy();
    component.ngOnInit();
    expect(component.commentForm.invalid).toBeFalsy();
   
  });
  it('should map on form', fakeAsync(() => {

    component.role ="ROLE_REGISTERED_USER";
    fixture.detectChanges();
    fixture.whenStable().then(() => {
      
      expect(fixture.debugElement.query(By.css('#newComment')).nativeElement.value).toEqual('');
      let text = fixture.debugElement.nativeElement.querySelector('#newComment');
      text.value = 'Tekst komentara';
      text.dispatchEvent(new Event('input'));
      fixture.detectChanges(); // tell angular that data are fetched
      tick(); // initiate next cycle of binding these data to HTML components
      fixture.detectChanges(); // 
      let controlTextInput = fixture.debugElement.query(By.css('#newComment')).nativeElement;
      expect(controlTextInput.value).toEqual('Tekst komentara');
      let controlText = component.commentForm.controls['text'];

      expect(controlText.value).toEqual('Tekst komentara');

      component.commentForm.controls['text'].setValue('');
    });
  }));

  it('should save image on upload', async(() => {
    component.ngOnInit();

    component.onFileSelect({target: {files: ["mockImage"]}});
    expect(component.commentForm.valid).toBeTruthy();
    expect(component.commentForm.value['image']).toBe("mockImage");
  }));

  it('should add comment with image', fakeAsync(() => {
    component.ngOnInit();
    spyOn(component, "windowReload").and.callFake(function(){});
    spyOn(component, "onFileSelect").and.callFake(function(){});
    component.commentForm.controls['image'].setValue("image.jpg");
    component.addNewComment();
    component.culturalOfferId = 1;  
    expect(userService.getCurrentUser).toHaveBeenCalledTimes(1);
    expect(imageService.add).toHaveBeenCalledTimes(1);
    expect(component.comment.imageDTO).toBeDefined();
    expect(toastr.success).toHaveBeenCalledTimes(1);
    expect(component.windowReload).toHaveBeenCalled();
    flush();
  }));

  it('should add comment with image and text', fakeAsync(() => {
    component.ngOnInit();
    spyOn(component, "windowReload").and.callFake(function(){});
    spyOn(component, "onFileSelect").and.callFake(function(){});
    component.commentForm.controls['text'].setValue("Sadrzaj teksta");
    component.commentForm.controls['image'].setValue("image.jpg");
    component.addNewComment();
    component.culturalOfferId = 1;  
    expect(userService.getCurrentUser).toHaveBeenCalledTimes(1);
    expect(imageService.add).toHaveBeenCalledTimes(1);
    expect(component.comment.imageDTO).toBeDefined();
    expect(component.comment.text).toBeDefined();
    expect(toastr.success).toHaveBeenCalledTimes(1);
    expect(component.windowReload).toHaveBeenCalled();
    flush();
  }));
  it('should add comment with text', fakeAsync(() => {
    component.ngOnInit();
    spyOn(component, "windowReload").and.callFake(function(){});
    component.commentForm.controls['text'].setValue("Sadrzaj teksta");
    component.addNewComment();
    component.culturalOfferId = 1;  
    expect(userService.getCurrentUser).toHaveBeenCalledTimes(1);
    expect(component.comment.text).toBeDefined();
    expect(toastr.success).toHaveBeenCalledTimes(1);
    expect(component.windowReload).toHaveBeenCalled();
    flush();
  }));
});
