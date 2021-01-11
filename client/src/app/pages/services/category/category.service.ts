import { Injectable } from '@angular/core';
import { HttpClientModule, HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Category } from '../../model/Category';
import { map } from 'rxjs/operators';

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
		return this.http.get('http://localhost:8080/api/category', queryParams);
	}
    add(newCategory: Category): Observable<any> {
		return this.http.post('http://localhost:8080/api/category', newCategory, {headers: this.headers, responseType: 'text'});
	}
	getCategory(id: number): Observable<any> {
		let queryParams = {};
		queryParams = {
			headers: this.headers,
			observe: 'response',
			params: new HttpParams()
	};
	return this.http.get('http://localhost:8080/api/category/'+id, queryParams).pipe(map(res => res));
	}
	update(editCategory: Category, id: number): Observable<any> {
		return this.http.put('http://localhost:8080/api/category/'+ id, editCategory, {headers: this.headers, responseType: 'text'});

	}
}
