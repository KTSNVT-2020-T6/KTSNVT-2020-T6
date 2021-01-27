import { Injectable } from '@angular/core';
import { HttpClientModule, HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';
import { Img } from '../../model/Image';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';


@Injectable({
    providedIn: 'root'
})
export class ImageService {
    private headers = new HttpHeaders({'Content-Type': 'application/json'});

    private RegenerateData = new Subject<void>();

    RegenerateData$ = this.RegenerateData.asObservable();

    announceChange(): void{
        this.RegenerateData.next();
    }

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

        return this.http.get(`${environment.baseUrl}/${environment.image}/${id}`, queryParams);
    }

    add(image: FormData): Observable<any> {
        return this.http.post(`${environment.baseUrl}/${environment.image}`,
         image, {headers: new HttpHeaders(), reportProgress: true}).pipe(map(res => res));
    }
}
