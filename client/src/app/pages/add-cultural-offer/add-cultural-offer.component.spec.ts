import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddCulturalOfferComponent } from './add-cultural-offer.component';

describe('AddCulturalOfferComponent', () => {
  let component: AddCulturalOfferComponent;
  let fixture: ComponentFixture<AddCulturalOfferComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddCulturalOfferComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddCulturalOfferComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
