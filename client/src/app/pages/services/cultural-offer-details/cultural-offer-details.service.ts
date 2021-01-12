import { Injectable } from '@angular/core';
import { HttpClientModule, HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CulturalOffer } from '../../model/CulturalOffer';
import { map } from 'rxjs/operators';

@Injectable({
	providedIn: 'root'
})
export class CulturalOfferDetailsService {
	private headers = new HttpHeaders({'Content-Type': 'application/json'});

	constructor(
		private http: HttpClient
	) {}

	getOne(id: any): Observable<any> {
		let queryParams = {};
		queryParams = {
			headers: this.headers,
			observe: 'response',
			params: new HttpParams()
		};
		return this.http.get('http://localhost:8080/api/culturaloffer/'+ id, queryParams).pipe(map(res => res));
		
	}
	add(newCulturalOffer: CulturalOffer): Observable<any> {
		return this.http.post('http://localhost:8080/api/culturaloffer', newCulturalOffer, {headers: this.headers, responseType: 'text'});
	}
	delete(id: number): Observable<any> {
		
		return this.http.delete('http://localhost:8080/api/culturaloffer/'+id, {headers: this.headers, responseType: 'text'});
	}

	getPage(page: number, size: number): Observable<any> {
		let queryParams = {};
		queryParams = {
			headers: this.headers,
			observe: 'response',
			params: new HttpParams()
				.set('page', String(page))
				.append('size', String(size)),
		};
		return this.http.get('http://localhost:8080/api/culturaloffer/', queryParams).pipe(map(res => res));
		
	}
	getFavorite(): Observable<any> {
		let queryParams = {};
		queryParams = {
			headers: this.headers,
			observe: 'response',
			params: new HttpParams()
		};
		return this.http.get('http://localhost:8080/api/culturaloffer/subscriptions', queryParams).pipe(map(res => res));
		
	}


	edit(newCulturalOffer: CulturalOffer): Observable<any> {
		return this.http.put('http://localhost:8080/api/culturaloffer/'+newCulturalOffer.id, newCulturalOffer, {headers: this.headers, responseType: 'json'});
	}
	searchContent(content: string): Observable<any> {
		let queryParams = {};
		queryParams = {
			headers: this.headers,
			observe: 'response',
			params: new HttpParams()
		};
		return this.http.get('http://localhost:8080/api/culturaloffer/content/'+ content, queryParams).pipe(map(res => res));

	}
	searchCity(city: string): Observable<any> {
		let queryParams = {};
		queryParams = {
			headers: this.headers,
			observe: 'response',
			params: new HttpParams()
		};
		return this.http.get('http://localhost:8080/api/culturaloffer/from_city/'+ city, queryParams).pipe(map(res => res));
	}
	searchCombined(content: string, city: string): Observable<any> {
		let queryParams = {};
		queryParams = {
			headers: this.headers,
			observe: 'response',
			params: new HttpParams()
		};
		return this.http.get('http://localhost:8080/api/culturaloffer/combined/'+ content + '/' + city, queryParams).pipe(map(res => res));
	}
}
