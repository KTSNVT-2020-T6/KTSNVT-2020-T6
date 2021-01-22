import { Injectable } from '@angular/core';
import { HttpClientModule, HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';
import { CulturalOffer } from '../../model/CulturalOffer';
import { map } from 'rxjs/operators';

@Injectable({
	providedIn: 'root'
})
export class CulturalOfferDetailsService {
	private headers = new HttpHeaders({'Content-Type': 'application/json'});
	private RegenerateData = new Subject<void>();

    RegenerateData$ = this.RegenerateData.asObservable();

    announceChange() {
        this.RegenerateData.next();
    }
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
		return this.http.get('http://localhost:8080/api/cultural-offer/'+ id, queryParams).pipe(map(res => res));
		
	}
	add(newCulturalOffer: CulturalOffer): Observable<any> {
		return this.http.post('http://localhost:8080/api/cultural-offer', newCulturalOffer, {headers: this.headers, responseType: 'json'});
	}
	delete(id: number): Observable<any> {
		return this.http.delete('http://localhost:8080/api/cultural-offer/'+id, {headers: this.headers});
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
		return this.http.get('http://localhost:8080/api/cultural-offer/', queryParams).pipe(map(res => res));
		
	}
	getFavorite(): Observable<any> {
		let queryParams = {};
		queryParams = {
			headers: this.headers,
			observe: 'response',
			params: new HttpParams()
			
		};
		return this.http.get('http://localhost:8080/api/cultural-offer/find/subscriptions', queryParams).pipe(map(res => res));
		
	}

	edit(newCulturalOffer: CulturalOffer): Observable<any> {
		return this.http.put('http://localhost:8080/api/cultural-offer/'+newCulturalOffer.id, newCulturalOffer, {headers: this.headers, responseType: 'json'});
	}
	
	searchCombined(page: number, size: number, content: string, city: string): Observable<any> {
		let queryParams = {};
		queryParams = {
			headers: this.headers,
			observe: 'response',
			params: new HttpParams()
				.set('page', String(page))
				.append('size', String(size)),
		};
		return this.http.get('http://localhost:8080/api/cultural-offer/combined/'+ content + '_' + city, queryParams).pipe(map(res => res));
	}

	unsubscribe(id: any): Observable<any> {
		return this.http.put('http://localhost:8080/api/cultural-offer/unsubscribe/'+id, {headers: this.headers, responseType:'text'});
	}

	subscribeUser(id:any): Observable<any> {
		return this.http.put('http://localhost:8080/api/cultural-offer/subscribe/'+id, {headers: this.headers, responseType:'text'});
	}

}
