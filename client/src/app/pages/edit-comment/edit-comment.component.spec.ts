// import { ComponentFixture, TestBed } from '@angular/core/testing';
// import {  ActivatedRoute, Router, RouterModule } from '@angular/router';
// import { FormsModule, ReactiveFormsModule } from '@angular/forms';
// import { fakeAsync, tick } from '@angular/core/testing';
// import { By } from '@angular/platform-browser';
// import { Observable, of } from 'rxjs';
// import { UserService } from '../services/user/user.service';
// import { ImageService } from '../services/image/image.service';
// import { ToastrModule, ToastrService } from 'ngx-toastr';
// import { MatDialog, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
// import { MatCardModule } from '@angular/material/card';
// import { EditCommentComponent } from './edit-comment.component';
// import { CommentService } from '../services/comment/comment.service';

// class MdDialogMock {
//   open() {
//     return {
//       afterClosed: jasmine.createSpy('afterClosed').and.returnValue(of(true))
//     };
//   }
// };
// describe('EditCommentComponent', () => {
//   let editCommentComponent: EditCommentComponent;
//   let fixture: ComponentFixture<EditCommentComponent>;
//   let commentService: any;
//   let route: any;
//   let userService: any;
//   let imageService:any;
//   let dialog: MdDialogMock;
  

//  beforeEach(() => {
    
//     let dialogMock = {
//       open: jasmine.createSpy('open').and.callThrough(),
//       afterClosed: jasmine.createSpy('afterClosed').and.callThrough(),
//     }
//     let userServiceMock = {
//       getCurrentUser: jasmine.createSpy('getCurrentUser')
//         .and.returnValue(of({body: {  
//             id: 1,
//             firstName: 'Ana',
//             lastName: 'Anic',
//             email: 'aan@gmail.com',
//             password: 'asdf',
//             active:  true,
//             verified: true,
//             idImageDTO: 1,
//             src: '' 
//           }})),
//     };
    
//     let imageServiceMock = {
//       add: jasmine.createSpy('add')
//         .and.returnValue(of({body:''}))
//     }
//     let commentServiceMock = {
//         getComment : jasmine.createSpy('getComment')
//         .and.returnValue(of({body: {
//           id: 1,
// 	        text: "I'm losing it",
// 	        date: new Date(),
//           nameSurname: "Sandro Boticeli",
//           userId: 1,
//           culturalOfferId: 1, 
//         }})),
//           //ne znam ovo verovatno treba drugacije
//           update : jasmine.createSpy('update')
//           .and.returnValue(of({body:''}))
//     };

//     let routerMock= {
//         navigate: jasmine.createSpy('navigate')
//     }
//     TestBed.configureTestingModule({
//        declarations: [ EditCommentComponent ],
//        imports: [ FormsModule, ReactiveFormsModule, RouterModule, ToastrModule.forRoot(), MatCardModule],
//        providers:    [ 
//         { provide: UserService, useValue: userServiceMock },
//         { provide: ImageService, useValue: imageServiceMock },
//         { provide: CommentService, useValue: commentServiceMock },
//         { provide: Router, useValue: routerMock },
//         { provide: MatDialog, useClass: MdDialogMock}
//        ]
//     });

//     fixture = TestBed.createComponent(EditCommentComponent);
//     editCommentComponent = fixture.componentInstance;
//     userService = TestBed.inject(UserService);
//     imageService = TestBed.inject(ImageService);
//     commentService = TestBed.inject(CommentService);
//     dialog = TestBed.get(MatDialog);
//     route = TestBed.inject(Router);
//   }); 
//   it('should create commponent', fakeAsync(() => {
//     expect(editCommentComponent).toBeTruthy();
//   }));
//   it('should fetch student and his enrollments on init in edit mode', fakeAsync(() => {
//     editCommentComponent.ngOnInit();
   
//     expect(commentService.getComment).toHaveBeenCalled(); 
//     tick();
//     // should fetch comment from service
//     expect(editCommentComponent.comment.id).toBe(1);
//     expect(editCommentComponent.comment.text).toEqual("I'm losing it");
//     expect(editCommentComponent.comment.date).toEqual(new Date());
//     expect(editCommentComponent.comment.nameSurname).toEqual("Sandro Boticeli");
//     expect(editCommentComponent.comment.userId).toEqual(1);
//     expect(editCommentComponent.comment.culturalOfferId).toEqual(1);
  
//     //should display fetched comment
//     fixture.detectChanges(); // tell angular that data are fetched
//     tick(); // initiate next cycle of binding these data to HTML components
//     fixture.detectChanges(); // detect changes in the HTML components
//     // assert that values in the HTML components are as expected

//     let text = fixture.debugElement.query(By.css('#editText')).nativeElement;
//     expect(text.textContent).toEqual("I'm losing it");
    
    
//   }));

  

//   //TREBA STAVITI SPY NA EDIT DUGME I ONDA UGASITI DIJALOG AKO JE REKAO EDIT, ali treba da se zapravo zatvori u ts na save

  
  
// });