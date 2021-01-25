import { async, ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { User } from '../../../core/model/User';
import { AdminService } from '../../../core/services/admin/admin.service';
import { AuthenticationService } from '../../../core/services/authentication/authentication.service';
import { ImageService } from '../../../core/services/image/image.service';
import { RegisteredUserService } from '../../../core/services/registered-user/registered-user.service';
import { By } from '@angular/platform-browser';

import { EditProfileComponent } from './edit-profile.component';
import { of } from 'rxjs';

export class MatDialogRefMock {
    close(value = '') {
    }
}

describe('EditProfileComponent', () => {
  let component: EditProfileComponent;
  let fixture: ComponentFixture<EditProfileComponent>;
  let toastr: any;
  let regUserService: any;
  let adminService: any;
  let imageService: any;
  let dialogRef: any;
  let router: any;
  let authenticationService: any;

  const mockUser: User = {
    id: 1,
    firstName : 'John',
    lastName: 'Smith',
    email: 'at@gmail.com',
    password: 'sdadawq3ewet56yrtgsdfad',
    active: true,
    verified: true,
    idImageDTO: 1,
    src: 'ne znam sta je src'  
  };

  beforeEach(() => {
    const toastrMocked = {
        success: jasmine.createSpy('success'),
        error: jasmine.createSpy('error')
    };
    let regUserServiceMock = {
         editUser: jasmine.createSpy('editUser')
         .and.returnValue(of({body: {}})),
    };
  
    let adminServiceMock = {
        editAdmin: jasmine.createSpy('editAdmin')
         .and.returnValue(of({body: {}})),
    };
  
    let imageServiceMock = {
        add: jasmine.createSpy('add')
        .and.returnValue(of(1)),
    };
    let routerMock= {
        navigate: jasmine.createSpy('navigate')
    };
    let authenticationServiceMock = {

    };
    TestBed.configureTestingModule({
        declarations: [ EditProfileComponent ],
        imports: [ToastrModule.forRoot(), ReactiveFormsModule, FormsModule, MatDialogModule],
        providers:    [ {provide: RegisteredUserService, useValue: regUserServiceMock },
                        {provide: AdminService, useValue: adminServiceMock },
                        {provide: ImageService, useValue: imageServiceMock },
                        {provide: AuthenticationService, useValue: authenticationServiceMock},
                        { provide: MatDialogRef, useClass: MatDialogRefMock},
                        { provide: Router, useValue: routerMock },
                        { provide: ToastrService, useValue: toastrMocked },
                        { provide: MAT_DIALOG_DATA, useValue: mockUser }]
        });
    fixture = TestBed.createComponent(EditProfileComponent);
    component = fixture.componentInstance;
    regUserService = TestBed.inject(RegisteredUserService);
    adminService = TestBed.inject(AdminService);
    authenticationService = TestBed.inject(AuthenticationService);
    imageService = TestBed.inject(ImageService);
    router = TestBed.inject(Router);
    toastr = TestBed.inject(ToastrService);
    dialogRef = TestBed.inject(MatDialogRef);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  it('should set form values in constructor', async(() => {
    fixture.whenStable()
      .then(() => {
        expect(component.form).toBeDefined();
        console.log(component.form.value);
        expect(component.form.value.firstName).toEqual('John'); 
        expect(component.form.value.lastName).toEqual('Smith');
        expect(component.form.value.email).toEqual('at@gmail.com');
        expect(component.form.value.image).toEqual(null);
        fixture.detectChanges();        
        let firstNameInput = fixture.debugElement.query(By.css('#firstName')).nativeElement;
        let lastNameInput = fixture.debugElement.query(By.css('#lastName')).nativeElement;
        
        expect(firstNameInput.value).toEqual('John');
        expect(lastNameInput.value).toEqual('Smith');
      });
  }));

  it('should close dialog on cancel', async(() => {
    spyOn(dialogRef, 'close');
    component.cancel();
    expect(dialogRef.close).toHaveBeenCalled();   
  }));

  it('should set input in reactive form', fakeAsync(() => {
    fixture.detectChanges();  
    fixture.whenStable().then(() => {
        expect(fixture.debugElement.query(By.css('#firstName')).nativeElement.value).toEqual('John');
        expect(fixture.debugElement.query(By.css('#lastName')).nativeElement.value).toEqual('Smith');
        
        let firstName = fixture.debugElement.query(By.css('#firstName')).nativeElement;
        firstName.value = 'new first name';
        let lastName = fixture.debugElement.query(By.css('#lastName')).nativeElement;
        lastName.value = 'new last name';

        firstName.dispatchEvent(new Event('input')); 
        lastName.dispatchEvent(new Event('input'));

        let controlFirstName = component.form.controls['firstName'];
        let controlLastName = component.form.controls['lastName'];
       
        expect(controlFirstName.value).toEqual('new first name');
        expect(controlLastName.value).toEqual('new last name');
      });   
  }));

  it('should save reg user changes', fakeAsync(() =>{
    component.role = "ROLE_REGISTERED_USER";
    spyOn(dialogRef, 'close'); 
    component.saveChanges();
    tick(15000);

    expect(regUserService.editUser).toHaveBeenCalled(); 
    expect(toastr.success).toHaveBeenCalled();
    expect(dialogRef.close).toHaveBeenCalled();
  }));

  it('should save admin changes', fakeAsync(() => {
    component.role = "ROLE_ADMIN";
    spyOn(dialogRef, 'close'); 
    component.saveChanges();
    tick(15000);

    expect(adminService.editAdmin).toHaveBeenCalled(); 
    expect(toastr.success).toHaveBeenCalled();
    expect(dialogRef.close).toHaveBeenCalled();
  }));

  it('should save image', fakeAsync(() => {
    component.saveImage();
    expect(imageService.add).toHaveBeenCalled();
    expect(component.user.idImageDTO).toEqual(1);
  }));

});
