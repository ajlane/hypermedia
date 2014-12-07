package au.id.ajlane.hypermedia.demo;

import java.net.URI;
import java.util.List;

public class Index {

  private final List<BlogSummary> blogs;
  private final Paging<Index> paging;
  private final URI url;

  public Index(
      final URI url,
      final List<BlogSummary> blogs,
      final Paging<Index> paging
  ) {
    this.url = url;
    this.blogs = blogs;
    this.paging = paging;
  }

  public List<BlogSummary> getBlogs() {
    return blogs;
  }

  public Paging<Index> getPaging() {
    return paging;
  }

  public URI getUrl() {
    return url;
  }
}
