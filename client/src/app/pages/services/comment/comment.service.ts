import { Injectable } from '@angular/core';
import { HttpClientModule, HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CulturalOffer } from '../../model/CulturalOffer';
import { map } from 'rxjs/operators';
import { Comment } from '../../model/Comment';

@Injectable({
	providedIn: 'root'
})
export class CommentService {
	private headers = new HttpHeaders({'Content-Type': 'application/json'});

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
	save(comment: Comment):Observable<any> {
		return this.http.post('http://localhost:8080/api/comment', comment, {headers: this.headers, responseType: 'text'});
	}
}
