package au.id.ajlane.hypermedia.demo;

public class PostSummary {

  private final AuthorSummary author;
  private final String preview;
  private final String title;

  public PostSummary(final String title, final String preview, final AuthorSummary author) {
    this.title = title;
    this.preview = preview;
    this.author = author;
  }

  public AuthorSummary getAuthor() {
    return author;
  }

  public String getPreview() {
    return preview;
  }

  public String getTitle() {
    return title;
  }
}
