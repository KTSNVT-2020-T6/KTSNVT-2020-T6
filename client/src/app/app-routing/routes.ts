import { Routes } from '@angular/router';

import { CulturalOfferDetailsComponent } from '../pages/cultural-offer-details/cultural-offer-details.component';
//import { MainPageComponent } from '../pages/main-page/main-page.component';
import { HomePageComponent } from '../pages/home-page/home-page/home-page.component';
import { RoleGuard } from '../guards/role/role.service';
import { LoginGuard } from '../guards/login/login.service';
import { LoginPageComponent } from '../pages/login-page/login-page.component';
import { ProfileDetailsComponent } from '../pages/profile-details/profile-details.component';
import { RegisterPageComponent } from '../pages/register-page/register-page.component';
import { VerificationPageComponent } from '../pages/verification-page/verification-page.component';
export const routes :Routes = [
	{
		path: 'home',
		component: HomePageComponent
	},
	{
		path: 'culturaloffer/:id',
		component: CulturalOfferDetailsComponent,
		// canActivate: [RoleGuard],
		// data: {expectedRoles: 'ROLE_ADMIN|ROLE_REGISTERED_USER'}
	},
	{
		path: 'login',
		component: LoginPageComponent,
		canActivate: [LoginGuard]
	},
	{
		path: 'profileDetails',
		component: ProfileDetailsComponent,
	},
	{
		path: 'register',
		component: RegisterPageComponent,
		canActivate: [LoginGuard]
	},
	{
		path: 'verification/:token',
		component: VerificationPageComponent,
		canActivate: [LoginGuard]
	},
];
