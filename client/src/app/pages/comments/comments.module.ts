import { NgModule } from '@angular/core';
import { AddCommentComponent } from './add-comment/add-comment.component';
import { EditCommentComponent } from './edit-comment/edit-comment.component';
import { CommentListComponent } from './comment-list/comment-list/comment-list.component';
import { MaterialModule } from '../material-module';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [AddCommentComponent, EditCommentComponent, CommentListComponent],
  imports: [ MaterialModule, SharedModule],
  exports: [AddCommentComponent, EditCommentComponent, CommentListComponent],
  providers: []
})
export class CommentsModule { }
