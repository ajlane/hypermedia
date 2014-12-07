package au.id.ajlane.hypermedia.demo;

import au.id.ajlane.hypermedia.Id;

public class CommenterSummary {

  private final int commentCount;
  private final String name;
  private final String url;

  public CommenterSummary(final String url, final String name, final int commentCount) {
    this.url = url;
    this.name = name;
    this.commentCount = commentCount;
  }

  public int getCommentCount() {
    return commentCount;
  }

  public String getName() {
    return name;
  }

  @Id
  public String getUrl() {
    return url;
  }
}
