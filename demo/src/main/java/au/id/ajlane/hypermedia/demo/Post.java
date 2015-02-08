package au.id.ajlane.hypermedia.demo;


import au.id.ajlane.hypermedia.Action;
import au.id.ajlane.hypermedia.Hypermedia;

import java.util.List;

@Hypermedia
public interface Post
{
    @Action
    Post edit(PostEdit edit);

    AuthorSummary getAuthor();

    Paging<Post> getCommentPaging();

    List<Comment> getComments();

    String getContent();

    Paging<Post> getContentPaging();

    String getTitle();

    String getUrl();

    @Action
    Post rename(PostRename rename);
}
