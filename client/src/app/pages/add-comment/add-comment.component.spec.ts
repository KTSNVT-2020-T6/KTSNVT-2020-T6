import { ComponentFixture, TestBed } from '@angular/core/testing';
import {  Router, RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { fakeAsync, tick } from '@angular/core/testing';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { AuthenticationService } from '../services/authentication/authentication.service';
import { MatCardModule } from '@angular/material/card';
import { AddCommentComponent } from './add-comment.component';
import { BrowserModule, By } from '@angular/platform-browser';
import { Observable, of } from 'rxjs';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { User } from '../model/User';
import { HttpClientModule } from '@angular/common/http';

describe('AddCommentComponent', () => {
  let component: AddCommentComponent;
  let fixture: ComponentFixture<AddCommentComponent>;
  let authenticationService: any;
  let router: any;
  let toastr: any;


 beforeEach(() => {
 
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
       imports: [ FormsModule, ReactiveFormsModule, HttpClientModule,
         RouterModule, ToastrModule.forRoot(), MatCardModule,
          BrowserModule, BrowserAnimationsModule],
       providers:    [ 
        { provide: AuthenticationService, useValue: authenticationServiceMock },
        { provide: Router, useValue: routerMock },
        { provide: ToastrService, useValue: toastrMocked },
       ]
    });

    fixture = TestBed.createComponent(AddCommentComponent);
    component = fixture.componentInstance;
    authenticationService = TestBed.inject(AuthenticationService);
    router = TestBed.inject(Router);
    toastr = TestBed.inject(ToastrService);
  
  }); 

  it('should create component', fakeAsync(() => {
    expect(component).toBeTruthy();
  }));
  
  it('should be empty form', () => {
    expect(component).toBeTruthy();
    expect(component.commentForm.invalid).toBeFalsy();
   
  });

});



//   it('should create', () => {
//     expect(component).toBeTruthy();
//   });
  
//   // it('comment form should be empty', () => {
//   //   component.ngOnInit();
//   //   expect(component.commentForm.invalid).toBeFalsy();
//   // });
//   // it('should send a comment with text only', fakeAsync(() => {

//   //   component.role ="ROLE_REGISTERED_USER";

//   //   expect(component).toBeTruthy();
//   //   component.ngOnInit(); //da se namesti id
  

//   //   fixture.whenStable().then(() => {
//   //       console.log();
//   //       // expect(fixture.debugElement.query(By.css('form mat-form-field #newComment')).nativeElement.value).toEqual('');
//   //       // expect(fixture.debugElement.query(By.css('form mat-form-field #uploadButton')).nativeElement.value).toEqual('');
//   //      // component.commentForm.controls['text'].setValue('');
//   //       let text = fixture.debugElement.nativeElement.querySelector('#newComment');
//   //       text.value = 'Tekst komentara';
    
//   //       text.dispatchEvent(new Event('input')); 
       
//   //       let controlText = component.commentForm.controls['text'];
        
//   //       expect(controlText.value).toEqual('Tekst komentara');
      

//   //     });
    
//   // }));
//   // // it('should send a comment with image only', fakeAsync(() => {
    
    
//   // // }));
//   // // it('should send a comment with image and text', fakeAsync(() => {
//   //   // expect(component).toBeTruthy();
//   //   // component.ngOnInit(); //da se namesti id
    
//   //   // fixture.detectChanges();  
//   //   // fixture.whenStable().then(() => {
//   //   //     expect(fixture.debugElement.query(By.css('#firstName')).nativeElement.value).toEqual('');
//   //   //     expect(fixture.debugElement.query(By.css('#lastName')).nativeElement.value).toEqual('');
      
//   //   //     let firstName = fixture.debugElement.query(By.css('#firstName')).nativeElement;
//   //   //     firstName.value = 'Jana';
//   //   //     let lastName = fixture.debugElement.query(By.css('#lastName')).nativeElement;
//   //   //     lastName.value = 'Maric';
        

//   //   //     firstName.dispatchEvent(new Event('input')); 
//   //   //     lastName.dispatchEvent(new Event('input'));
      

//   //   //     let controlFirstName = component.form.controls['firstName'];
//   //   //     let controlLastName = component.form.controls['lastName'];
       

//   //   //     expect(controlFirstName.value).toEqual('Jana');
//   //   //     expect(controlLastName.value).toEqual('Maric');

//   //   //   });
    
//   // // }));
//   // // it('try to send a empty comment', fakeAsync(() => {
    
    
//   // // }));
//   // // it('should select file', fakeAsync(() => {
    
    
//   // // }));
// });
