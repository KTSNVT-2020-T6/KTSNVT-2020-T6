import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoriesAndTypesComponent } from './categories-and-types.component';

describe('CategoriesAndTypesComponent', () => {
  let component: CategoriesAndTypesComponent;
  let fixture: ComponentFixture<CategoriesAndTypesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CategoriesAndTypesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CategoriesAndTypesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
