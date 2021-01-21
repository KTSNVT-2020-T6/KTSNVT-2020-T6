import { TestBed, getTestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { fakeAsync, tick} from '@angular/core/testing';
import { HttpClient } from '@angular/common/http';

import { PostService } from './post.service';
import { Post } from '../../model/Post';
import { Img } from '../../model/Image';

describe('PostService', () => {
  let injector;
  let postService: PostService;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;

  beforeEach(() => {

    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
       providers: [PostService]
    });

    injector = getTestBed();
    postService = TestBed.inject(PostService);
    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
  });
  
  afterEach(() => {
    httpMock.verify();
  });

  it('addPost() should query url and create new post', fakeAsync(() => {
    
    const mockImage: Img = {
        id: 1,
	    description: 'image of gallery',
        relativePath: 'relPath.jpg'
    }
    
    let post: Post = {
        text: 'Exhibition of paintings by artist Uros Predic',
        date: new Date('2021-05-05'),
        imageDTO: mockImage,
        culturalOfferId: 1,
        culturalOfferName: 'Gallery Paja Jovanovic'
    };

    const mockPost: Post = {
        id: 1,
        text: 'Exhibition of paintings by artist Uros Predic',
        date: new Date('2021-05-05'),
        imageDTO: mockImage,
        culturalOfferId: 1,
        culturalOfferName: 'Gallery Paja Jovanovic'
    };
    
    postService.addPost(post).subscribe(res => post = res);
    
    const req = httpMock.expectOne('http://localhost:8080/api/post');
    expect(req.request.method).toBe('POST');
    req.flush(mockPost);
    tick();
 
    expect(post).toBeDefined();
    expect(post.id).toEqual(1);
    expect(post.text).toEqual('Exhibition of paintings by artist Uros Predic');
    expect(post.date).toEqual(new Date('2021-05-05')),
    expect(post.imageDTO).toEqual(mockImage);
    expect(post.culturalOfferId).toEqual(1);
    expect(post.culturalOfferName).toEqual('Gallery Paja Jovanovic');
  
}));


  it('getPage() should query url and get post page', fakeAsync(() => {
    let posts!: Post[];

    const mockImage: Img = {
        id: 1,
	    description: 'image of gallery',
        relativePath: 'relPath.jpg'
    }

    const mockPosts: Post[] = [
        {
            id: 1,
            text: 'Exhibition of paintings by artist Uros Predic',
            date: new Date('2021-06-05'),
            imageDTO: mockImage,
            culturalOfferId: 1,
            culturalOfferName: 'Gallery Paja Jovanovic'
        },
        {
            id: 2,
            text: 'Exhibition of paintings by artist Jovana Kacanski',
            date: new Date('2021-07-05'),
            imageDTO: mockImage,
            culturalOfferId: 2,
            culturalOfferName: 'Gallery Kasandra'
        }
    ];
    
    postService.getPage(0, 2).subscribe(res => posts = res.body);
    
    const req = httpMock.expectOne('http://localhost:8080/api/post/?page=0&size=2&sort=date,desc');
    expect(req.request.method).toBe('GET');
    req.flush(mockPosts);
    tick();
 
    expect(posts.length).toEqual(2, 'should contain given amount of cultural offers');
    expect(posts[0].id).toEqual(1);
    expect(posts[0].text).toEqual('Exhibition of paintings by artist Uros Predic');
    expect(posts[0].date).toEqual(new Date('2021-06-05'));
    expect(posts[0].imageDTO).toEqual(mockImage);
    expect(posts[0].culturalOfferId).toEqual(1);
    expect(posts[0].culturalOfferName).toEqual('Gallery Paja Jovanovic');
    

    expect(posts[1].id).toEqual(2);
    expect(posts[1].text).toEqual('Exhibition of paintings by artist Jovana Kacanski');
    expect(posts[1].date).toEqual(new Date('2021-07-05'));
    expect(posts[1].imageDTO).toEqual(mockImage);
    expect(posts[1].culturalOfferId).toEqual(2);
    expect(posts[1].culturalOfferName).toEqual('Gallery Kasandra');
  }));

  it('delete() should query url and delete post', fakeAsync(() => {
    postService.delete(1).subscribe(res => {});
    
    const req = httpMock.expectOne('http://localhost:8080/api/post/1');
    expect(req.request.method).toBe('DELETE');
    req.flush({});
  }));

  
});