import { Injectable } from '@angular/core';
import { HttpClientModule, HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Category } from '../../model/Category';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Injectable({
	providedIn: 'root'
})
export class CategoryService {
	private headers = new HttpHeaders({'Content-Type': 'application/json'});

	constructor(
		private http: HttpClient
	) {}
	getAll(): Observable<any> {
		let queryParams = {};
		queryParams = {
			headers: this.headers,
			observe: 'response',
			params: new HttpParams()
		};
	
		return this.http.get(`${environment.baseUrl}/${environment.category}`, queryParams);
	}
    add(newCategory: Category): Observable<any> {
		return this.http.post(`${environment.baseUrl}/${environment.category}`, newCategory, {headers: this.headers, responseType: 'json'});
	}
	getCategory(id: number): Observable<any> {
		let queryParams = {};
		queryParams = {
			headers: this.headers,
			observe: 'response',
			params: new HttpParams()  
	    };
	    return this.http.get(`${environment.baseUrl}/${environment.category}/${id}`, queryParams).pipe(map(res => res));
	}
	update(editCategory: Category, id: number): Observable<any> {
		return this.http.put(`${environment.baseUrl}/${environment.category}/${id}`, editCategory, {headers: this.headers, responseType: 'json'});

	}
	delete(id: number): Observable<any> {
		return this.http.delete(`${environment.baseUrl}/${environment.category}/${id}`, {headers: this.headers, responseType: 'text'});
	}
}
