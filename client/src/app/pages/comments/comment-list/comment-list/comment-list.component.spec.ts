import {ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { Router } from '@angular/router';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';
import {async, fakeAsync, tick} from '@angular/core/testing';



import { of } from 'rxjs';
import { JwtHelperService } from '@auth0/angular-jwt';
import { MatDialog } from '@angular/material/dialog';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { CommentListComponent } from './comment-list.component';
import { CommentService } from '../../../../core/services/comment/comment.service';
import { UserService } from '../../../../core/services/user/user.service';
import { ImageService } from '../../../../core/services/image/image.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '../../../material-module';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

class MdDialogMock {
    open(): any{
      return {
        afterClosed: jasmine.createSpy('afterClosed').and.returnValue(of(true))
      };
    }
  }

describe('CommentListComponent', () => {
  let component: CommentListComponent;
  let fixture: ComponentFixture<CommentListComponent>;
  let commentService: CommentService;
  let userService: UserService;
  let imageService: ImageService;
  let dialog: MatDialog;
  let jwtHelper: any;
  let toastr: any;

  beforeEach(() => {
    const commentServiceMock = {
      getPage: jasmine.createSpy('getPage')
          .and.returnValue(of({body: {content: [{}, {}], totalElements: 2 } })),
      delete: jasmine.createSpy('delete')
        .and.returnValue(of())
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

    const imageServiceMock = {
    getImage: jasmine.createSpy('getImage')
        .and.returnValue(of({body: ''})),
    };

    const toastrMocked = {
        success: jasmine.createSpy('success'),
        error: jasmine.createSpy('error')
    };

    const jwtServiceMocked = {
        decodeToken: jasmine.createSpy('decodeToken').and.returnValue({role: 'ROLE_ADMIN'})
    };

    TestBed.configureTestingModule({
       declarations: [ CommentListComponent ],
       imports: [FormsModule, ReactiveFormsModule, ToastrModule.forRoot(), NoopAnimationsModule, MaterialModule],
       providers:    [ {provide: CommentService, useValue: commentServiceMock },
                       {provide: UserService, useValue: userServiceMock },
                       {provide: ImageService, useValue: imageServiceMock },
                       { provide: MatDialog, useClass: MdDialogMock},
                       { provide: ToastrService, useValue: toastrMocked },
                       { provide: JwtHelperService, useValue: jwtServiceMocked}]
       });

    fixture = TestBed.createComponent(CommentListComponent);
    component    = fixture.componentInstance;
    commentService = TestBed.inject(CommentService);
    userService = TestBed.inject(UserService);
    imageService = TestBed.inject(ImageService);
    dialog = TestBed.inject(MatDialog);
    toastr = TestBed.inject(ToastrService);
    jwtHelper = TestBed.inject(JwtHelperService);

  });

  it('should create component', fakeAsync(() => {
    expect(component).toBeTruthy();
  }));

  it('should fetch the comment list page on init', waitForAsync(() => {
    component.ngOnInit();
    expect(userService.getCurrentUser).toHaveBeenCalledTimes(1);
    expect(commentService.getPage).toHaveBeenCalled();


    fixture.whenStable()
      .then(() => {
        expect(component.comments.length).toBe(2);
        fixture.detectChanges();
        const elements: DebugElement[] =
          fixture.debugElement.queryAll(By.css('ul li'));
        expect(elements.length).toBe(2);
      });
  }));

  it('should fetch the posts page on changePage', waitForAsync(() => {
    component.changePage(2);

    expect(commentService.getPage).toHaveBeenCalled();

    fixture.whenStable()
      .then(() => {
        expect(component.comments.length).toBe(2);
        fixture.detectChanges();
        const elements: DebugElement[] =
          fixture.debugElement.queryAll(By.css('ul li'));
        expect(elements.length).toBe(2);
      });
  }));

  it ('should opet confirmation dialog for deleting a post', () => {
    spyOn(dialog, 'open').and.callThrough();
    component.confirmDialog(1);

    expect(dialog.open).toHaveBeenCalled();
    expect(commentService.delete).toHaveBeenCalled();
  });

});
