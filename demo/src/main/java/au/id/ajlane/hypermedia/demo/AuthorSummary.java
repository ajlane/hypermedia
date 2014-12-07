package au.id.ajlane.hypermedia.demo;

import au.id.ajlane.hypermedia.Link;
import au.id.ajlane.hypermedia.Expect;

import java.net.URI;

public class AuthorSummary {

  private final String name;
  private final URI profile;

  public AuthorSummary(final String name, final URI profile) {
    this.name = name;
    this.profile = profile;
  }

  public String getName() {
    return name;
  }

  @Link
  @Expect("Author")
  public URI getProfile() {
    return profile;
  }
}
