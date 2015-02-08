package au.id.ajlane.hypermedia.demo;

import au.id.ajlane.hypermedia.Hypermedia;
import au.id.ajlane.properties.PropertyOrder;

@Hypermedia
@PropertyOrder({"title", "preview", "author"})
public interface PostSummary
{
    public AuthorSummary getAuthor();

    public String getPreview();

    public String getTitle();
}
