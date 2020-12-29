import { Routes } from '@angular/router';

import { CulturalOfferDetailsComponent } from '../pages/cultural-offer-details/cultural-offer-details.component';
//import { MainPageComponent } from '../pages/main-page/main-page.component';
import { HomePageComponent } from '../pages/home-page/home-page/home-page.component';
import { RoleGuard } from '../guards/role/role.service';
export const routes :Routes = [
	{
		path: 'home',
		component: HomePageComponent,
		canActivate: [RoleGuard],
		data: {expectedRoles: 'ADMIN'}
	},
	{
		path: 'culturaloffer/:id',
		component: CulturalOfferDetailsComponent,
		canActivate: [RoleGuard],
		data: {expectedRoles: 'ADMIN'}
	}
];
