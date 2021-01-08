import { Injectable } from '@angular/core';
import { HttpClientModule, HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../../model/User';
import { map } from 'rxjs/operators';


@Injectable({
	providedIn: 'root'
})
export class UserService {
    private headers = new HttpHeaders({'Content-Type': 'application/json'});

	constructor(
		private http: HttpClient
    ) {}
    
    getCurrentUser():Observable<any>{
        let queryParams = {};
		queryParams = {
			headers: this.headers,
			observe: 'response',
			params: new HttpParams()
		};
		return this.http.get('http://localhost:8080/api/user/currentUser', queryParams).pipe(map(res => res));
    }
}