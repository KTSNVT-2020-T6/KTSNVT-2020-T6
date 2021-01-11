import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../../model/User';

@Injectable({
	providedIn: 'root'
})
export class AuthenticationService {
	private headers = new HttpHeaders({'Content-Type': 'application/json'});

	constructor(
		private http: HttpClient
	) { }

	login(auth: any): Observable<any> {
		return this.http.post('http://localhost:8080/auth/log-in', {username: auth.username, password: auth.password}, {headers: this.headers, responseType: 'json'});
	}

	isLoggedIn(): boolean {
		if (!localStorage.getItem('user')) {
				return false;
		}
		return true;
	}

	register(user: User): Observable<any> {
		return this.http.post('http://localhost:8080/auth/sign-up', user, {headers: this.headers, responseType: 'json'});
	}

	registerAdmin(user: User): Observable<any> {
		console.log(user);
		return this.http.post('http://localhost:8080/auth/sign-up-admin', user, {headers: this.headers, responseType: 'json'});
	}

	signOut(): Observable<any> {
		return this.http.get('http://localhost:8080/auth/sign-out', {headers: this.headers});
	
	}

	editAdmin(user: User): Observable<any> {
		console.log(user);
		return this.http.put('http://localhost:8080/api/admin/'+user.id, user, {headers: this.headers, responseType: 'json'});
	}

	editUser(user: User): Observable<any> {
		console.log(user);
		return this.http.put('http://localhost:8080/api/registered_user/'+user.id, user, {headers: this.headers, responseType: 'json'});
	}


}
