import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
	providedIn: 'root'
})
export class VerificationService {
	private headers = new HttpHeaders({'Content-Type': 'application/json'});

	constructor(
		private http: HttpClient
	) { }

	verify(token: string): Observable<any> {
		return this.http.get('http://localhost:8080/api/verification/'+token, {headers: this.headers, responseType: 'text'});
	}



}