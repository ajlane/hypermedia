package au.id.ajlane.hypermedia.demo;

import au.id.ajlane.hypermedia.Hypermedia;
import au.id.ajlane.hypermedia.Id;
import au.id.ajlane.hypermedia.Link;
import au.id.ajlane.properties.PropertyOrder;

import java.io.InputStream;
import java.util.List;

@Hypermedia
@PropertyOrder({"url", "name", "email", "portrait", "bio", "draftsPaging", "drafts", "postsPaging", "posts"})
public interface Author
{
    String getBio();

    List<PostSummary> getDrafts();

    Paging<Author> getDraftsPaging();

    String getEmail();

    String getName();

    @Link
    InputStream getPortrait();

    List<PostSummary> getPosts();

    Paging<Author> getPostsPaging();

    @Id
    String getUrl();
}
