import { Injectable } from '@angular/core';
import { HttpClientModule, HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';
import { CulturalOffer } from '../../model/CulturalOffer';
import { map } from 'rxjs/operators';
import { User } from '../../model/User';

@Injectable({
	providedIn: 'root'
})
export class RegisteredUserService {
	private headers = new HttpHeaders({'Content-Type': 'application/json'});
	private RegenerateData = new Subject<void>();

    RegenerateData$ = this.RegenerateData.asObservable();

    announceChange() {
        this.RegenerateData.next();
    }
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
	editUser(user: User): Observable<any> {
		return this.http.put('http://localhost:8080/api/registered_user/'+user.id, user, {headers: this.headers, responseType: 'json'});
	}

}
