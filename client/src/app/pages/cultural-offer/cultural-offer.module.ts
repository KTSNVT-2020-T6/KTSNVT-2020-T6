import { NgModule } from '@angular/core';
import { MaterialModule } from '../material-module';
import { AddCulturalOfferComponent } from './add-cultural-offer/add-cultural-offer.component';
import { EditCulturalOfferComponent } from './edit-cultural-offer/edit-cultural-offer.component';
import { CulturalOfferDetailsComponent } from './cultural-offer-details/cultural-offer-details.component';
import { CulturalOfferListComponent } from './cultural-offer-list/cultural-offer-list.component';
import { FavoriteComponent } from './favorite/favorite.component';
import { ImageSliderComponent } from './image-slider/image-slider/image-slider.component';
import { StarRatingComponent } from './star-rating/star-rating.component';
import { SharedModule } from '../shared/shared.module';
import { GeoapifyGeocoderAutocompleteModule } from '@geoapify/angular-geocoder-autocomplete';
import { CommentsModule } from '../comments/comments.module';
import { HomePageComponent } from './home-page/home-page/home-page.component';
import { MapContainerComponent } from './map-container/map-container.component';
import { SearchDetailsComponent } from './search-details/search-details.component';

@NgModule({
  declarations: [HomePageComponent, MapContainerComponent,
    SearchDetailsComponent, AddCulturalOfferComponent,
    EditCulturalOfferComponent, CulturalOfferDetailsComponent,
    CulturalOfferListComponent, FavoriteComponent, ImageSliderComponent, StarRatingComponent],
  imports: [ MaterialModule, SharedModule,
    GeoapifyGeocoderAutocompleteModule.withConfig('bac3b0a7331c49dd8a287ce77712b64e'), CommentsModule],
  exports: [HomePageComponent, MapContainerComponent,
    SearchDetailsComponent, AddCulturalOfferComponent, EditCulturalOfferComponent,
     CulturalOfferDetailsComponent, CulturalOfferListComponent, FavoriteComponent, ImageSliderComponent, StarRatingComponent],
  providers: []
})
export class CulturalOfferModule { }
