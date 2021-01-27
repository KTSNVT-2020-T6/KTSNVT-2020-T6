import { Injectable } from '@angular/core';
import { HttpClientModule, HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Rate} from '../../model/Rate';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export class RateService {
    private headers = new HttpHeaders({'Content-Type': 'application/json'});

    constructor(
        private http: HttpClient
    ) {}
    createOrEditRate(newRate: Rate): Observable<any>{
        let queryParams = {};
        queryParams = {
            headers: this.headers,
            observe: 'response',
            params: new HttpParams()
        };
        return this.http.post(`${environment.baseUrl}/${environment.rate}/check`, newRate , {headers: this.headers, responseType: 'json'});
    }

}
