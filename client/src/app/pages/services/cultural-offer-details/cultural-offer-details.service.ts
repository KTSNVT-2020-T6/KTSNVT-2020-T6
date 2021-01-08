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
}
