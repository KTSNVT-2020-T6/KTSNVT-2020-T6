import { Routes } from '@angular/router';

import { CulturalOfferDetailsComponent } from '../pages/cultural-offer/cultural-offer-details/cultural-offer-details.component';
// import { MainPageComponent } from '../pages/main-page/main-page.component';
import { HomePageComponent } from '../pages/cultural-offer/home-page/home-page/home-page.component';
import { RoleGuard } from '../guards/role/role.service';
import { LoginGuard } from '../guards/login/login.service';
import { LoginPageComponent } from '../pages/auth/login-page/login-page.component';
import { ProfileDetailsComponent } from '../pages/user/profile-details/profile-details.component';
import { RegisterPageComponent } from '../pages/auth/register-page/register-page.component';
import { VerificationPageComponent } from '../pages/auth/verification-page/verification-page.component';
import { CategoriesAndTypesComponent } from '../pages/categories-types/categories-and-types/categories-and-types.component';
import { PostsPageComponent } from '../pages/posts/posts-page/posts-page.component';
import { FavoriteComponent } from '../pages/cultural-offer/favorite/favorite.component';
export const routes: Routes = [
    {
       path: '',
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
        canActivate: [RoleGuard],
        data: {expectedRoles: 'ROLE_ADMIN|ROLE_REGISTERED_USER'}
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
    {
        path: 'categoriesAndTypes',
        component: CategoriesAndTypesComponent,
        canActivate: [RoleGuard],
        data: {expectedRoles: 'ROLE_ADMIN'}
    },
    {
        path: 'posts',
        component: PostsPageComponent,
        // canActivate: [RoleGuard],
        // data: {expectedRoles: 'ROLE_ADMIN|ROLE_REGISTERED_USER'}
    },
    {
        path: 'favorites',
        component: FavoriteComponent,
        canActivate: [RoleGuard],
        data: {expectedRoles: 'ROLE_REGISTERED_USER'}
    },
];
