package au.id.ajlane.hypermedia.demo;

import au.id.ajlane.hypermedia.Hypermedia;
import au.id.ajlane.properties.PropertyOrder;

import java.util.List;

@Hypermedia
@PropertyOrder({"name", "description", "authors", "paging", "posts"})
public interface Blog
{
    List<AuthorSummary> getAuthors();

    String getDescription();

    String getName();

    Paging<Blog> getPaging();

    List<PostSummary> getPosts();
}
