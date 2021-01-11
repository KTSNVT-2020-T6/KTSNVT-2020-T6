import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing/app-routing.module';
import { FormsModule, ReactiveFormsModule} from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppComponent } from './app.component';
import { MainPageComponent } from './pages/main-page/main-page.component';
import { NavbarComponent } from './core/navbar-user/navbar/navbar.component';
import { CulturalOfferDetailsComponent } from './pages/cultural-offer-details/cultural-offer-details.component';
import { MatIconModule } from '@angular/material/icon';
import { LayoutModule } from '@angular/cdk/layout';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list'
import { MatSliderModule } from '@angular/material/slider'
import { MatMenuModule } from '@angular/material/menu';
import { MatGridListModule } from '@angular/material/grid-list';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { StarRatingComponent } from './pages/star-rating/star-rating/star-rating.component';
import { FlexLayoutModule} from "@angular/flex-layout";
import { Interceptor } from './interceptors/intercept.service';
import { HomePageComponent } from './pages/home-page/home-page/home-page.component';
import { ToastrModule } from 'ngx-toastr';
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { AddCulturalOfferComponent } from './pages/add-cultural-offer/add-cultural-offer.component';
import { AddCategoryComponent } from './pages/add-category/add-category.component';
import { AddTypeComponent } from './pages/add-type/add-type.component';
import { MaterialModule } from './pages/material-module';
import { NavbarNonAuthComponent } from './core/navbar-non-auth/navbar-non-auth/navbar-non-auth.component';
import { NavbarAdminComponent } from './core/navbar-admin/navbar-admin/navbar-admin.component';
import { EditTypeComponent } from './pages/edit-type/edit-type/edit-type.component';
import { MapContainerComponent } from './pages/map-container/map-container.component';
import { ProfileDetailsComponent } from './pages/profile-details/profile-details.component';
import { AddPostComponent } from './pages/add-post/add-post.component';
import { RegisterPageComponent } from './pages/register-page/register-page.component';
import { VerificationPageComponent } from './pages/verification-page/verification-page.component';
import { CulturalOfferListComponent } from './pages/cultural-offer-list/cultural-offer-list.component';
import { ImageSliderComponent } from './pages/image-slider/image-slider/image-slider.component';
import { CommentListComponent } from './pages/comment-list/comment-list/comment-list.component';
import { AddAdminComponent } from './pages/add-admin/add-admin.component';
import { CategoriesAndTypesComponent } from './pages/categories-and-types/categories-and-types.component';
import { EditCategoryComponent } from './pages/edit-category/edit-category.component';
import { EditCommentComponent } from './pages/edit-comment/edit-comment.component';
import { PaginationComponent } from './pages/pagination/pagination.component';
import { EditPasswordComponent } from './pages/edit-password/edit-password.component';
import { EditCulturalOfferComponent } from './pages/edit-cultural-offer/edit-cultural-offer.component';
import { EditProfileComponent } from './pages/edit-profile/edit-profile.component';
import { PostsPageComponent } from './pages/posts-page/posts-page.component';
@NgModule({
  declarations: [
    AppComponent,
    MainPageComponent,
    CulturalOfferDetailsComponent,
    NavbarComponent,
    StarRatingComponent,
    HomePageComponent,
    LoginPageComponent,
    AddCulturalOfferComponent,
    AddCategoryComponent,
    AddTypeComponent,
    NavbarNonAuthComponent,
    NavbarAdminComponent,
    EditTypeComponent,
    MapContainerComponent,
    ProfileDetailsComponent,
    AddPostComponent,
    RegisterPageComponent,
    VerificationPageComponent,
    CulturalOfferListComponent,
    ImageSliderComponent,
    CommentListComponent,
    AddAdminComponent,
    CategoriesAndTypesComponent,
    EditCategoryComponent,
    EditProfileComponent,
    EditCommentComponent,
    PaginationComponent,
	EditPasswordComponent, 
	EditCulturalOfferComponent, PostsPageComponent

     ],
    imports: [
     BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatIconModule,
    LayoutModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatListModule,
    MatSliderModule,
    MatMenuModule,
    BrowserAnimationsModule,
    MatGridListModule,
    MatCardModule,
    MatFormFieldModule,
    FlexLayoutModule,
    ToastrModule.forRoot(),
    MaterialModule
  ],
  providers: [{provide: HTTP_INTERCEPTORS, useClass: Interceptor, multi: true}],
  bootstrap: [AppComponent]
})
export class AppModule { }
