import { NgModule } from '@angular/core';
import { MaterialModule } from '../material-module';
import { SharedModule } from '../shared/shared.module';
import { AddPostComponent } from './add-post/add-post.component';
import { PostsPageComponent } from './posts-page/posts-page.component';

@NgModule({
  declarations: [PostsPageComponent, AddPostComponent],
  imports: [ MaterialModule, SharedModule],
  exports: [PostsPageComponent, AddPostComponent],
  providers: []
})
export class PostsModule { }
