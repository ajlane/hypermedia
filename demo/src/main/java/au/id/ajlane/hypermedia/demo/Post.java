package au.id.ajlane.hypermedia.demo;


import au.id.ajlane.hypermedia.Id;

import java.util.List;

public class Post {

  private final AuthorSummary author;
  private final Paging<Post> commentPaging;
  private final List<Comment> comments;
  private final String content;
  private final Paging<Post> contentPaging;
  private final String title;
  private final String url;

  public Post(
      final String url,
      final String title,
      final AuthorSummary author,
      final String content,
      final List<Comment> comments,
      final Paging<Post> contentPaging,
      final Paging<Post> commentPaging
  ) {
    this.url = url;
    this.title = title;
    this.author = author;
    this.content = content;
    this.comments = comments;
    this.contentPaging = contentPaging;
    this.commentPaging = commentPaging;
  }

  public AuthorSummary getAuthor() {
    return author;
  }

  public Paging<Post> getCommentPaging() {
    return commentPaging;
  }

  public List<Comment> getComments() {
    return comments;
  }

  public String getContent() {
    return content;
  }

  public Paging<Post> getContentPaging() {
    return contentPaging;
  }

  public String getTitle() {
    return title;
  }

  @Id
  public String getUrl() {
    return url;
  }
}
