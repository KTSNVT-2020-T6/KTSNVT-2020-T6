import { TestBed, getTestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { fakeAsync, tick} from '@angular/core/testing';
import { HttpClient } from '@angular/common/http';

import { Post } from '../../model/Post';
import { Img } from '../../model/Image';
import { ImageService } from './image.service';

describe('ImageService', () => {
  let injector;
  let imageService: ImageService;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;

  beforeEach(() => {

    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
       providers: [ImageService]
    });

    injector = getTestBed();
    imageService = TestBed.inject(ImageService);
    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
  });
  
  afterEach(() => {
    httpMock.verify();
  });

  it('add() should query url and create new image', fakeAsync(() => {
    let  formData = new FormData();
    formData.append('file', "D:\Control Panel.{21EC2020-3AEA-1069-A2DD-08002B30309D}\New folder\Private\Instagram-prebaceno OKTOBAR 2020\projekat" );
    
    const formDataMock = new FormData();
    formDataMock.append('file', "D:\Control Panel.{21EC2020-3AEA-1069-A2DD-08002B30309D}\New folder\Private\Instagram-prebaceno OKTOBAR 2020\projekat" );

    
    imageService.add(formData).subscribe(res => formData = res);
    
    const req = httpMock.expectOne('https://localhost:8443/api/image');
    expect(req.request.method).toBe('POST');
    req.flush(formDataMock);
    tick();
 
    expect(formData).toBeDefined();  
}));

it('getImage() should query url and get image by id', fakeAsync(() => {
    let image = new ArrayBuffer(8);

    const mockImage = new ArrayBuffer(8);

    imageService.getImage(1).subscribe(res => image = res.body);
    
    const req = httpMock.expectOne('https://localhost:8443/api/image/1');
    expect(req.request.method).toBe('GET');
    req.flush(mockImage);
    tick();

    expect(image).toBeDefined();
  }));
  it("should throw error on add image",()=> {
    let  formData = new FormData();
    formData.append('file', "D:\Control Panel.{21EC2020-3AEA-1069-A2DD-08002B30309D}\New folder\Private\Instagram-prebaceno OKTOBAR 2020\projekat" );
    
    const formDataMock = new FormData();
    formDataMock.append('file', "D:\Control Panel.{21EC2020-3AEA-1069-A2DD-08002B30309D}\New folder\Private\Instagram-prebaceno OKTOBAR 2020\projekat" );


    let error:string = '';
    imageService.add(formData).subscribe(null,e => {
      error = e.statusText;
    });
    const req = httpMock.expectOne('https://localhost:8443/api/image');
    expect(req.request.method).toBe('POST');
    req.flush("Error on server",{
      status: 404,
      statusText: 'Error on server'
    });
   
    expect(error.toString().indexOf("Error on server") >= 0).toBeTruthy();
  });

});