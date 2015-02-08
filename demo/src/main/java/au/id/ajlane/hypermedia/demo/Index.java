package au.id.ajlane.hypermedia.demo;

import au.id.ajlane.hypermedia.Hypermedia;
import au.id.ajlane.hypermedia.Id;
import au.id.ajlane.properties.PropertyOrder;

import java.net.URI;
import java.util.List;

@Hypermedia
@PropertyOrder({"url", "paging", "blogs"})
public interface Index
{
    List<BlogSummary> getBlogs();

    Paging<Index> getPaging();

    @Id
    URI getUrl();
}
