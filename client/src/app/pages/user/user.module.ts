import { NgModule } from '@angular/core';
import { MaterialModule } from '../material-module';
import { SharedModule } from '../shared/shared.module';
import { AddAdminComponent } from './add-admin/add-admin.component';
import { EditPasswordComponent } from './edit-password/edit-password.component';
import { EditProfileComponent } from './edit-profile/edit-profile.component';
import { ProfileDetailsComponent } from './profile-details/profile-details.component';

@NgModule({
  declarations: [AddAdminComponent, EditPasswordComponent, EditProfileComponent, ProfileDetailsComponent],
  imports: [ MaterialModule, SharedModule],
  exports: [AddAdminComponent, EditPasswordComponent, EditProfileComponent, ProfileDetailsComponent],
  providers: []
})
export class UserModule { }