import { Injectable } from '@angular/core';
import { HttpClientModule, HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';
import { User } from '../../model/User';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';


@Injectable({
	providedIn: 'root'
})
export class AdminService {
    private headers = new HttpHeaders({'Content-Type': 'application/json'});
	private RegenerateData = new Subject<void>();

    RegenerateData$ = this.RegenerateData.asObservable();

    announceChange() {
        this.RegenerateData.next();
    }
	constructor(
		private http: HttpClient
    ) {}
    
    delete(id: any): Observable<any> {
		return this.http.delete(`${environment.baseUrl}/${environment.admin}/${id}`, {headers: this.headers, responseType: 'text'});
	}

	editAdmin(user: User): Observable<any> {
		return this.http.put(`${environment.baseUrl}/${environment.admin}/${user.id}`, user, {headers: this.headers, responseType: 'json'});
	}
}