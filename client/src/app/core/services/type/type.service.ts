import { Injectable } from '@angular/core';
import { HttpClientModule, HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Type } from '../../model/Type';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

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
		return this.http.get(`${environment.baseUrl}/${environment.type}/category/${id}`, queryParams).pipe(map(res => res));
		
	}
	add(newType: Type): Observable<any> {
		return this.http.post(`${environment.baseUrl}/${environment.type}`, newType, {headers: this.headers, responseType: 'json'});
	}
	update(editType: Type, id: number): Observable<any> {
		return this.http.put(`${environment.baseUrl}/${environment.type}/${id}`, editType, {headers: this.headers, responseType: 'json'});
	}

	delete(id: number): Observable<any> {
		return this.http.delete(`${environment.baseUrl}/${environment.type}/${id}`, {headers: this.headers, responseType: 'text'});
	}

	getType(id:number):Observable<any>{
		let queryParams = {};
		queryParams = {
			headers: this.headers,
			observe: 'response',
			params: new HttpParams()
		};
		return this.http.get(`${environment.baseUrl}/${environment.type}/${id}`, queryParams).pipe(map(res => res));
	}

	getAll():Observable<any>{
		let queryParams = {};
		queryParams = {
			headers: this.headers,
			observe: 'response',
			params: new HttpParams()
		};
		return this.http.get(`${environment.baseUrl}/${environment.type}`, queryParams);
	}

}