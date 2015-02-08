package au.id.ajlane.hypermedia.demo;

import au.id.ajlane.hypermedia.Hypermedia;
import au.id.ajlane.properties.PropertyOrder;

@Hypermedia
@PropertyOrder({"url", "title", "paging", "content"})
public interface UnpublishedPost
{
    String getContent();

    Paging<UnpublishedPost> getPaging();

    String getTitle();

    String getUrl();
}
