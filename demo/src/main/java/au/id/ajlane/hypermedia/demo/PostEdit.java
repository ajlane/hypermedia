package au.id.ajlane.hypermedia.demo;


import au.id.ajlane.hypermedia.Id;

public class PostEdit {
  private final String content;

  public PostEdit(
      final String content
  ) {
    this.content = content;
  }

  public String getContent() {
    return content;
  }
}
