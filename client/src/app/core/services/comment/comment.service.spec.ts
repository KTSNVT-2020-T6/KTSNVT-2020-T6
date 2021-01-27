import { TestBed, getTestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { fakeAsync, tick} from '@angular/core/testing';
import { HttpClient } from '@angular/common/http';


import { Comment } from '../../model/Comment';
import { CommentService } from './comment.service';
import { Img } from '../../model/Image';

describe('CommentService', () => {
  let injector;
  let commentService: CommentService;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;

  beforeEach(() => {

    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
       providers: [CommentService]
    });

    injector = getTestBed();
    commentService = TestBed.inject(CommentService);
    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('getComment() should query url and get comment by id', fakeAsync(() => {
    let comment!: Comment;

    const mockImageUser: Img = {
        id: 1,
        description: 'user image',
        relativePath: 'relPath.jpg'
    };

    const mockImageComment: Img = {
        id: 2,
        description: 'comment image',
        relativePath: 'relPath1.jpg'
    };

    const mockComment: Comment = {
        id: 1,
        text: 'Last week exibitions was great!',
       // date: new Date(),
        nameSurname: 'Nebojsa Pecalica',
        userId: 1,
        userImage: mockImageUser,
        imageDTO: mockImageComment,
        culturalOfferId: 1
    };

    commentService.getComment(1).subscribe(res => comment = res.body);

    const req = httpMock.expectOne('https://localhost:8443/api/comment/1');
    expect(req.request.method).toBe('GET');
    req.flush(mockComment);
    tick();

    expect(comment).toBeDefined();
    expect(comment.id).toEqual(1);
    expect(comment.text).toEqual('Last week exibitions was great!');
    // expect(comment.date).toEqual(new Date());
    expect(comment.nameSurname).toEqual('Nebojsa Pecalica');
    expect(comment.userId).toEqual(1);
    expect(comment.userImage).toEqual(mockImageUser);
    expect(comment.imageDTO).toEqual(mockImageComment);
    expect(comment.culturalOfferId).toEqual(1);
  }));

  it('getPage() should query url and get cultural offer page', fakeAsync(() => {
    let comments!: Comment[];

    const mockImageUser: Img = {
        id: 1,
        description: 'user image',
        relativePath: 'relPath.jpg'
    };

    const mockImageComment: Img = {
        id: 2,
        description: 'comment image',
        relativePath: 'relPath1.jpg'
    };

    const mockComments: Comment[] = [
        {
            id: 1,
            text: 'Last week exibitions was great!',
            nameSurname: 'Nebojsa Pecalica',
            userId: 1,
            userImage: mockImageUser,
            imageDTO: mockImageComment,
            culturalOfferId: 1
        },
        {
            id: 2,
            text: 'It was great expirience!',
            nameSurname: 'Nebojsa Pecalica',
            userId: 1,
            userImage: mockImageUser,
            culturalOfferId: 1
        }
    ];

    commentService.getPage(0, 2, 1).subscribe(res => comments = res.body);

    const req = httpMock.expectOne('https://localhost:8443/api/comment/page/1?page=0&size=2');
    expect(req.request.method).toBe('GET');
    req.flush(mockComments);
    tick();

    expect(comments.length).toEqual(2, 'should contain given amount of cultural offers');
    expect(comments[0].id).toEqual(1);
    expect(comments[0].text).toEqual('Last week exibitions was great!');
    expect(comments[0].nameSurname).toEqual('Nebojsa Pecalica');
    expect(comments[0].userId).toEqual(1);
    expect(comments[0].userImage).toEqual(mockImageUser);
    expect(comments[0].imageDTO).toEqual(mockImageComment);
    expect(comments[0].culturalOfferId).toEqual(1);

    expect(comments[1].id).toEqual(2);
    expect(comments[1].text).toEqual('It was great expirience!');
    expect(comments[1].nameSurname).toEqual('Nebojsa Pecalica');
    expect(comments[1].userId).toEqual(1);
    expect(comments[1].userImage).toEqual(mockImageUser);
    expect(comments[1].culturalOfferId).toEqual(1);
  }));


  it('save() should query url and create new comment', fakeAsync(() => {

    const mockImageUser: Img = {
        id: 1,
        description: 'user image',
        relativePath: 'relPath.jpg'
    };

    let comment: Comment = {
        text: 'It was great expirience!',
        nameSurname: 'Nebojsa Pecalica',
        userId: 1,
        userImage: mockImageUser,
        culturalOfferId: 1

    };

    const mockComment: Comment = {
        id: 1,
        text: 'It was great expirience!',
        nameSurname: 'Nebojsa Pecalica',
        userId: 1,
        userImage: mockImageUser,
        culturalOfferId: 1
    };

    commentService.save(comment).subscribe(res => comment = res);

    const req = httpMock.expectOne('https://localhost:8443/api/comment');
    expect(req.request.method).toBe('POST');
    req.flush(mockComment);
    tick();

    expect(comment).toBeDefined();
    expect(comment.id).toEqual(1);
    expect(comment.text).toEqual('It was great expirience!');
    expect(comment.nameSurname).toEqual('Nebojsa Pecalica');
    expect(comment.userId).toEqual(1);
    expect(comment.userImage).toEqual(mockImageUser);
    expect(comment.culturalOfferId).toEqual(1);

  }));

  it('update() should query url and edit comment', fakeAsync(() => {
    const mockImageUser: Img = {
        id: 1,
        description: 'user image',
        relativePath: 'relPath.jpg'
    };


    let comment: Comment = {
        id: 1,
        text: 'It was great expirience!',
        nameSurname: 'Nebojsa Pecalica',
        userId: 1,
        userImage: mockImageUser,
        culturalOfferId: 1
    };

    const mockComment: Comment = {
        id: 1,
        text: 'It was great expirience!',
        nameSurname: 'Nebojsa Pecalica',
        userId: 1,
        userImage: mockImageUser,
        culturalOfferId: 1
    };

    commentService.update(comment, 1).subscribe(res => comment = res);

    const req = httpMock.expectOne('https://localhost:8443/api/comment/1');
    expect(req.request.method).toBe('PUT');
    req.flush(mockComment);
    tick();

    expect(comment).toBeDefined();
    expect(comment.id).toEqual(1);
    expect(comment.text).toEqual('It was great expirience!');
    expect(comment.nameSurname).toEqual('Nebojsa Pecalica');
    expect(comment.userId).toEqual(1);
    expect(comment.userImage).toEqual(mockImageUser);
    expect(comment.culturalOfferId).toEqual(1);
  }));

  it('delete() should query url and delete comment', fakeAsync(() => {
    commentService.delete(1).subscribe(res => {});

    const req = httpMock.expectOne('https://localhost:8443/api/comment/1');
    expect(req.request.method).toBe('DELETE');
    req.flush({});
  }));

  it('should throw deleting error', () => {
    let errorr = '';
    commentService.delete(1).subscribe({error: (e) => errorr = e.statusText});
    const req = httpMock.expectOne('https://localhost:8443/api/comment/1');
    expect(req.request.method).toBe('DELETE');
    req.flush('Error on server', {
      status: 404,
      statusText: 'Error on server'
    });

    expect(errorr.toString().indexOf('Error on server') >= 0).toBeTruthy();
  });

  it('get comment should throw error', () => {
    let errorr = '';
    commentService.getComment(1).subscribe({error: (e) => errorr = e.statusText});
    const req = httpMock.expectOne('https://localhost:8443/api/comment/1');
    expect(req.request.method).toBe('GET');
    req.flush('Error on server', {
      status: 404,
      statusText: 'Error on server'
    });

    expect(errorr.toString().indexOf('Error on server') >= 0).toBeTruthy();
  });

  it('getPage should throw error', () => {
    let errorr = '';
    commentService.getPage(0, 2, 1).subscribe({error: (e) => errorr = e.statusText});
    const req = httpMock.expectOne('https://localhost:8443/api/comment/page/1?page=0&size=2');
    expect(req.request.method).toBe('GET');
    req.flush('Error on server', {
      status: 404,
      statusText: 'Error on server'
    });

    expect(errorr.toString().indexOf('Error on server') >= 0).toBeTruthy();
  });

  it('save should throw error', () => {
    const mockImageUser: Img = {
      id: 1,
    description: 'user image',
      relativePath: 'relPath.jpg'
    };

    const comment: Comment = {
    text: 'It was great expirience!',
      nameSurname: 'Nebojsa Pecalica',
      userId: 1,
      userImage: mockImageUser,
      culturalOfferId: 1

  };
    let errorr = '';
    commentService.save(comment).subscribe({error: (e) => errorr = e.statusText});

    const req = httpMock.expectOne('https://localhost:8443/api/comment');
    expect(req.request.method).toBe('POST');
    req.flush('Error on server', {
      status: 404,
      statusText: 'Error on server'
    });

    expect(errorr.toString().indexOf('Error on server') >= 0).toBeTruthy();
  });

  it('update should throw error', () => {
    const mockImageUser: Img = {
      id: 1,
    description: 'user image',
      relativePath: 'relPath.jpg'
    };

    const comment: Comment = {
    text: 'It was great expirience!',
      nameSurname: 'Nebojsa Pecalica',
      userId: 1,
      userImage: mockImageUser,
      culturalOfferId: 1

  };
    let errorr = '';
    commentService.update(comment, 1).subscribe({error: (e) => errorr = e.statusText});
    const req = httpMock.expectOne('https://localhost:8443/api/comment/1');
    expect(req.request.method).toBe('PUT');
    req.flush('Error on server', {
      status: 404,
      statusText: 'Error on server'
    });

    expect(errorr.toString().indexOf('Error on server') >= 0).toBeTruthy();
  });

});
