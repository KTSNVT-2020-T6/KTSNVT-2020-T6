import { Injectable } from '@angular/core';
import { HttpClientModule, HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';
import { CulturalOffer } from '../../model/CulturalOffer';
import { map } from 'rxjs/operators';
import { Comment } from '../../model/Comment';
import { environment } from 'src/environments/environment';

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
		
		return this.http.get(`${environment.baseUrl}/${environment.comment}/page/${culturalOfferId}`, queryParams);
		
	}
	getComment(id: number):Observable<any>{
		let queryParams = {};
		queryParams = {
			headers: this.headers,
			observe: 'response',
			params: new HttpParams()
		};
		
		return this.http.get(`${environment.baseUrl}/${environment.comment}/${id}`, queryParams).pipe(map(res => res));
	}
	update(editComment: Comment, id: number): Observable<any> {
		return this.http.put(`${environment.baseUrl}/${environment.comment}/${id}`, editComment, {headers: this.headers, responseType: 'json'});
	}
	save(comment: Comment):Observable<any> {
		return this.http.post(`${environment.baseUrl}/${environment.comment}`, comment, {headers: this.headers, responseType: 'json'});
	}
	delete(id: number): Observable<any> {
		return this.http.delete(`${environment.baseUrl}/${environment.comment}/${id}`, {headers: this.headers, responseType: 'text'});
	}
}
