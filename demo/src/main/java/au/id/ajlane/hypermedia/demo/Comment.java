package au.id.ajlane.hypermedia.demo;

public class Comment {

  private final String comment;
  private final CommenterSummary commenter;

  public Comment(final CommenterSummary commenter, final String comment) {
    this.commenter = commenter;
    this.comment = comment;
  }

  public String getComment() {
    return comment;
  }

  public CommenterSummary getCommenter() {
    return commenter;
  }
}
