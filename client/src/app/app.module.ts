import { BrowserModule } from '@angular/platform-browser';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule, NO_ERRORS_SCHEMA } from '@angular/core';

import { AppRoutingModule } from './app-routing/app-routing.module';
import { FormsModule, ReactiveFormsModule} from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppComponent } from './app.component';
import { NavbarUserComponent } from './core/navbar-user/navbar/navbar.component';
import { MatIconModule } from '@angular/material/icon';
import { LayoutModule } from '@angular/cdk/layout';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatSliderModule } from '@angular/material/slider';
import { MatMenuModule } from '@angular/material/menu';
import { MatGridListModule } from '@angular/material/grid-list';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FlexLayoutModule} from '@angular/flex-layout';
import { Interceptor } from './interceptors/intercept.service';
import { ToastrModule } from 'ngx-toastr';
import { MaterialModule } from './pages/material-module';
import { NavbarNonAuthComponent } from './core/navbar-non-auth/navbar-non-auth/navbar-non-auth.component';
import { NavbarAdminComponent } from './core/navbar-admin/navbar-admin/navbar-admin.component';
import { GeoapifyGeocoderAutocompleteModule } from '@geoapify/angular-geocoder-autocomplete';
import { AuthModule } from './pages/auth/auth.module';
import { CategoriesTypesModule } from './pages/categories-types/categories-types.module';
import { CommentsModule } from './pages/comments/comments.module';
import { CulturalOfferModule } from './pages/cultural-offer/cultural-offer.module';
import { PostsModule } from './pages/posts/posts.module';
import { SharedModule } from './pages/shared/shared.module';
import { UserModule } from './pages/user/user.module';



@NgModule({
  declarations: [
    AppComponent,
    NavbarUserComponent,
    NavbarNonAuthComponent,
    NavbarAdminComponent,

     ],
    imports: [
     BrowserModule,
    AppRoutingModule,
    ToastrModule.forRoot(),
    MaterialModule,
    GeoapifyGeocoderAutocompleteModule.withConfig('bac3b0a7331c49dd8a287ce77712b64e'),
    AuthModule,
    CategoriesTypesModule,
    CommentsModule,
    CulturalOfferModule,
    PostsModule,
    SharedModule,
    UserModule
  ],
  providers: [{provide: HTTP_INTERCEPTORS, useClass: Interceptor, multi: true}],
  bootstrap: [AppComponent],
  schemas: [NO_ERRORS_SCHEMA]
})
export class AppModule { }
