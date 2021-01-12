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

    getPage(page: number, size: number): Observable<any> {
		let queryParams = {};
		queryParams = {
			headers: this.headers,
			observe: 'response',
			params: new HttpParams()
				.set('page', String(page))
				.append('size', String(size))
				.append('sort','date,desc'),
		};
		return this.http.get('http://localhost:8080/api/post/', queryParams).pipe(map(res => res));
		
	}

}