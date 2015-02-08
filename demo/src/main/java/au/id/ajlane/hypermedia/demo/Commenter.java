package au.id.ajlane.hypermedia.demo;

import au.id.ajlane.hypermedia.Hypermedia;
import au.id.ajlane.properties.PropertyOrder;

import java.util.List;

@Hypermedia
@PropertyOrder({"name", "email", "commentCount", "commentsPaging", "comments"})
public interface Commenter
{
    int getCommentCount();

    List<CommentSummary> getComments();

    Paging<Commenter> getCommentsPaging();

    String getEmail();

    String getName();
}
