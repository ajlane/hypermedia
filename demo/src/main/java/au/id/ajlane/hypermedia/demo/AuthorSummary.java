package au.id.ajlane.hypermedia.demo;

import au.id.ajlane.hypermedia.Hypermedia;
import au.id.ajlane.hypermedia.Link;
import au.id.ajlane.properties.PropertyOrder;

@Hypermedia
@PropertyOrder({"name", "profile"})
public interface AuthorSummary
{
    String getName();

    @Link
    Author getProfile();
}
