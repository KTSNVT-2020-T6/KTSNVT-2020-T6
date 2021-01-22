import {ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core'
import {async, fakeAsync, tick} from '@angular/core/testing';



import { of } from 'rxjs';
import { PostsPageComponent } from './posts-page.component';
import { PostService } from '../services/post/post.service';
import { CulturalOfferDetailsService } from '../services/cultural-offer-details/cultural-offer-details.service';
import { ImageService } from '../services/image/image.service';
import { JwtHelperService } from '@auth0/angular-jwt';
import { MatDialog } from '@angular/material/dialog';
import { ToastrModule } from 'ngx-toastr';

class MdDialogMock {
    open() {
      return {
        afterClosed: jasmine.createSpy('afterClosed').and.returnValue(of(true))
      };
    }
  };

describe('PostsPageComponent', () => {
  let component: PostsPageComponent;
  let fixture: ComponentFixture<PostsPageComponent>;
  let postService: any;
  let culturalOfferService: any;
  let imageService: any;
  let dialog: MdDialogMock;
  let jwtHelper: any;

  beforeEach(() => {
    let postServiceMock = {
      getPage: jasmine.createSpy('getPage')
          .and.returnValue(of({body: {content: [{}, {}], totalElements: 2 } })), 
      delete: jasmine.createSpy('delete')
        .and.returnValue(of())
    };

    let culturalOfferServiceMock = {
        getOne: jasmine.createSpy('getOne')
            .and.returnValue(of({body: {}})), 
      };

    let imageServiceMock = {
    getImage: jasmine.createSpy('getImage')
        .and.returnValue(of({body: ''})), 
    };

    const jwtServiceMocked = {
        decodeToken: jasmine.createSpy('decodeToken').and.returnValue({role: 'ROLE_ADMIN'})
      }

    TestBed.configureTestingModule({
       declarations: [ PostsPageComponent ],
       imports: [ToastrModule.forRoot()],
       providers:    [ {provide: PostService, useValue: postServiceMock },
                       {provide: CulturalOfferDetailsService, useValue: culturalOfferServiceMock },
                       {provide: ImageService, useValue: imageServiceMock },
                       { provide: MatDialog, useClass: MdDialogMock},
                       { provide: JwtHelperService, useValue: jwtServiceMocked}]
       });

    fixture = TestBed.createComponent(PostsPageComponent);
    component    = fixture.componentInstance;
    postService = TestBed.inject(PostService);
    culturalOfferService = TestBed.inject(CulturalOfferDetailsService);
    imageService = TestBed.inject(ImageService);
    dialog = TestBed.get(MatDialog);
    jwtHelper = TestBed.inject(JwtHelperService);

  });

  it('should create component', fakeAsync(() => {
    expect(component).toBeTruthy();
  }));

  it('should fetch the posts page on init', async(() => {
    component.ngOnInit();

    expect(postService.getPage).toHaveBeenCalled();
    expect(culturalOfferService.getOne).toHaveBeenCalledTimes(2);

    fixture.whenStable()
      .then(() => {
        expect(component.posts.length).toBe(2); 
        fixture.detectChanges();        
        let elements: DebugElement[] = 
          fixture.debugElement.queryAll(By.css('mat-sidenav-container mat-card'));
        expect(elements.length).toBe(2); 
      });
  }));

  it('should fetch the posts page on changePage', async(() => {
    component.changePage(2);

    expect(postService.getPage).toHaveBeenCalled();
    expect(culturalOfferService.getOne).toHaveBeenCalledTimes(2);

    fixture.whenStable()
      .then(() => {
        expect(component.posts.length).toBe(2); 
        fixture.detectChanges();        
        let elements: DebugElement[] = 
          fixture.debugElement.queryAll(By.css('mat-sidenav-container mat-card'));
        expect(elements.length).toBe(2); 
      });
  }));

  it ('should opet confirmation dialog for deleting a post', () => {
    component.role = "ROLE_ADMIN";
    spyOn(dialog, 'open').and.callThrough();
    component.confirmDialog(1);

    expect(dialog.open).toHaveBeenCalled();
    expect(postService.delete).toHaveBeenCalled();
  }); 

});
