package au.id.ajlane.hypermedia.demo;

import au.id.ajlane.hypermedia.Hypermedia;
import au.id.ajlane.hypermedia.Link;
import au.id.ajlane.properties.PropertyOrder;

import java.util.List;

@Hypermedia
@PropertyOrder({"blog", "name", "description", "authors"})
public interface BlogSummary
{
    List<AuthorSummary> getAuthors();

    @Link
    Blog getBlog();

    String getDescription();

    String getName();
}
