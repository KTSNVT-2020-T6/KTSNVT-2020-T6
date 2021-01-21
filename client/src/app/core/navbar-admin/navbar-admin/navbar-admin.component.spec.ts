import { ComponentFixture, discardPeriodicTasks, flush, inject, TestBed } from '@angular/core/testing';
import {  Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { fakeAsync, tick } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { async, Observable, of } from 'rxjs';
import { RouterTestingModule} from '@angular/router/testing';
import { RouterLinkWithHref } from '@angular/router';
import { PostsPageComponent } from 'src/app/pages/posts-page/posts-page.component';
import { LoginPageComponent } from 'src/app/pages/login-page/login-page.component';
import { RegisterPageComponent } from 'src/app/pages/register-page/register-page.component';
import { HomePageComponent } from 'src/app/pages/home-page/home-page/home-page.component';
import { SpyLocation } from '@angular/common/testing';
import { Component, DebugElement, NO_ERRORS_SCHEMA } from '@angular/core';
import { Location } from '@angular/common';
import { NavbarAdminComponent } from './navbar-admin.component';
import { AuthenticationService } from 'src/app/pages/services/authentication/authentication.service';
import { FavoriteComponent } from 'src/app/pages/favorite/favorite.component';
import { ProfileDetailsComponent } from 'src/app/pages/profile-details/profile-details.component';
import { AppComponent } from 'src/app/app.component';
import { MatCardModule } from '@angular/material/card';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatDialog } from '@angular/material/dialog';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MatMenuModule } from '@angular/material/menu';
import { MatButtonModule } from '@angular/material/button';
class MdDialogMock {
    open() {
      return {
        afterClosed: jasmine.createSpy('afterClosed').and.returnValue(of(true))
      };
    }
  };
describe('NavbarAdminComponent', () => {
  let component: NavbarAdminComponent;
  let fixture: ComponentFixture<NavbarAdminComponent>;
  let location: Location;
  let router: Router;
  let dialog: MdDialogMock;
  let debugElement: DebugElement;
  let authenticationService: any;
  
  beforeEach(() => {
    let dialogMock = {
        open: jasmine.createSpy('open').and.callThrough(),
        afterClosed: jasmine.createSpy('afterClosed').and.callThrough(),
      }
    let routerMock = {
      navigate: jasmine.createSpy('navigate')
    };
    let authenticationServiceMock ={
        signOut: jasmine.createSpy('signOut').and.returnValue(of({ subscribe: () => {} })),
    }
    TestBed.configureTestingModule({
       declarations: [HomePageComponent , NavbarAdminComponent],
       imports: [ MatToolbarModule,MatCardModule, MatIconModule,NoopAnimationsModule,
        MatMenuModule,
        RouterTestingModule.withRoutes([
            {path: '', component: HomePageComponent},
            {path: 'posts', component: PostsPageComponent},
            {path: 'profileDetails', component: ProfileDetailsComponent},
            
        ])
      ],
       providers:    [ 
            { provide: AuthenticationService, useValue: authenticationServiceMock },
            { provide: MatDialog, useClass: MdDialogMock}]       
    });
   
    location = TestBed.get(Location);
    fixture = TestBed.createComponent(NavbarAdminComponent);
    debugElement = fixture.debugElement;
    component = fixture.componentInstance;
    authenticationService = TestBed.inject(AuthenticationService);
    router = TestBed.get(Router);
  }); 
  it('should create commponent', fakeAsync(() => {
    expect(component).toBeTruthy();
  }));
  it('check redirection on home page', fakeAsync(() => {
    fixture.detectChanges();
    //we trigger a click on our link
    debugElement.query(By.css('#home')).nativeElement.click();
    tick();
    expect(location.path()).toBe('/');
   }));
  it('check redirection on news page', fakeAsync(() => {
    fixture.detectChanges();
    //we trigger a click on our link
    debugElement.query(By.css('#news')).nativeElement.click();
    tick();
    expect(location.path()).toBe('/posts');
  }));

  it('check redirection on page for profile details', fakeAsync(() => {
    fixture.detectChanges();
    //we trigger a click on our link
    debugElement.query(By.css('#profile')).nativeElement.click();
    tick();
    expect(location.path()).toBe('/profileDetails');
  }));
  it('check redirection on home page when logout', fakeAsync(() => {
    spyOn(router, 'navigate');
    fixture.detectChanges();
    tick();
    component.signOut();
    expect(authenticationService.signOut).toHaveBeenCalled();
    tick();
    expect(router.navigate).toHaveBeenCalledWith(['/login']);

  }));
  it('should  have the menu open', fakeAsync(() => {
    
  }));
  it('should not have the menu open', async () => {
    let matMenu = fixture.nativeElement.parentNode.querySelector('.mat-menu-panel');
    expect(matMenu).toBeFalsy();

  });
  it('should mat-menu and open add new cultural offer on mat-menu click button add-new-co', fakeAsync(() => {
   
  })); 
  it('should open add new admin on mat-menu click button add-new-admin', fakeAsync(() => {
    
  }));
  

});