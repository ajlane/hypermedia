package au.id.ajlane.hypermedia.demo;

import au.id.ajlane.hypermedia.Expect;
import au.id.ajlane.hypermedia.Link;

import java.net.URI;
import java.util.List;

public class BlogSummary {

  private final List<AuthorSummary> authors;
  private final String name;
  private final URI url;
  private final String description;

  public BlogSummary(
      final URI url,
      final String name,
      final String description,
      final List<AuthorSummary> authors
  ) {
    this.url = url;
    this.name = name;
    this.description = description;
    this.authors = authors;
  }

  public List<AuthorSummary> getAuthors() {
    return authors;
  }

  public String getDescription() {
    return description;
  }

  public String getName() {
    return name;
  }

  @Link
  @Expect("Blog")
  public URI getUrl() {
    return url;
  }
}
