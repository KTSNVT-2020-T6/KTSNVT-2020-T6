import { Injectable } from '@angular/core';
import { HttpClientModule, HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';
import { User } from '../../model/User';
import { map } from 'rxjs/operators';


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
		return this.http.delete('http://localhost:8080/api/admin/'+id, {headers: this.headers, responseType: 'text'});
	}

	editAdmin(user: User): Observable<any> {
		return this.http.put('http://localhost:8080/api/admin/'+user.id, user, {headers: this.headers, responseType: 'json'});
	}
}