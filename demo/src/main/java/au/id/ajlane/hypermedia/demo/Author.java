package au.id.ajlane.hypermedia.demo;

import au.id.ajlane.hypermedia.Expect;
import au.id.ajlane.hypermedia.Id;
import au.id.ajlane.hypermedia.Link;

import java.util.List;

public class Author {

  private final String bio;
  private final List<PostSummary> drafts;
  private final String email;
  private final String name;
  private final Paging<Author> paging;
  private final String portrait;
  private final List<PostSummary> posts;
  private final String url;

  public Author(
      final String url,
      final String email,
      final String name,
      final String portrait,
      final String bio,
      final List<PostSummary> posts,
      final List<PostSummary> drafts,
      final Paging<Author> paging
  ) {
    this.url = url;
    this.name = name;
    this.email = email;
    this.portrait = portrait;
    this.bio = bio;
    this.posts = posts;
    this.drafts = drafts;
    this.paging = paging;
  }

  public String getBio() {
    return bio;
  }

  public List<PostSummary> getDrafts() {
    return drafts;
  }

  public String getEmail() {
    return email;
  }

  public String getName() {
    return name;
  }

  public Paging<Author> getPaging() {
    return paging;
  }

  @Link
  @Expect(Expect.BINARY)
  public String getPortrait() {
    return portrait;
  }

  public List<PostSummary> getPosts() {
    return posts;
  }

  @Id
  @Expect("Author")
  public String getUrl() {
    return url;
  }
}
