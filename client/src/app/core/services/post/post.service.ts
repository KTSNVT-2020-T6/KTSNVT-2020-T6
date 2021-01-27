import { Injectable } from '@angular/core';
import { HttpClientModule, HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Post } from '../../model/Post';
import { Observable, Subject } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export class PostService {

    private headers = new HttpHeaders({'Content-Type': 'application/json'});
    private RegenerateData = new Subject<void>();
    RegenerateData$ = this.RegenerateData.asObservable();
    announceChange(): void {
        this.RegenerateData.next(); }
    constructor(
        private http: HttpClient
    ) {}

    addPost(newPost: Post): Observable<any>{
        return this.http.post(`${environment.baseUrl}/${environment.post}`, newPost, {headers: this.headers, responseType: 'json'});
    }

    getPage(page: number, size: number): Observable<any> {
      let queryParams = {};
      queryParams = {
      headers: this.headers,
      observe: 'response',
      params: new HttpParams()
        .set('page', String(page))
        .append('size', String(size))
        .append('sort', 'date,desc'),
      };
      return this.http.get(`${environment.baseUrl}/${environment.post}/`, queryParams).pipe(map(res => res));
    }
    delete(postId: number): Observable<any>{
      return this.http.delete(`${environment.baseUrl}/${environment.post}/${postId}`, {headers: this.headers, responseType: 'text'});
    }

}
