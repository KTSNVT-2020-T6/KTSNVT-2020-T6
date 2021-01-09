import { Injectable } from '@angular/core';
import { HttpClientModule, HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Post } from '../../model/Post';

@Injectable({
    providedIn: 'root'
})
export class PostService {
    private headers = new HttpHeaders({'Content-Type': 'application/json'});

	constructor(
		private http: HttpClient
    ) {}
    
    addPost(newPost: Post): Observable<any>{
        return this.http.post('http://localhost:8080/api/post', newPost, {headers: this.headers, responseType: 'text'});
    }

}