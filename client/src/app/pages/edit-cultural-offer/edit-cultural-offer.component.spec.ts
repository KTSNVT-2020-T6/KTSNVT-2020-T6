import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditCulturalOfferComponent } from './edit-cultural-offer.component';

describe('EditCulturalOfferComponent', () => {
  let component: EditCulturalOfferComponent;
  let fixture: ComponentFixture<EditCulturalOfferComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditCulturalOfferComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditCulturalOfferComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
