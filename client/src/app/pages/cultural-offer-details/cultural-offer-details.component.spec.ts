import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CulturalOfferDetailsComponent } from './cultural-offer-details.component';

describe('CulturalOfferDetailsComponent', () => {
  let component: CulturalOfferDetailsComponent;
  let fixture: ComponentFixture<CulturalOfferDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CulturalOfferDetailsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CulturalOfferDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
