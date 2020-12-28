import { Routes } from '@angular/router';

import { CulturalOfferDetailsComponent } from '../pages/cultural-offer-details/cultural-offer-details.component';
import { MainPageComponent } from '../pages/main-page/main-page.component';


export const routes :Routes = [
	{path: 'log-in', component: MainPageComponent},
	{path: 'culturaloffer/:id', component: CulturalOfferDetailsComponent}
];
