import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NovaKomdComponent } from './nova-komd.component';

describe('NovaKomdComponent', () => {
  let component: NovaKomdComponent;
  let fixture: ComponentFixture<NovaKomdComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NovaKomdComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NovaKomdComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
