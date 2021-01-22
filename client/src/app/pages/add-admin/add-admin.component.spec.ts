import { ComponentFixture, TestBed } from '@angular/core/testing';
import {  Router, RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { fakeAsync, tick } from '@angular/core/testing';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { AuthenticationService } from '../services/authentication/authentication.service';
import { MatCardModule } from '@angular/material/card';
import { AddAdminComponent } from './add-admin.component';
import { BrowserModule, By } from '@angular/platform-browser';
import { Observable, of } from 'rxjs';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { User } from '../model/User';
import { MatDialogRef } from '@angular/material/dialog';

class MatDialogRefMock {
    close(value = '') {

    }
}

describe('AddAdminComponent', () => {
  let component: AddAdminComponent;
  let fixture: ComponentFixture<AddAdminComponent>;
  let authenticationService: any;
  let router: any;
  let toastr: any;
  let dialogRef: any;

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
       declarations: [ AddAdminComponent ],
       imports: [ FormsModule, ReactiveFormsModule, RouterModule, ToastrModule.forRoot(), MatCardModule, BrowserModule, BrowserAnimationsModule],
       providers:    [ 
        { provide: AuthenticationService, useValue: authenticationServiceMock },
        { provide: Router, useValue: routerMock },
        { provide: ToastrService, useValue: toastrMocked },
        { provide: MatDialogRef, useClass: MatDialogRefMock},
       ]
    });

    fixture = TestBed.createComponent(AddAdminComponent);
    component = fixture.componentInstance;
    authenticationService = TestBed.inject(AuthenticationService);
    router = TestBed.inject(Router);
    toastr = TestBed.inject(ToastrService);
    dialogRef = TestBed.inject(MatDialogRef);
  }); 

  it('should create component', fakeAsync(() => {
    expect(component).toBeTruthy();
  }));
  
  it('should be initialized', () => {
    component.ngOnInit();
    expect(component.form).toBeDefined();
    expect(component.form.invalid).toBeTruthy();
  });

  it('should set input in reactive form', fakeAsync(() => {
    fixture.detectChanges();  
    fixture.whenStable().then(() => {
        expect(fixture.debugElement.query(By.css('#firstName')).nativeElement.value).toEqual('');
        expect(fixture.debugElement.query(By.css('#lastName')).nativeElement.value).toEqual('');
        expect(fixture.debugElement.query(By.css('#email')).nativeElement.value).toEqual('');
        expect(fixture.debugElement.query(By.css('#password')).nativeElement.value).toEqual('');
        expect(fixture.debugElement.query(By.css('#passwordRepeat')).nativeElement.value).toEqual('');
        
        let firstName = fixture.debugElement.query(By.css('#firstName')).nativeElement;
        firstName.value = 'Jana';
        let lastName = fixture.debugElement.query(By.css('#lastName')).nativeElement;
        lastName.value = 'Maric';
        let email = fixture.debugElement.query(By.css('#email')).nativeElement;
        email.value = 'jana@gmail.com';
        let password = fixture.debugElement.query(By.css('#password')).nativeElement;
        password.value = 'asdf';
        let passwordRepeat = fixture.debugElement.query(By.css('#passwordRepeat')).nativeElement;
        passwordRepeat.value = 'asdf';

        firstName.dispatchEvent(new Event('input')); 
        lastName.dispatchEvent(new Event('input'));
        email.dispatchEvent(new Event('input'));
        password.dispatchEvent(new Event('input'));
        passwordRepeat.dispatchEvent(new Event('input'));

        let controlFirstName = component.form.controls['firstName'];
        let controlLastName = component.form.controls['lastName'];
        let controlEmail = component.form.controls['email'];
        let controlPassword = component.form.controls['password'];
        let controlPasswordRepeat = component.form.controls['repeatPassword'];

        expect(controlFirstName.value).toEqual('Jana');
        expect(controlLastName.value).toEqual('Maric');
        expect(controlEmail.value).toEqual('jana@gmail.com');
        expect(controlPassword.value).toEqual('asdf');
        expect(controlPasswordRepeat.value).toEqual('asdf');

      });

    
  }));

  it('should register user', fakeAsync(() => {    
    spyOn(dialogRef, 'close');
    expect(component.form.valid).toBeFalsy();
    component.form.controls['firstName'].setValue("Jana");
    component.form.controls['lastName'].setValue("Maric");
    component.form.controls['email'].setValue("jana@gmail.com");
    component.form.controls['password'].setValue("asdf");
    component.form.controls['repeatPassword'].setValue("asdf");
   
    expect(component.form.valid).toBeTruthy();
    component.addAdmin(); 

    expect(authenticationService.registerAdmin).toHaveBeenCalledTimes(1);
    expect(toastr.success).toHaveBeenCalledTimes(1);
    expect(dialogRef.close).toHaveBeenCalledTimes(1);
}));

    it('should be invalid form when first name is empty', () => {
        expect(component.form.valid).toBeFalsy();
        component.form.controls['firstName'].setValue("");
        component.form.controls['lastName'].setValue("Maric");
        component.form.controls['email'].setValue("jana@gmail.com");
        component.form.controls['password'].setValue("asdf");
        component.form.controls['repeatPassword'].setValue("asdf");

        expect(component.form.invalid).toBeTruthy();
        fixture.detectChanges();

        const submitButton = fixture.debugElement.query(By.css('#submitAdmin')).nativeElement;
        expect(submitButton.disabled).toBeTruthy();
    });

    it('should be invalid form when last name is empty', () => {
        expect(component.form.valid).toBeFalsy();
        component.form.controls['firstName'].setValue("Jana");
        component.form.controls['lastName'].setValue("");
        component.form.controls['email'].setValue("jana@gmail.com");
        component.form.controls['password'].setValue("asdf");
        component.form.controls['repeatPassword'].setValue("asdf");

        expect(component.form.invalid).toBeTruthy();
        fixture.detectChanges();

        const submitButton = fixture.debugElement.query(By.css('#submitAdmin')).nativeElement;
        expect(submitButton.disabled).toBeTruthy();
    });

    it('should be invalid form when email is invalid', () => {
        expect(component.form.valid).toBeFalsy();
        component.form.controls['firstName'].setValue("Jana");
        component.form.controls['lastName'].setValue("Maric");
        component.form.controls['email'].setValue("jana@gmai");
        component.form.controls['password'].setValue("asdf");
        component.form.controls['repeatPassword'].setValue("asdf");

        expect(component.form.invalid).toBeTruthy();
        fixture.detectChanges();

        const submitButton = fixture.debugElement.query(By.css('#submitAdmin')).nativeElement;
        expect(submitButton.disabled).toBeTruthy();
    });

    it('should be invalid form when password is empty', () => {
        expect(component.form.valid).toBeFalsy();
        component.form.controls['firstName'].setValue("");
        component.form.controls['lastName'].setValue("Maric");
        component.form.controls['email'].setValue("jana@gmail.com");
        component.form.controls['password'].setValue("");
        component.form.controls['repeatPassword'].setValue("asdf");

        expect(component.form.invalid).toBeTruthy();
        fixture.detectChanges();

        const submitButton = fixture.debugElement.query(By.css('#submitAdmin')).nativeElement;
        expect(submitButton.disabled).toBeTruthy();
    });

    it('should be invalid form when password repeat is empty', () => {
        expect(component.form.valid).toBeFalsy();
        component.form.controls['firstName'].setValue("");
        component.form.controls['lastName'].setValue("Maric");
        component.form.controls['email'].setValue("jana@gmail.com");
        component.form.controls['password'].setValue("asdf");
        component.form.controls['repeatPassword'].setValue("");

        expect(component.form.invalid).toBeTruthy();
        fixture.detectChanges();

        const submitButton = fixture.debugElement.query(By.css('#submitAdmin')).nativeElement;
        expect(submitButton.disabled).toBeTruthy();
    });

    it('should be invalid form when password is not repeated correctly', fakeAsync(() => {
        expect(component.form.valid).toBeFalsy();
        component.form.controls['firstName'].setValue("Jana");
        component.form.controls['lastName'].setValue("Maric");
        component.form.controls['email'].setValue("jana@gmail.com");
        component.form.controls['password'].setValue("asdf");
        component.form.controls['repeatPassword'].setValue("asdgsjfhdjf");

        fixture.detectChanges();
        component.addAdmin();

        fixture.detectChanges();
        tick();

        expect(component.passwordError).toBeTruthy();

    }));

    it('should close the dialog', fakeAsync(() => {
        spyOn(dialogRef, 'close');   
        component.cancel();

        expect(dialogRef.close).toHaveBeenCalledTimes(1);

    }));
   
});

