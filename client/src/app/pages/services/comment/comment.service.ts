import { Injectable } from '@angular/core';
import { HttpClientModule, HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';
import { CulturalOffer } from '../../model/CulturalOffer';
import { map } from 'rxjs/operators';
import { Comment } from '../../model/Comment';

@Injectable({
	providedIn: 'root'
})
export class CommentService {
	private headers = new HttpHeaders({'Content-Type': 'application/json'});

	private RegenerateData = new Subject<void>();

    RegenerateData$ = this.RegenerateData.asObservable();

    announceChange() {
        this.RegenerateData.next();
	}

	constructor(
		private http: HttpClient
	) {}

	getPage(page: number, size: number, culturalOfferId:number): Observable<any> {
		let queryParams = {};
		queryParams = {
			headers: this.headers,
			observe: 'response',
			params: new HttpParams()
				.set('page', String(page))
				.append('size', String(size)),
		};
		return this.http.get('http://localhost:8080/api/comment/page/'+culturalOfferId, queryParams);
		
	}
	getComment(id: number):Observable<any>{
		let queryParams = {};
		queryParams = {
			headers: this.headers,
			observe: 'response',
			params: new HttpParams()
		};
		return this.http.get('http://localhost:8080/api/comment/'+id, queryParams).pipe(map(res => res));
	}
	update(editComment: Comment, id: number): Observable<any> {
		return this.http.put('http://localhost:8080/api/comment/'+ id, editComment, {headers: this.headers, responseType: 'json'});
	}
	save(comment: Comment):Observable<any> {
		return this.http.post('http://localhost:8080/api/comment', comment, {headers: this.headers, responseType: 'json'});
	}
	delete(id: number): Observable<any> {
		return this.http.delete('http://localhost:8080/api/comment/'+ id, {headers: this.headers, responseType: 'text'});
	}
}
