import { Injectable } from '@angular/core';
import { HttpClientModule, HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CulturalOffer } from '../../model/CulturalOffer';
import { map } from 'rxjs/operators';

@Injectable({
	providedIn: 'root'
})
export class RegisteredUserService {
	private headers = new HttpHeaders({'Content-Type': 'application/json'});

	constructor(
		private http: HttpClient
	) {}
	getNumberOfSubscribed(id: number): Observable<any>{
		let queryParams = {};
		queryParams = {
			headers: this.headers,
			observe: 'response',
			params: new HttpParams()
		};
		return this.http.get('http://localhost:8080/api/registered_user/interested/'+ id, queryParams);

	}
	delete(id: any): Observable<any> {
		return this.http.delete('http://localhost:8080/api/registered_user/'+id, {headers: this.headers, responseType: 'text'});
	}

}
