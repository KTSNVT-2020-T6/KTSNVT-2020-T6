import {  ActivatedRoute, Router, RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { async, ComponentFixture, fakeAsync, TestBed, tick, waitForAsync } from '@angular/core/testing';
import { BrowserModule, By } from '@angular/platform-browser';
import { Observable, of } from 'rxjs';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { MatDialog, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatCardModule } from '@angular/material/card';
import { EditCommentComponent } from './edit-comment.component';
import { ActivatedRouteStub } from 'src/app/testing/router-stubs';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ImageService } from 'src/app/core/services/image/image.service';
import { CommentService } from 'src/app/core/services/comment/comment.service';
import { MaterialModule } from '../../material-module';

class MatDialogRefMock {
    close(value = ''): void {}
}

describe('EditCommentComponent', () => {
  let component: EditCommentComponent;
  let fixture: ComponentFixture<EditCommentComponent>;
  let commentService: any;
  let route: any;
  let imageService: any;
  let dialog: any;
  let toastr: any;

  beforeEach(() => {
    const imageServiceMock = {
      add: jasmine.createSpy('add')
        .and.returnValue(of({body: ''}))
    };
    const commentServiceMock = {
        getComment : jasmine.createSpy('getComment')
        .and.returnValue(of({body: {
        id: 1,
        text: 'I\'m losing it',
        date: new Date(),
        nameSurname: 'Sandro Boticeli',
        userId: 1,
        culturalOfferId: 1,
        }})),
        update : jasmine.createSpy('update')
        .and.returnValue(of({}))
    };



    const activatedRouteStub: ActivatedRouteStub = new ActivatedRouteStub();
    activatedRouteStub.testParamss = {id: 1};


    const toastrMocked = {
        success: jasmine.createSpy('success'),
        error: jasmine.createSpy('error')
    };

    TestBed.configureTestingModule({
       declarations: [ EditCommentComponent ],
       imports: [ FormsModule, ReactiveFormsModule,
        RouterModule, ToastrModule.forRoot(), MatCardModule, MaterialModule, BrowserModule, BrowserAnimationsModule],
       providers:    [
        { provide: ImageService, useValue: imageServiceMock },
        { provide: CommentService, useValue: commentServiceMock },
        { provide: MatDialogRef, useClass: MatDialogRefMock},
        { provide: ActivatedRoute, useValue: activatedRouteStub},
       ]
    });

    fixture = TestBed.createComponent(EditCommentComponent);
    component = fixture.componentInstance;
    imageService = TestBed.inject(ImageService);
    commentService = TestBed.inject(CommentService);
    toastr = TestBed.inject(ToastrService);
    dialog = TestBed.inject(MatDialogRef);
    route = TestBed.inject(ActivatedRoute);
  });

  it('should create commponent', fakeAsync(() => {
    expect(component).toBeTruthy();
  }));

  it('should be initialized', waitForAsync(() => {
    component.ngOnInit();
    expect(commentService.getComment).toHaveBeenCalled();
    expect(component.comment.id).toBe(1);
    expect(component.comment.text).toEqual('I\'m losing it');
    expect(component.comment.date).toBeDefined();
    expect(component.comment.nameSurname).toEqual('Sandro Boticeli');
    expect(component.comment.userId).toEqual(1);
    expect(component.comment.culturalOfferId).toEqual(1);
    expect(component.editForm).toBeDefined();

    fixture.whenStable().then(() => {
        const text = fixture.debugElement.query(By.css('#editText')).nativeElement;
        text.value = component.comment.text;

        text.dispatchEvent(new Event('input'));

        const controlText = component.editForm.controls.text;

        expect(controlText.value).toEqual('I\'m losing it');

      });

    }));

  it('should close dialog on cancel', waitForAsync(() => {
    spyOn(dialog, 'close');
    component.cancelClicked();
    expect(dialog.close).toHaveBeenCalled();
    }));

  it('should set input in reactive form', fakeAsync(() => {
    fixture.detectChanges();

    fixture.whenStable().then(() => {

        expect(fixture.debugElement.query(By.css('#editText')).nativeElement.value).toEqual('I\'m losing it');

        const text = fixture.debugElement.query(By.css('#editText')).nativeElement;
        text.value = 'Now I\'m great';
        text.dispatchEvent(new Event('input'));

        const controlText = component.editForm.controls.text;

        expect(controlText.value).toEqual('Now I\'m great');

        });
  }));

  it('should edit comment with image', fakeAsync(() => {
        component.ngOnInit();
        spyOn(dialog, 'close');
        spyOn(toastr, 'success');
        component.imageAdded = 'D:\Control Panel.{21EC2020-3AEA-1069-A2DD-08002B30309D}\New folder\Private\Instagram-prebaceno OKTOBAR 2020\projekat.jpg';
        component.editForm.controls.text.setValue('Building with very poor horizontal and vertical isolation....');
        component.editForm.controls.image.setValue('D:\Control Panel.{21EC2020-3AEA-1069-A2DD-08002B30309D}\New folder\Private\Instagram-prebaceno OKTOBAR 2020\projekat.jpg');

        component.editComment();


        expect(imageService.add).toHaveBeenCalledTimes(1);
        expect(toastr.success).toHaveBeenCalled();
        expect(component.comment.imageDTO).toBeDefined();
        expect(component.editForm.valid).toBeTruthy();


        expect(commentService.update).toHaveBeenCalledTimes(1);
        expect(dialog.close).toHaveBeenCalledTimes(1);
        expect(toastr.success).toHaveBeenCalled();
    }));

  it('should save edited comment', fakeAsync(() => {

    spyOn(dialog, 'close');
    spyOn(toastr, 'success');
    component.editComment();
    tick(15000);

    expect(commentService.update).toHaveBeenCalled();
    expect(dialog.close).toHaveBeenCalled();
    expect(toastr.success).toHaveBeenCalled();
 }));

});
