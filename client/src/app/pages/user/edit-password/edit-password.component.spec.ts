import { ComponentFixture, TestBed } from '@angular/core/testing';
import {  Router, RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { fakeAsync, tick } from '@angular/core/testing';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { AuthenticationService } from '../../../core/services/authentication/authentication.service';
import { MatCardModule } from '@angular/material/card';
import { EditPasswordComponent } from './edit-password.component';
import { BrowserModule, By } from '@angular/platform-browser';
import { Observable, of } from 'rxjs';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { User } from '../../../core/model/User';
import { MatDialogRef } from '@angular/material/dialog';

class MatDialogRefMock {
    close(value = ''): void {}
}

describe('EditPasswordComponent', () => {
  let component: EditPasswordComponent;
  let fixture: ComponentFixture<EditPasswordComponent>;
  let authenticationService: any;
  let router: any;
  let toastr: any;
  let dialogRef: any;

  beforeEach(() => {
    const authenticationServiceMock = {
      changePassword: jasmine.createSpy('changePassword').and.returnValue(of(new Observable<any>())),
      signOut: jasmine.createSpy('signOut').and.returnValue(of(new Observable<any>()))
    };
    const routerMock = {
      navigate: jasmine.createSpy('navigate')
    };
    const toastrMocked = {
      success: jasmine.createSpy('success'),
       error: jasmine.createSpy('error')
    };
    TestBed.configureTestingModule({
       declarations: [ EditPasswordComponent ],
       imports: [ FormsModule, ReactiveFormsModule, RouterModule,
        ToastrModule.forRoot(), MatCardModule, BrowserModule, BrowserAnimationsModule ],
       providers: [
        { provide: AuthenticationService, useValue: authenticationServiceMock },
        { provide: Router, useValue: routerMock },
        { provide: ToastrService, useValue: toastrMocked },
        { provide: MatDialogRef, useClass: MatDialogRefMock},
       ]
    });

    fixture = TestBed.createComponent(EditPasswordComponent);
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
        expect(fixture.debugElement.query(By.css('#newPassword')).nativeElement.value).toEqual('');
        expect(fixture.debugElement.query(By.css('#oldPassword')).nativeElement.value).toEqual('');
        const oldPassword = fixture.debugElement.query(By.css('#oldPassword')).nativeElement;
        oldPassword.value = 'asdf';
        const newPassword = fixture.debugElement.query(By.css('#newPassword')).nativeElement;
        newPassword.value = 'asdfghjk';
        oldPassword.dispatchEvent(new Event('input'));
        newPassword.dispatchEvent(new Event('input'));
        const controlOldPassword = component.form.controls.oldPassword;
        const controlNewPassword = component.form.controls.newPassword;
        expect(controlOldPassword.value).toEqual('asdf');
        expect(controlNewPassword.value).toEqual('asdfghjk');
      });
  }));

  it('should change user`s password', fakeAsync(() => {
    spyOn(dialogRef, 'close');
    expect(component.form.valid).toBeFalsy();
    component.form.controls.oldPassword.setValue('asdf');
    component.form.controls.newPassword.setValue('asdfghjk');
    expect(component.form.valid).toBeTruthy();
    component.saveChanges();
    expect(authenticationService.changePassword).toHaveBeenCalledTimes(1);
    expect(toastr.success).toHaveBeenCalledTimes(1);
    expect(dialogRef.close).toHaveBeenCalledTimes(1);
    expect(authenticationService.signOut).toHaveBeenCalledTimes(1);
    expect(router.navigate).toHaveBeenCalledWith(['/login']);
  }));

  it('should be invalid form when old password is empty', () => {
    expect(component.form.valid).toBeFalsy();
    component.form.controls.oldPassword.setValue('');
    component.form.controls.newPassword.setValue('asdfghjk');
    expect(component.form.invalid).toBeTruthy();
    fixture.detectChanges();
    const submitButton = fixture.debugElement.query(By.css('#submitPasswordBtn')).nativeElement;
    expect(submitButton.disabled).toBeTruthy();
  });

  it('should be invalid form when new password is empty', () => {
    expect(component.form.valid).toBeFalsy();
    component.form.controls.oldPassword.setValue('asdf');
    component.form.controls.newPassword.setValue('');
    expect(component.form.invalid).toBeTruthy();
    fixture.detectChanges();
    const submitButton = fixture.debugElement.query(By.css('#submitPasswordBtn')).nativeElement;
    expect(submitButton.disabled).toBeTruthy();
  });

  it('should not close dialog if new password equals old password', fakeAsync(() => {
    spyOn(dialogRef, 'close');
    expect(component.form.valid).toBeFalsy();
    component.form.controls.oldPassword.setValue('asdf');
    component.form.controls.newPassword.setValue('asdf');
    expect(component.form.valid).toBeTruthy();
    component.saveChanges();
    expect(authenticationService.changePassword).toHaveBeenCalledTimes(0);
    expect(toastr.error).toHaveBeenCalledTimes(1);
    expect(dialogRef.close).toHaveBeenCalledTimes(1);
    expect(authenticationService.signOut).toHaveBeenCalledTimes(0);
    expect(component.passwordError).toBeTruthy();
  }));

  it('should close the dialog', fakeAsync(() => {
    spyOn(dialogRef, 'close');
    component.cancel();
    expect(dialogRef.close).toHaveBeenCalledTimes(1);
  }));
});
