package au.id.ajlane.hypermedia.demo;

import java.util.List;

public class Commenter {

  private final int commentCount;
  private final List<CommentSummary> comments;
  private final String name;
  private final String email;
  private final Paging<Commenter> paging;

  public Commenter(
      final String name,
      final String email,
      final int commentCount,
      final Paging<Commenter> paging,
      final List<CommentSummary> comments
  ) {
    this.name = name;
    this.comments = comments;
    this.commentCount = commentCount;
    this.paging = paging;
    this.email = email;
  }

  public int getCommentCount() {
    return commentCount;
  }

  public List<CommentSummary> getComments() {
    return comments;
  }

  public String getEmail() {
    return email;
  }

  public String getName() {
    return name;
  }

  public Paging<Commenter> getPaging() {
    return paging;
  }
}
