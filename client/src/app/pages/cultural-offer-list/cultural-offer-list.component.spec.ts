import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CulturalOfferListComponent } from './cultural-offer-list.component';

describe('CulturalOfferListComponent', () => {
  let component: CulturalOfferListComponent;
  let fixture: ComponentFixture<CulturalOfferListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CulturalOfferListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CulturalOfferListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
