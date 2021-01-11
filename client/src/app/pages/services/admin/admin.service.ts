import { Injectable } from '@angular/core';
import { HttpClientModule, HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../../model/User';
import { map } from 'rxjs/operators';


@Injectable({
	providedIn: 'root'
})
export class AdminService {
    private headers = new HttpHeaders({'Content-Type': 'application/json'});

	constructor(
		private http: HttpClient
    ) {}
    
    delete(id: any): Observable<any> {
		return this.http.delete('http://localhost:8080/api/admin/'+id, {headers: this.headers, responseType: 'text'});
	}
}