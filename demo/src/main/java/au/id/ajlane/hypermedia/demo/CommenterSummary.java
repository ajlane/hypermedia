package au.id.ajlane.hypermedia.demo;

import au.id.ajlane.hypermedia.Hypermedia;
import au.id.ajlane.hypermedia.Link;
import au.id.ajlane.properties.PropertyOrder;

@Hypermedia
@PropertyOrder({"profile", "name", "commentCount"})
public interface CommenterSummary
{
    public int getCommentCount();

    public String getName();

    @Link
    public Commenter getProfile();
}
