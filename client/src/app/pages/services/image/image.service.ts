import { Injectable } from '@angular/core';
import { HttpClientModule, HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Img } from '../../model/Image';
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
			params: new HttpParams(),
			responseType: 'arraybuffer'
		};
        return this.http.get('http://localhost:8080/api/image/'+ id, queryParams);
	}
	
	add(image: FormData): Observable<any> {
		return this.http.post('http://localhost:8080/api/image', image, {headers: new HttpHeaders(), reportProgress: true}).pipe(map(res => res));
	}
}