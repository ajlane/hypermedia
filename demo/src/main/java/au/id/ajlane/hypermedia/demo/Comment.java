package au.id.ajlane.hypermedia.demo;

import au.id.ajlane.hypermedia.Hypermedia;
import au.id.ajlane.properties.PropertyOrder;

@Hypermedia
@PropertyOrder({"comment", "commenter"})
public interface Comment
{
    String getComment();

    CommenterSummary getCommenter();
}
