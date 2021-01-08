import { Injectable } from '@angular/core';
import { HttpClientModule, HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Image } from '../../model/Image';
import { map } from 'rxjs/operators';


@Injectable({
	providedIn: 'root'
})
export class ImageService {
    private headers = new HttpHeaders({'Content-Type': 'application/json'});

	constructor(
		private http: HttpClient
    ) {}
    
    getImage(id: any): Observable<any>{
		let queryParams = {};
		queryParams = {
			headers: this.headers,
			observe: 'response',
			params: new HttpParams()
		};
        return this.http.get('http://localhost:8080/api/image/'+ id, queryParams).pipe(map(res => res));
    }
}