import { NgModule } from '@angular/core';
import { MaterialModule } from '../material-module';
import { ConfirmationComponent } from './confirmation/confirmation.component';
import { PaginationComponent } from './pagination/pagination.component';

@NgModule({
  declarations: [ConfirmationComponent, PaginationComponent],
  imports: [ MaterialModule],
  exports: [ConfirmationComponent, PaginationComponent],
  providers: []
})
export class SharedModule { }
