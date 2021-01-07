import { Injectable } from '@angular/core';
import { HttpClientModule, HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Type } from '../../model/Type';
import { map } from 'rxjs/operators';

@Injectable({
	providedIn: 'root'
})
export class TypeService {
	private headers = new HttpHeaders({'Content-Type': 'application/json'});

	constructor(
		private http: HttpClient
	) {}
    getTypesOfCategory(id:number):Observable<any>{
		let queryParams = {};
		queryParams = {
			headers: this.headers,
			observe: 'response',
			params: new HttpParams()
		};
		return this.http.get('http://localhost:8080/api/type/category/'+ id, queryParams).pipe(map(res => res));
		
	}
	add(newType: Type): Observable<any> {
		return this.http.post('http://localhost:8080/api/type', newType, {headers: this.headers, responseType: 'text'});
	}
	update(editType: Type): Observable<any> {
		return this.http.put('http://localhost:8080/api/type/'+ editType.id, editType, {headers: this.headers, responseType: 'text'});
	}

}