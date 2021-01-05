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
		return this.http.get('http://localhost:8080/api/culturaloffer/1', queryParams).pipe(map(res => res));
		//return this.http.get<CulturalOffer>('api/culturaloffer/'+ id, queryParams).pipe(map(res => res));
	}
}
