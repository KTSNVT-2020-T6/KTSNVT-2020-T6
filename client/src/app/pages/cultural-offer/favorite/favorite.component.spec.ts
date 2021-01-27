import { ComponentFixture, TestBed } from '@angular/core/testing';
import { fakeAsync, tick } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { Observable, of } from 'rxjs';
import { ImageService } from '../../../core/services/image/image.service';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { FavoriteComponent } from './favorite.component';
import { CulturalOfferDetailsService } from '../../../core/services/cultural-offer-details/cultural-offer-details.service';
import { CulturalOffer } from '../../../core/model/CulturalOffer';

describe('FavoriteComponent', () => {
  let favoriteComponent: FavoriteComponent;
  let fixture: ComponentFixture<FavoriteComponent>;
  let culturalOfferService: any;
  let imageService: any;
  let toastr: any;

  beforeEach(() => {
    const toastrMocked = {
        success: jasmine.createSpy('success'),
        error: jasmine.createSpy('error')
    };

    const coServiceMock = {
        getFavorite: jasmine.createSpy('getFavorite').and.returnValue(
            of({body: [{id: 1, name: 'Sinisa', imageDTO: []}, {id: 2, imageDTO: []}] })),
        unsubscribe: jasmine.createSpy('unsubscribe').and.returnValue(of({}))
    };

    const imageServiceMock = {
        getImage : jasmine.createSpy('getImage').and.returnValue(of({body: {}}))
    };

    TestBed.configureTestingModule({
       declarations: [ FavoriteComponent ],
       imports: [ ToastrModule.forRoot()],
       providers: [
        { provide: ToastrService, useValue: toastrMocked},
        { provide: CulturalOfferDetailsService, useValue: coServiceMock },
        { provide: ImageService, useValue: imageServiceMock },
       ]
    });

    fixture = TestBed.createComponent(FavoriteComponent);
    favoriteComponent = fixture.componentInstance;
    culturalOfferService = TestBed.inject(CulturalOfferDetailsService);
    imageService = TestBed.inject(ImageService);
    toastr = TestBed.inject(ToastrService);
  });

  it('should create commponent', fakeAsync(() => {
    expect(favoriteComponent).toBeTruthy();
  }));

  it('should fetch favorites on init', fakeAsync(() => {
    favoriteComponent.ngOnInit();
    expect(culturalOfferService.getFavorite).toHaveBeenCalled();
    tick();
    expect(favoriteComponent.culturalOffers).toHaveSize(2);

  }));

  it('unsubscribe', fakeAsync(() => {
    favoriteComponent.ngOnInit();
    favoriteComponent.unsubscribe(favoriteComponent.culturalOffers[0]);
    expect(culturalOfferService.unsubscribe).toHaveBeenCalled();

    expect(toastr.success).toHaveBeenCalled();
    expect(favoriteComponent.culturalOffers.length).toBe(1);

  }));



});
