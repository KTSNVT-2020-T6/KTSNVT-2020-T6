import { ComponentFixture, TestBed } from '@angular/core/testing';
import {  Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { fakeAsync, tick } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { Observable, of } from 'rxjs';
import { ProfileDetailsComponent } from './profile-details.component';
import { UserService } from '../../../core/services/user/user.service';
import { RegisteredUserService } from '../../../core/services/registered-user/registered-user.service';
import { AdminService } from '../../../core/services/admin/admin.service';
import { ImageService } from '../../../core/services/image/image.service';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { MatDialog, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { AuthenticationService } from '../../../core/services/authentication/authentication.service';
import { MatCardModule } from '@angular/material/card';
import { EditProfileComponent } from '../edit-profile/edit-profile.component';
import { ConfirmationComponent } from '../../shared/confirmation/confirmation.component';
import { JwtHelperService } from '@auth0/angular-jwt';

class MdDialogMock {
  open(): any{
    return {
      afterClosed: jasmine.createSpy('afterClosed').and.returnValue(of(true))
    };
  }
}
describe('ProfileDetailsComponent', () => {
  let profileComponent: ProfileDetailsComponent;
  let fixture: ComponentFixture<ProfileDetailsComponent>;
  let userService: any;
  let adminService: any;
  let regUserService: any;
  let imageService: any;
  let authenticationService: any;
  let dialog: MatDialog;
  let router: any;
  let jwtHelper: any;

  beforeEach(() => {
    const dialogMock = {
      open: jasmine.createSpy('open').and.callThrough(),
      afterClosed: jasmine.createSpy('afterClosed').and.callThrough(),
    };
    const userServiceMock = {
      getCurrentUser: jasmine.createSpy('getCurrentUser')
        .and.returnValue(of({body: {
            id: 1,
            firstName: 'Ana',
            lastName: 'Anic',
            email: 'aan@gmail.com',
            password: 'asdf',
            active:  true,
            verified: true,
            idImageDTO: 1,
            src: ''
          }})),
    };
    const regUserServiceMock = {
      delete: jasmine.createSpy('delete').and.returnValue({ subscribe: () => {} })};
    const adminServiceMock = {
      delete: jasmine.createSpy('delete').and.returnValue({ subscribe: () => {} })};
    const imageServiceMock = {
      getImage: jasmine.createSpy('getImage')
        .and.returnValue(of({body: ''}))
    };
    const authenticationServiceMock = {
      signOut: jasmine.createSpy('signOut')
    };
    const routerMock = {
        navigate: jasmine.createSpy('navigate')
    };
    const jwtServiceMocked = {
      decodeToken: jasmine.createSpy('decodeToken').and.returnValue({role: 'ROLE_ADMIN'})
    };
    TestBed.configureTestingModule({
       declarations: [ ProfileDetailsComponent ],
       imports: [ FormsModule, RouterModule, ToastrModule.forRoot(), MatCardModule],
       providers: [
        { provide: UserService, useValue: userServiceMock },
        { provide: RegisteredUserService, useValue: regUserServiceMock },
        { provide: AdminService, useValue: adminServiceMock },
        { provide: ImageService, useValue: imageServiceMock },
        { provide: AuthenticationService, useValue: authenticationServiceMock },
        { provide: Router, useValue: routerMock },
        { provide: MatDialog, useClass: MdDialogMock},
        { provide: JwtHelperService, useValue: jwtServiceMocked}
       ]
    });

    fixture = TestBed.createComponent(ProfileDetailsComponent);
    profileComponent = fixture.componentInstance;
    userService = TestBed.inject(UserService);
    regUserService = TestBed.inject(RegisteredUserService);
    imageService = TestBed.inject(ImageService);
    adminService = TestBed.inject(AdminService);
    authenticationService = TestBed.inject(AuthenticationService);
    dialog = TestBed.inject(MatDialog);
    router = TestBed.inject(Router);
    jwtHelper = TestBed.inject(JwtHelperService);
  });
  it('should create commponent', fakeAsync(() => {
    expect(profileComponent).toBeTruthy();
  }));
  it('should fetch user on init', fakeAsync(() => {
    profileComponent.ngOnInit();
    expect(userService.getCurrentUser).toHaveBeenCalled();
    tick();
    // should fetch user from service
    expect(profileComponent.user.id).toBe(1);
    expect(profileComponent.user.firstName).toEqual('Ana');
    expect(profileComponent.user.lastName).toEqual('Anic');
    expect(profileComponent.user.email).toEqual('aan@gmail.com');
    expect(profileComponent.user.password).toEqual('asdf');
    fixture.detectChanges(); // tell angular that data are fetched
    tick(); // initiate next cycle of binding these data to HTML components
    fixture.detectChanges(); // detect changes in the HTML components
    // assert that values in the HTML components are as expected
    const firstName = fixture.debugElement.query(By.css('table tr #firstName')).nativeElement;
    expect(firstName.textContent).toEqual('Ana');
    const lastName = fixture.debugElement.query(By.css('table tr #lastName')).nativeElement;
    expect(lastName.textContent).toEqual('Anic');
    const email = fixture.debugElement.query(By.css('table tr #email')).nativeElement;
    expect(email.textContent).toEqual('aan@gmail.com');
  }));

  it('Open dialog for editing profile', fakeAsync(() => {
    spyOn(dialog, 'open').and.callThrough();
    profileComponent.edit();
    expect(dialog.open).toHaveBeenCalled();
  }));

  it('Open dialog for changing password', fakeAsync(() => {
    spyOn(dialog, 'open').and.callThrough();
    profileComponent.editPassword();
    expect(dialog.open).toHaveBeenCalled();
  }));

  it('Open dialog for deactivate profile and check deactivating button', fakeAsync(() => {
    const role = 'ROLE_ADMIN';
    const user = {
      id: 1,
      firstName: 'Ana',
      lastName: 'Anic',
      email: 'aan@gmail.com',
      password: 'asdf',
      active:  true,
      verified: true,
      idImageDTO: 1,
      src: ''
    };
    profileComponent.role = role;
    profileComponent.user = user;
    spyOn(dialog, 'open').and.callThrough();
    spyOn(profileComponent, 'deleteProfile');
    profileComponent.confirmDialog();
    expect(dialog.open).toHaveBeenCalled();
    expect(profileComponent.deleteProfile).toHaveBeenCalled();
  }));
});
