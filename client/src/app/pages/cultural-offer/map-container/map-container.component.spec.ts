import {ComponentFixture, TestBed } from '@angular/core/testing';
import { DebugElement, SimpleChange, SimpleChanges } from '@angular/core';
import {async, fakeAsync, tick} from '@angular/core/testing';
import { MapContainerComponent } from './map-container.component';
import { HttpClient, HttpClientModule } from '@angular/common/http';


describe('MapContainerComponent', () => {
  let component: MapContainerComponent;
  let fixture: ComponentFixture<MapContainerComponent>;


  beforeEach(() => {

    TestBed.configureTestingModule({
       declarations: [ MapContainerComponent ],
       imports: [HttpClientModule],
       providers:    []
       });

    fixture = TestBed.createComponent(MapContainerComponent);
    component    = fixture.componentInstance;

  });

  it('should create component', fakeAsync(() => {
    expect(component).toBeTruthy();
  }));

  it('should load markers on changes', fakeAsync(() => {
    const previousValue = [{}, {}];
    const currentValue = undefined;
    const changesObj: SimpleChanges = {
        culturalOffers : new SimpleChange(previousValue, currentValue, false),
      };
    spyOn(component, 'ngOnChanges').and.callThrough();
    component.ngOnChanges(changesObj);

    fixture.detectChanges();
    tick();
    expect(component.ngOnChanges).toHaveBeenCalled();
  }));
});
