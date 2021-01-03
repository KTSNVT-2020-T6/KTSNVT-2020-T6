import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class Interceptor implements HttpInterceptor {
    //da li je dobar intercepter
	intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

		const item = localStorage.getItem('user');// || "{}";
		

		if (item) {
			const decodedItem = JSON.parse(item);
			const cloned = req.clone({
				headers: req.headers.set('Authentication', decodedItem.token)
			});

			return next.handle(cloned);
		} else {
			return next.handle(req);
		}
	}
}
