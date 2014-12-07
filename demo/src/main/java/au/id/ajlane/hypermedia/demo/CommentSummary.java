package au.id.ajlane.hypermedia.demo;

public class CommentSummary {

  private final int count;
  private final PostSummary post;

  public CommentSummary(PostSummary post, int count) {
    this.post = post;
    this.count = count;
  }

  public int getCount() {
    return count;
  }

  public PostSummary getPost() {
    return post;
  }
}
