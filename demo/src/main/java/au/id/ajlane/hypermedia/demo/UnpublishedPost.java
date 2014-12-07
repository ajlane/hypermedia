package au.id.ajlane.hypermedia.demo;


import au.id.ajlane.hypermedia.Id;

import java.util.List;

public class UnpublishedPost {

  private final String content;
  private final Paging<UnpublishedPost> paging;
  private final String title;
  private final String url;

  public UnpublishedPost(
      final String url,
      final String title,
      final String content,
      final Paging<UnpublishedPost> paging
  ) {
    this.url = url;
    this.title = title;
    this.content = content;
    this.paging = paging;
  }

  public String getContent() {
    return content;
  }

  public Paging<UnpublishedPost> getPaging() {
    return paging;
  }

  public String getTitle() {
    return title;
  }

  @Id
  public String getUrl() {
    return url;
  }
}
