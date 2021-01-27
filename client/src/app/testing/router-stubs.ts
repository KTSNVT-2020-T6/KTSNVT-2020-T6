import { BehaviorSubject, Observable } from 'rxjs';
import { convertToParamMap, ParamMap } from '@angular/router';
import { Injectable } from '@angular/core';

type StringPromise = {} | Promise<string>;

@Injectable()
export class ActivatedRouteStub {
  private subject = new BehaviorSubject(this.testParamss);
  params = this.subject.asObservable();
  private testParams!: {};
  get testParamss(): StringPromise { return this.testParams; }
  set testParamss(params: StringPromise) {
    this.testParams = params;
    this.subject.next(params);
  }
  get snapshot(): any {
    return { params: this.testParamss };
  }
}
