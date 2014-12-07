package au.id.ajlane.hypermedia.demo;

import java.util.List;

public class Blog {

  private final List<AuthorSummary> authors;
  private final String description;
  private final String name;
  private final Paging<Blog> paging;
  private final List<PostSummary> posts;

  public Blog(
      final String name,
      final String description,
      final List<AuthorSummary> authors,
      final List<PostSummary> posts,
      final Paging<Blog> paging
  ) {
    this.name = name;
    this.description = description;
    this.authors = authors;
    this.posts = posts;
    this.paging = paging;
  }

  public List<AuthorSummary> getAuthors() {
    return authors;
  }

  public String getDescription() {
    return description;
  }

  public String getName() {
    return name;
  }

  public Paging<Blog> getPaging() {
    return paging;
  }

  public List<PostSummary> getPosts() {
    return posts;
  }
}
